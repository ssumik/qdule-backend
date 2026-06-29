package dev.qdule.domain.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import dev.qdule.application.dto.responses.PageResponse;
import dev.qdule.domain.model.Schedule;
import dev.qdule.domain.model.ScheduleStatus;

public interface ScheduleRepository {
    Optional<Schedule> findById(Long id);

    PageResponse<Schedule> findAll(
            int page,
            int size,
            LocalDateTime start,
            LocalDateTime end,
            ScheduleStatus status);

    List<Schedule> findBlockingSchedules(LocalDateTime start, LocalDateTime end, List<ScheduleStatus> statuses);

    List<Schedule> findByStatuses(LocalDateTime start, LocalDateTime end, List<ScheduleStatus> statuses);

    Schedule save(Schedule schedule);

    void removeById(Long id);
}
