package dev.qdule.infra.persistence.repository;

import java.util.Optional;

import dev.qdule.application.dto.responses.PageResponse;
import dev.qdule.application.exception.ScheduleNotFoundException;
import dev.qdule.domain.model.Schedule;
import dev.qdule.domain.repository.ScheduleRepository;
import dev.qdule.infra.mapper.ScheduleEntityMapper;
import dev.qdule.infra.persistence.entities.ScheduleEntity;
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
        ScheduleEntity scheduleEntity = scheduleRepositoryPanache.findById(schedule.getId());
        if (scheduleEntity == null) {
            scheduleEntity = new ScheduleEntity();
        }
        var entity = ScheduleEntityMapper.toEntity(schedule, scheduleEntity);
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
    public PageResponse<Schedule> findAll(int page, int size) {
        var pageResponse = new PageResponse<Schedule>();

        pageResponse.setContent(scheduleRepositoryPanache
                .findAll()
                .page(page, size)
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
}
