package dev.qdule.domain.repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import dev.qdule.domain.model.Shift;
import dev.qdule.domain.model.ShiftStatus;

public interface ShiftRepository {
    Optional<Shift> findByDay(DayOfWeek dayOfWeek);

    List<Shift> findEnabledByDays(Set<DayOfWeek> days);

    Optional<Shift> findById(Long id);

    List<Shift> findAll(ShiftStatus status, List<DayOfWeek> days);

    Shift save(Shift shift);

    void removeById(Long id);
}
