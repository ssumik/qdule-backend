package dev.qdule.infra.persistence.repository;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import dev.qdule.application.exception.ShiftNotFoundException;
import dev.qdule.domain.model.Shift;
import dev.qdule.domain.model.ShiftStatus;
import dev.qdule.domain.repository.ShiftRepository;
import dev.qdule.infra.mapper.ShiftEntityMapper;
import dev.qdule.infra.persistence.entities.ShiftEntity;
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
        var entity = ShiftEntityMapper.toEntity(shift);
        var em = shiftRepositoryPanache.getEntityManager();
        var merged = em.merge(entity);
        return ShiftEntityMapper.toDomain(merged);
    }

    @Override
    public void removeById(Long id) {
        if (!shiftRepositoryPanache.deleteById(id)) {
            throw new ShiftNotFoundException(id);
        }
    }

    @Override
    public List<Shift> findAll(ShiftStatus status, List<DayOfWeek> days) {
        Map<String, Object> parameters = new HashMap<>();
        String query = "";

        if (status != null) {
            query = addToQuery(query, "status = :status");
            parameters.put("status", status);
        }

        if (days != null && !days.isEmpty()) {
            query = addToQuery(query, "dayOfWeek in :days");
            parameters.put("days", days);
        }

        List<ShiftEntity> entities;

        if (query.isBlank()) {
            entities = shiftRepositoryPanache.listAll();
        } else {
            entities = shiftRepositoryPanache.list(query, parameters);
        }

        return entities
                .stream()
                .map(ShiftEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Shift> findByDay(DayOfWeek dayOfWeek) {
        return shiftRepositoryPanache
                .find("dayOfWeek = ?1", dayOfWeek)
                .firstResultOptional()
                .map(ShiftEntityMapper::toDomain);
    }

    @Override
    public List<Shift> findEnabledByDays(Set<DayOfWeek> days) {
        if (days == null || days.isEmpty()) {
            return List.of();
        }

        return shiftRepositoryPanache
                .find("dayOfWeek in ?1 and status = ?2", days, ShiftStatus.ENABLED)
                .list()
                .stream()
                .map(ShiftEntityMapper::toDomain)
                .toList();
    }

    private String addToQuery(String query, String aditionalQuery) {
        if (query.equals("")) {
            return aditionalQuery;
        }

        return query + " AND " + aditionalQuery;
    }
}
