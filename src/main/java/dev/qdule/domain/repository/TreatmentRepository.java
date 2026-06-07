package dev.qdule.domain.repository;

import java.util.Optional;

import dev.qdule.domain.model.Treatment;

public interface TreatmentRepository {
    Optional<Treatment> findById(Long id);

    Treatment save(Treatment treatment);

    void removeById(Long id);
}