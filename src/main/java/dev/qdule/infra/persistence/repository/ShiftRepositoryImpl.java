package dev.qdule.infra.persistence.repository;

import java.time.DayOfWeek;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import dev.qdule.application.dto.responses.PageResponse;
import dev.qdule.application.exception.ConflictException;
import dev.qdule.application.exception.ShiftNotFoundException;
import dev.qdule.application.mapper.ShiftMapper;
import dev.qdule.domain.model.Shift;
import dev.qdule.domain.model.ShiftBreak;
import dev.qdule.domain.repository.ShiftRepository;
import dev.qdule.infra.mapper.ShiftEntityMapper;
import dev.qdule.infra.mapper.UserEntityMapper;
import dev.qdule.infra.persistence.panache.ShiftRepositoryPanache;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ShiftRepositoryImpl implements ShiftRepository {
    private ShiftRepositoryPanache shiftRepositoryPanache;

    @Inject
    public ShiftRepositoryImpl(ShiftRepositoryPanache shiftRepositoryPanache) {
        this.shiftRepositoryPanache = shiftRepositoryPanache;
    }

    @Override
    public Optional<Shift> findById(Long id) {
        return Optional.ofNullable(
                shiftRepositoryPanache.findById(id))
                .map(ShiftEntityMapper::toDomain);
    }

    @Override
    public Shift save(Shift shift) {
        validateBreaks(shift.getBreaks());
        var entity = ShiftEntityMapper.toEntity(shift);
        var em = shiftRepositoryPanache.getEntityManager();
        var merged = em.merge(entity);
        return ShiftEntityMapper.toDomain(merged);
    }

    // TODO: VALIDAR SE FAZ SENTIDO MANTER DESTA FORMA
    private void validateBreaks(List<ShiftBreak> breaks) {
        if (breaks == null || breaks.isEmpty()) {
            return;
        }

        var sortedBreaks = breaks.stream()
                .peek(this::validateBreakRange)
                .sorted(Comparator.comparing(ShiftBreak::getStartTime))
                .toList();

        for (int index = 1; index < sortedBreaks.size(); index++) {
            ShiftBreak previous = sortedBreaks.get(index - 1);
            ShiftBreak current = sortedBreaks.get(index);

            if (current.getStartTime().isBefore(previous.getEndTime())) {
                throw new ConflictException("Shift breaks cannot overlap");
            }
        }
    }

    private void validateBreakRange(ShiftBreak shiftBreak) {
        if (shiftBreak.getStartTime() == null || shiftBreak.getEndTime() == null) {
            throw new ConflictException("Shift break start time and end time are required");
        }

        if (!shiftBreak.getStartTime().isBefore(shiftBreak.getEndTime())) {
            throw new ConflictException(
                    "Shift break start time must be before end time");
        }
    }

    @Override
    public void removeById(Long id) {
        if (!shiftRepositoryPanache.deleteById(id)) {
            throw new ShiftNotFoundException(id);
        }
    }

    @Override
    public PageResponse<Shift> findAll(int page, int size) {
        var pageResponse = new PageResponse<Shift>();

        pageResponse.setContent(shiftRepositoryPanache
                .findAll()
                .page(page - 1, size)
                .list()
                .stream()
                .map(ShiftEntityMapper::toDomain)
                .toList());
        pageResponse.setPage(page);
        pageResponse.setSize(size);
        pageResponse.setTotalElements(shiftRepositoryPanache
                .findAll().count());
        pageResponse.setTotalPages(shiftRepositoryPanache.getEntityManager()
                .createQuery("SELECT COUNT(s) FROM ShiftEntity s", Long.class)
                .getSingleResult()
                .intValue() / size);

        return pageResponse;
    }

    @Override
    public Optional<Shift> findByDay(DayOfWeek dayOfWeek) {
        return shiftRepositoryPanache
                .find("dayOfWeek = ?1", dayOfWeek)
                .firstResultOptional()
                .map(ShiftEntityMapper::toDomain);
    }
}
