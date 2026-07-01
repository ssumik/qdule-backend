package dev.qdule.infra.observability;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@ApplicationScoped
@Startup
public class DatabaseHealthMetrics {

    @Inject
    MeterRegistry meterRegistry;

    @Inject
    DataSource dataSource;

    @PostConstruct
    void registerMetrics() {
        Gauge.builder("qdule_database_connection_health", this, DatabaseHealthMetrics::databaseHealth)
                .description("Database connection health. 1 means healthy, 0 means unhealthy.")
                .register(meterRegistry);
    }

    double databaseHealth() {
        try (Connection connection = dataSource.getConnection()) {
            return connection.isValid(2) ? 1 : 0;
        } catch (SQLException exception) {
            return 0;
        }
    }
}
