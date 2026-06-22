package dev.qdule.domain.repository;

import java.util.Optional;

import dev.qdule.application.dto.responses.PageResponse;
import dev.qdule.domain.model.Changelog;

public interface ChangelogRepository {
    Optional<Changelog> findById(Long id);

    PageResponse<Changelog> findAll(int page, int size);

    Changelog save(Changelog changelog);

    void removeById(Long id);
}
