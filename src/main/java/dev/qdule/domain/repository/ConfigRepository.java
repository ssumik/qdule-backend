package dev.qdule.domain.repository;

import java.util.Optional;

import dev.qdule.domain.model.Config;

public interface ConfigRepository {
    Optional<Config> findCurrent();

    Config save(Config config);
}
