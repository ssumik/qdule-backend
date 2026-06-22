package dev.qdule.domain.repository;

import java.util.List;
import java.util.Optional;

import dev.qdule.application.dto.responses.PageResponse;
import dev.qdule.domain.model.Treatment;

public interface TreatmentRepository {
    Optional<Treatment> findById(Long id);

    PageResponse<Treatment> findAll(int size, int page);

    Treatment save(Treatment treatment);

    void removeById(Long id);
}