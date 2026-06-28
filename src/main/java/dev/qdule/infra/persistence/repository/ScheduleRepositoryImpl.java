package dev.qdule.infra.persistence.repository;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import dev.qdule.application.dto.responses.PageResponse;
import dev.qdule.application.exception.ConflictException;
import dev.qdule.application.exception.ScheduleNotFoundException;
import dev.qdule.domain.model.Schedule;
import dev.qdule.domain.model.ScheduleStatus;
import dev.qdule.domain.repository.ScheduleRepository;
import dev.qdule.infra.mapper.ScheduleEntityMapper;
import dev.qdule.infra.persistence.panache.ScheduleRepositoryPanache;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ScheduleRepositoryImpl implements ScheduleRepository {
    private ScheduleRepositoryPanache scheduleRepositoryPanache;

    @Inject
    public ScheduleRepositoryImpl(ScheduleRepositoryPanache scheduleRepositoryPanache) {
        this.scheduleRepositoryPanache = scheduleRepositoryPanache;
    }

    @Override
    public Optional<Schedule> findById(Long id) {
        return Optional.ofNullable(
                scheduleRepositoryPanache.findById(id))
                .map(ScheduleEntityMapper::toDomain);
    }

    @Override
    public Schedule save(Schedule schedule) {
        var entity = ScheduleEntityMapper.toEntity(schedule);
        var em = scheduleRepositoryPanache.getEntityManager();
        var merged = em.merge(entity);
        return ScheduleEntityMapper.toDomain(merged);
    }

    @Override
    public void removeById(Long id) {
        if (!scheduleRepositoryPanache.deleteById(id)) {
            throw new ScheduleNotFoundException(id);
        }
    }

    @Override
    public PageResponse<Schedule> findAll(
            int page,
            int size,
            ZonedDateTime start,
            ZonedDateTime end,
            ScheduleStatus status) {
        var pageResponse = new PageResponse<Schedule>();

        Map<String, Object> parameters = new HashMap<>();
        String query = "";

        if (start != null && end != null) {
            query = addToQuery(query, " startDateTime < :end and endDateTime > :start");
            parameters.put("start", start);
            parameters.put("end", end);
        }

        if (status != null) {
            query = addToQuery(query, " status = :status");
            parameters.put("status", status);
        }

        pageResponse.setContent(scheduleRepositoryPanache
                .find(query, parameters)
                .page(page - 1, size)
                .list()
                .stream()
                .map(ScheduleEntityMapper::toDomain)
                .toList());

        pageResponse.setPage(page);

        pageResponse.setSize(size);

        pageResponse.setTotalElements(scheduleRepositoryPanache
                .findAll().count());

        pageResponse.setTotalPages(scheduleRepositoryPanache.getEntityManager()
                .createQuery("SELECT COUNT(s) FROM ScheduleEntity s", Long.class)
                .getSingleResult()
                .intValue() / size);

        return pageResponse;
    }

    private String addToQuery(String query, String aditionalQuery) {
        if (query.equals("")) {
            return aditionalQuery;
        }

        return query + " AND " + aditionalQuery;
    }
}
