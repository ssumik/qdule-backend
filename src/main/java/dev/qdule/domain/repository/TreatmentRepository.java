package dev.qdule.domain.repository;

import java.util.Optional;

import dev.qdule.application.dto.responses.PageResponse;
import dev.qdule.domain.model.Treatment;
import dev.qdule.domain.model.TreatmentType;

public interface TreatmentRepository {
    Optional<Treatment> findById(Long id);

    PageResponse<Treatment> findAll(int page, int size);

    PageResponse<Treatment> findAllByType(int page, int size, TreatmentType type);

    Treatment save(Treatment treatment);

    void removeById(Long id);
}