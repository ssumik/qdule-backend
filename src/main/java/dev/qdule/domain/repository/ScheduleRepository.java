package dev.qdule.domain.repository;

import java.time.ZonedDateTime;
import java.util.Optional;

import dev.qdule.application.dto.responses.PageResponse;
import dev.qdule.domain.model.Schedule;
import dev.qdule.domain.model.ScheduleStatus;

public interface ScheduleRepository {
    Optional<Schedule> findById(Long id);

    PageResponse<Schedule> findAll(
            int page,
            int size,
            ZonedDateTime start,
            ZonedDateTime end,
            ScheduleStatus status);

    Schedule save(Schedule schedule);

    void removeById(Long id);
}
