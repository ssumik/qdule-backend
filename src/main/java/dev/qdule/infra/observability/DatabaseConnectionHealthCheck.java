package dev.qdule.infra.observability;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Readiness;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Readiness
@ApplicationScoped
public class DatabaseConnectionHealthCheck implements HealthCheck {

    private static final int VALIDATION_TIMEOUT_SECONDS = 2;

    @Inject
    DataSource dataSource;

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder response = HealthCheckResponse.named("database-connections")
                .withData("validationTimeoutSeconds", VALIDATION_TIMEOUT_SECONDS);

        try (Connection connection = dataSource.getConnection()) {
            return response.status(connection.isValid(VALIDATION_TIMEOUT_SECONDS)).build();
        } catch (SQLException exception) {
            return response.down()
                    .withData("error", exception.getClass().getSimpleName())
                    .build();
        }
    }
}
