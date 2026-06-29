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
import dev.qdule.domain.model.Config;
import dev.qdule.domain.model.Schedule;
import dev.qdule.domain.model.Treatment;
import dev.qdule.domain.repository.ClientRepository;
import dev.qdule.domain.repository.ConfigRepository;
import dev.qdule.domain.repository.ScheduleRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class EmailService {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final ClientRepository clientRepository;
    private final ConfigRepository configRepository;
    private final ScheduleRepository scheduleRepository;

    @ConfigProperty(name = "resend.api-key", defaultValue = "")
    private String resendApiKey;

    @Inject
    public EmailService(
            ClientRepository clientRepository,
            ConfigRepository configRepository,
            ScheduleRepository scheduleRepository) {
        this.clientRepository = clientRepository;
        this.configRepository = configRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public EmailSendResponse send(EmailSendRequest request) {
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new ClientNotFoundException(request.getClientId()));

        Schedule schedule = scheduleRepository.findById(request.getScheduleId())
                .orElseThrow(() -> new ScheduleNotFoundException(request.getScheduleId()));

        validateScheduleClient(client, schedule);
        var config = configRepository
                .findCurrent()
                .orElseThrow(() -> new EmailSendException("Email config is not configured"));

        validateEmailConfiguration(config);

        EmailType emailType = resolveEmailType(request);
        EmailContent emailContent = buildEmailContent(emailType, client, schedule, config);

        CreateEmailOptions emailOptions = CreateEmailOptions.builder()
                .from(config.getSendEmail())
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

    private void validateEmailConfiguration(Config config) {
        if (resendApiKey == null || resendApiKey.isBlank()) {
            throw new EmailSendException("Resend API key is not configured");
        }

        if (config.getSendEmail() == null || config.getSendEmail().isBlank()) {
            throw new EmailSendException("Sender email is not configured");
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
            Schedule schedule,
            Config config) {
        return switch (emailType) {
            case SCHEDULE_CREATED -> new EmailContent(
                    "Confirmacao de agendamento",
                    buildCreatedText(client, schedule, config),
                    buildCreatedHtml(client, schedule, config));
            case SCHEDULE_RESCHEDULED -> new EmailContent(
                    "Agendamento reagendado",
                    buildRescheduledText(client, schedule, config),
                    buildRescheduledHtml(client, schedule, config));
            case SCHEDULE_CANCELED -> new EmailContent(
                    "Agendamento cancelado",
                    buildCanceledText(client, schedule, config),
                    buildCanceledHtml(client, schedule, config));
        };
    }

    private String buildCreatedText(Client client, Schedule schedule, Config config) {
        String scheduleDetails = buildScheduleDetailsText(client, schedule);

        return """
                Ola, %s.

                Seu agendamento foi criado.

                %s

                %s
                """.formatted(
                client.getName(),
                scheduleDetails,
                buildCreatedActionLinksText(config));
    }

    private String buildRescheduledText(Client client, Schedule schedule, Config config) {
        return """
                Ola, %s.

                Seu agendamento foi reagendado.

                %s
                %s
                """.formatted(
                client.getName(),
                buildScheduleDetailsText(client, schedule),
                buildContactLinkText(config));
    }

    private String buildCanceledText(Client client, Schedule schedule, Config config) {
        return """
                Ola, %s.

                Seu agendamento foi cancelado.

                %s
                %s
                """.formatted(
                client.getName(),
                buildScheduleDetailsText(client, schedule),
                buildContactLinkText(config));
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

    private String buildCreatedHtml(Client client, Schedule schedule, Config config) {
        return """
                <p>Ola, %s.</p>
                <p>Seu agendamento foi criado.</p>
                %s
                %s
                """.formatted(
                escapeHtml(client.getName()),
                buildScheduleDetailsHtml(client, schedule),
                buildCreatedActionLinksHtml(config));
    }

    private String buildRescheduledHtml(Client client, Schedule schedule, Config config) {
        return """
                <p>Ola, %s.</p>
                <p>Seu agendamento foi reagendado.</p>
                %s
                %s
                """.formatted(
                escapeHtml(client.getName()),
                buildScheduleDetailsHtml(client, schedule),
                buildContactLinkHtml(config));
    }

    private String buildCanceledHtml(Client client, Schedule schedule, Config config) {
        return """
                <p>Ola, %s.</p>
                <p>Seu agendamento foi cancelado.</p>
                %s
                %s
                """.formatted(
                escapeHtml(client.getName()),
                buildScheduleDetailsHtml(client, schedule),
                buildContactLinkHtml(config));
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

    private String buildCreatedActionLinksHtml(Config config) {
        return buildContactLinkHtml(config);
    }

    private String buildContactLinkHtml(Config config) {
        return buildLinkHtml(config.getContactLink(), "Entrar em contato");
    }

    private String buildLinkHtml(String link, String label) {
        if (link == null || link.isBlank()) {
            return "";
        }

        return """
                <p><a href="%s">%s</a></p>
                """.formatted(escapeHtml(link), escapeHtml(label));
    }

    private String buildCreatedActionLinksText(Config config) {
        StringBuilder builder = new StringBuilder();

        appendTextLink(builder, "Contato", config.getContactLink());

        return builder.toString();
    }

    private String buildContactLinkText(Config config) {
        StringBuilder builder = new StringBuilder();

        appendTextLink(builder, "Contato", config.getContactLink());

        return builder.toString();
    }

    private void appendTextLink(StringBuilder builder, String label, String link) {
        if (link == null || link.isBlank()) {
            return;
        }

        builder
                .append(label)
                .append(": ")
                .append(link)
                .append("\n");
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
