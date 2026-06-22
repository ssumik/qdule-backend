package dev.qdule.domain.repository;

import java.util.Optional;

import dev.qdule.application.dto.responses.PageResponse;
import dev.qdule.domain.model.WorkSchedule;

public interface WorkScheduleRepository {
    Optional<WorkSchedule> findById(Long id);

    PageResponse<WorkSchedule> findAll(int page, int size);

    WorkSchedule save(WorkSchedule workSchedule);

    void removeById(Long id);
}
