package dev.qdule.domain.repository;

import java.util.Optional;

import dev.qdule.application.dto.responses.PageResponse;
import dev.qdule.domain.model.Shift;

public interface ShiftRepository {
    Optional<Shift> findById(Long id);

    PageResponse<Shift> findAll(int page, int size);

    Shift save(Shift shift);

    void removeById(Long id);
}
