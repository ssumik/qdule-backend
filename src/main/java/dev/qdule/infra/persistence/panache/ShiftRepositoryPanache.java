package dev.qdule.infra.persistence.panache;

import dev.qdule.infra.persistence.entities.ShiftEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ShiftRepositoryPanache implements PanacheRepository<ShiftEntity> {
}
