package dev.qdule.domain.repository;

import java.util.Optional;

import dev.qdule.application.dto.responses.PageResponse;
import dev.qdule.domain.model.Schedule;

public interface ScheduleRepository {
    Optional<Schedule> findById(Long id);

    PageResponse<Schedule> findAll(int page, int size);

    Schedule save(Schedule schedule);

    void removeById(Long id);
}
