package dev.qdule.application.services;

import java.time.format.DateTimeFormatter;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;

import dev.qdule.application.dto.requests.EmailSendRequest;
import dev.qdule.application.dto.responses.EmailSendResponse;
import dev.qdule.application.exception.ClientNotFoundException;
import dev.qdule.application.exception.ConflictException;
import dev.qdule.application.exception.EmailSendException;
import dev.qdule.application.exception.ScheduleNotFoundException;
import dev.qdule.domain.model.Client;
import dev.qdule.domain.model.Schedule;
import dev.qdule.domain.model.Treatment;
import dev.qdule.domain.repository.ClientRepository;
import dev.qdule.domain.repository.ScheduleRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class EmailService {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final ClientRepository clientRepository;
    private final ScheduleRepository scheduleRepository;

    private String resendFrom = "onboarding@resend.dev";

    @ConfigProperty(name = "resend.api-key", defaultValue = "")
    private String resendApiKey;

    @Inject
    public EmailService(
            ClientRepository clientRepository,
            ScheduleRepository scheduleRepository) {
        this.clientRepository = clientRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public EmailSendResponse send(EmailSendRequest request) {
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new ClientNotFoundException(request.getClientId()));

        Schedule schedule = scheduleRepository.findById(request.getScheduleId())
                .orElseThrow(() -> new ScheduleNotFoundException(request.getScheduleId()));

        validateScheduleClient(client, schedule);
        validateEmailConfiguration();

        CreateEmailOptions emailOptions = CreateEmailOptions.builder()
                .from(resendFrom)
                .to(client.getEmail())
                .subject("Confirmacao de agendamento")
                .text(buildText(client, schedule))
                .html(buildHtml(client, schedule))
                .build();

        try {
            CreateEmailResponse response = new Resend(resendApiKey).emails().send(emailOptions);
            return new EmailSendResponse(response.getId(), client.getId(), schedule.getId(), client.getEmail());
        } catch (ResendException exception) {
            throw new EmailSendException("Unable to send email with Resend :" + exception.getMessage(), exception);
        }
    }

    private void validateScheduleClient(Client client, Schedule schedule) {
        if (!schedule.getClient().getId().equals(client.getId())) {
            throw new ConflictException("Schedule does not belong to the informed client");
        }
    }

    private void validateEmailConfiguration() {
        if (resendApiKey == null || resendApiKey.isBlank()) {
            throw new EmailSendException("Resend API key is not configured");
        }
    }

    private String buildText(Client client, Schedule schedule) {
        Treatment treatment = schedule.getTreatment();

        return """
                Ola, %s.

                Seguem os dados do seu agendamento:

                Cliente: %s
                Email: %s
                Agendamento: %s
                Tratamento: %s
                Inicio: %s
                Fim: %s
                Status: %s
                Motivo: %s
                """.formatted(
                client.getName(),
                client.getName(),
                client.getEmail(),
                schedule.getId(),
                treatment.getName(),
                DATE_TIME_FORMATTER.format(schedule.getStartDateTime()),
                DATE_TIME_FORMATTER.format(schedule.getEndDateTime()),
                schedule.getStatus(),
                valueOrEmpty(schedule.getReason()));
    }

    private String buildHtml(Client client, Schedule schedule) {
        Treatment treatment = schedule.getTreatment();

        return """
                <p>Ola, %s.</p>
                <p>Seguem os dados do seu agendamento:</p>
                <ul>
                    <li><strong>Cliente:</strong> %s</li>
                    <li><strong>Email:</strong> %s</li>
                    <li><strong>Agendamento:</strong> %s</li>
                    <li><strong>Tratamento:</strong> %s</li>
                    <li><strong>Inicio:</strong> %s</li>
                    <li><strong>Fim:</strong> %s</li>
                    <li><strong>Status:</strong> %s</li>
                    <li><strong>Motivo:</strong> %s</li>
                </ul>
                """.formatted(
                escapeHtml(client.getName()),
                escapeHtml(client.getName()),
                escapeHtml(client.getEmail()),
                schedule.getId(),
                escapeHtml(treatment.getName()),
                DATE_TIME_FORMATTER.format(schedule.getStartDateTime()),
                DATE_TIME_FORMATTER.format(schedule.getEndDateTime()),
                schedule.getStatus(),
                escapeHtml(valueOrEmpty(schedule.getReason())));
    }

    private String valueOrEmpty(String value) {
        return value == null ? "" : value;
    }

    private String escapeHtml(String value) {
        return valueOrEmpty(value)
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
