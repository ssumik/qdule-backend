package dev.qdule.domain.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import dev.qdule.application.dto.responses.PageResponse;
import dev.qdule.domain.model.ScheduleException;

public interface ScheduleExceptionRepository {
    Optional<ScheduleException> findById(Long id);

    PageResponse<ScheduleException> findAll(int page, int size);

    boolean existsOverlapping(LocalDateTime start, LocalDateTime end);

    ScheduleException save(ScheduleException scheduleException);

    void removeById(Long id);
}
