package dev.qdule.infra.persistence.repository;

import java.util.Optional;

import dev.qdule.domain.model.Config;
import dev.qdule.domain.repository.ConfigRepository;
import dev.qdule.infra.mapper.ConfigEntityMapper;
import dev.qdule.infra.persistence.entities.ConfigEntity;
import dev.qdule.infra.persistence.panache.ConfigRepositoryPanache;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ConfigRepositoryImpl implements ConfigRepository {
    private ConfigRepositoryPanache configRepositoryPanache;

    @Inject
    public ConfigRepositoryImpl(ConfigRepositoryPanache configRepositoryPanache) {
        this.configRepositoryPanache = configRepositoryPanache;
    }

    @Override
    public Optional<Config> findCurrent() {
        return configRepositoryPanache
                .findAll(Sort.by("id"))
                .firstResultOptional()
                .map(ConfigEntityMapper::toDomain);
    }

    @Override
    public Config save(Config config) {
        ConfigEntity configEntity = ConfigEntityMapper.toEntity(config);
        var em = configRepositoryPanache.getEntityManager();
        var entity = em.merge(configEntity);
        return ConfigEntityMapper.toDomain(entity);
    }
}
