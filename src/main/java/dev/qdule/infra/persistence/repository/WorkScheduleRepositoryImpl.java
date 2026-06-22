package dev.qdule.infra.persistence.repository;

import java.util.Optional;

import dev.qdule.application.dto.responses.PageResponse;
import dev.qdule.application.exception.WorkScheduleNotFoundException;
import dev.qdule.domain.model.WorkSchedule;
import dev.qdule.domain.repository.WorkScheduleRepository;
import dev.qdule.infra.mapper.WorkScheduleEntityMapper;
import dev.qdule.infra.persistence.panache.WorkScheduleRepositoryPanache;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class WorkScheduleRepositoryImpl implements WorkScheduleRepository {
    private WorkScheduleRepositoryPanache workScheduleRepositoryPanache;

    @Inject
    public WorkScheduleRepositoryImpl(WorkScheduleRepositoryPanache workScheduleRepositoryPanache) {
        this.workScheduleRepositoryPanache = workScheduleRepositoryPanache;
    }

    @Override
    public Optional<WorkSchedule> findById(Long id) {
        return Optional.ofNullable(
                workScheduleRepositoryPanache.findById(id))
                .map(WorkScheduleEntityMapper::toDomain);
    }

    @Override
    public WorkSchedule save(WorkSchedule workSchedule) {
        dev.qdule.infra.persistence.entities.WorkScheduleEntity workScheduleEntity = workScheduleRepositoryPanache
                .findById(workSchedule.getId());
        if (workScheduleEntity == null) {
            workScheduleEntity = new dev.qdule.infra.persistence.entities.WorkScheduleEntity();
        }
        var entity = WorkScheduleEntityMapper.toEntity(workSchedule, workScheduleEntity);
        var em = workScheduleRepositoryPanache.getEntityManager();
        var merged = em.merge(entity);
        return WorkScheduleEntityMapper.toDomain(merged);
    }

    @Override
    public void removeById(Long id) {
        if (!workScheduleRepositoryPanache.deleteById(id)) {
            throw new WorkScheduleNotFoundException(id);
        }
    }

    @Override
    public PageResponse<WorkSchedule> findAll(int page, int size) {
        var pageResponse = new PageResponse<WorkSchedule>();

        pageResponse.setContent(workScheduleRepositoryPanache
                .findAll()
                .page(page, size)
                .list()
                .stream()
                .map(WorkScheduleEntityMapper::toDomain)
                .toList());
        pageResponse.setPage(page);
        pageResponse.setSize(size);
        pageResponse.setTotalElements(workScheduleRepositoryPanache
                .findAll().count());
        pageResponse.setTotalPages(workScheduleRepositoryPanache.getEntityManager()
                .createQuery("SELECT COUNT(w) FROM dev.qdule.infra.persistence.entities.WorkSchedule w", Long.class)
                .getSingleResult()
                .intValue() / size);

        return pageResponse;
    }
}
