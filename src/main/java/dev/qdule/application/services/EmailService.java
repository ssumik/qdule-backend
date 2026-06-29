package dev.qdule.application.services;

import java.time.format.DateTimeFormatter;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;

import dev.qdule.application.dto.EmailType;
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
    // TODO: REMOVER E MANDAR PARA O CONFIG ENTITY
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final String DEFAULT_LINK_LABEL = "Acesse a area do cliente para alterar seu agendamento.";

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

        EmailType emailType = resolveEmailType(request);
        EmailContent emailContent = buildEmailContent(emailType, client, schedule);

        CreateEmailOptions emailOptions = CreateEmailOptions.builder()
                .from(resendFrom)
                .to(client.getEmail())
                .subject(emailContent.subject())
                .text(emailContent.text())
                .html(emailContent.html())
                .build();

        try {
            CreateEmailResponse response = new Resend(resendApiKey).emails().send(emailOptions);
            return new EmailSendResponse(response.getId(), client.getId(), schedule.getId(), client.getEmail(),
                    emailType);
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

    private EmailType resolveEmailType(EmailSendRequest request) {
        if (request.getEmailType() == null) {
            return EmailType.SCHEDULE_CREATED;
        }

        return request.getEmailType();
    }

    private EmailContent buildEmailContent(
            EmailType emailType,
            Client client,
            Schedule schedule) {
        return switch (emailType) {
            case SCHEDULE_CREATED -> new EmailContent(
                    "Confirmacao de agendamento",
                    buildCreatedText(client, schedule),
                    buildCreatedHtml(client, schedule));
            case SCHEDULE_RESCHEDULED -> new EmailContent(
                    "Agendamento reagendado",
                    buildRescheduledText(client, schedule),
                    buildRescheduledHtml(client, schedule));
            case SCHEDULE_CANCELED -> new EmailContent(
                    "Agendamento cancelado",
                    buildCanceledText(client, schedule),
                    buildCanceledHtml(client, schedule));
        };
    }

    private String buildCreatedText(Client client, Schedule schedule) {
        String scheduleDetails = buildScheduleDetailsText(client, schedule);
        // TODO: AJUSTAR
        String actionLink = DEFAULT_LINK_LABEL;

        return """
                Ola, %s.

                Seu agendamento foi criado.

                %s

                Reagendar: %s
                Cancelar: %s
                """.formatted(
                client.getName(),
                scheduleDetails,
                actionLink,
                actionLink);
    }

    private String buildRescheduledText(Client client, Schedule schedule) {
        return """
                Ola, %s.

                Seu agendamento foi reagendado.

                %s
                """.formatted(
                client.getName(),
                buildScheduleDetailsText(client, schedule));
    }

    private String buildCanceledText(Client client, Schedule schedule) {
        return """
                Ola, %s.

                Seu agendamento foi cancelado.

                %s
                """.formatted(
                client.getName(),
                buildScheduleDetailsText(client, schedule));
    }

    private String buildScheduleDetailsText(Client client, Schedule schedule) {
        Treatment treatment = schedule.getTreatment();

        return """
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
                client.getEmail(),
                schedule.getId(),
                treatment.getName(),
                DATE_TIME_FORMATTER.format(schedule.getStartDateTime()),
                DATE_TIME_FORMATTER.format(schedule.getEndDateTime()),
                schedule.getStatus(),
                valueOrEmpty(schedule.getReason()));
    }

    private String buildCreatedHtml(Client client, Schedule schedule) {
        return """
                <p>Ola, %s.</p>
                <p>Seu agendamento foi criado.</p>
                %s
                %s
                """.formatted(
                escapeHtml(client.getName()),
                buildScheduleDetailsHtml(client, schedule),
                buildActionLinksHtml());
    }

    private String buildRescheduledHtml(Client client, Schedule schedule) {
        return """
                <p>Ola, %s.</p>
                <p>Seu agendamento foi reagendado.</p>
                %s
                """.formatted(
                escapeHtml(client.getName()),
                buildScheduleDetailsHtml(client, schedule));
    }

    private String buildCanceledHtml(Client client, Schedule schedule) {
        return """
                <p>Ola, %s.</p>
                <p>Seu agendamento foi cancelado.</p>
                %s
                """.formatted(
                escapeHtml(client.getName()),
                buildScheduleDetailsHtml(client, schedule));
    }

    private String buildScheduleDetailsHtml(Client client, Schedule schedule) {
        Treatment treatment = schedule.getTreatment();

        return """
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
                escapeHtml(client.getEmail()),
                schedule.getId(),
                escapeHtml(treatment.getName()),
                DATE_TIME_FORMATTER.format(schedule.getStartDateTime()),
                DATE_TIME_FORMATTER.format(schedule.getEndDateTime()),
                schedule.getStatus(),
                escapeHtml(valueOrEmpty(schedule.getReason())));
    }

    private String buildActionLinksHtml() {
        // TODO: AJUSTAR
        String actionLink = escapeHtml(DEFAULT_LINK_LABEL);

        return """
                <p><a href="%s">Reagendar atendimento</a></p>
                <p><a href="%s">Cancelar atendimento</a></p>
                """.formatted(actionLink, actionLink);
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

    private record EmailContent(String subject, String text, String html) {
    }
}
