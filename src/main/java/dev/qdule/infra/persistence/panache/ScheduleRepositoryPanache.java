package dev.qdule.infra.persistence.panache;

import dev.qdule.infra.persistence.entities.ScheduleEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ScheduleRepositoryPanache implements PanacheRepository<ScheduleEntity> {
}
