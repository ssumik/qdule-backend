package dev.qdule.infra.persistence.panache;

import dev.qdule.infra.persistence.entities.WorkScheduleEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WorkScheduleRepositoryPanache implements PanacheRepository<WorkScheduleEntity> {
}
