package dev.qdule.infra.persistence.panache;

import dev.qdule.infra.persistence.entities.ConfigEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ConfigRepositoryPanache implements PanacheRepository<ConfigEntity> {
}
