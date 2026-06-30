package dev.qdule.infra.persistence.repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import dev.qdule.application.dto.responses.PageResponse;
import dev.qdule.application.exception.ScheduleExceptionNotFoundException;
import dev.qdule.domain.model.ScheduleException;
import dev.qdule.domain.repository.ScheduleExceptionRepository;
import dev.qdule.infra.mapper.ScheduleExceptionEntityMapper;
import dev.qdule.infra.persistence.panache.ScheduleExceptionRepositoryPanache;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ScheduleExceptionRepositoryImpl implements ScheduleExceptionRepository {
    private ScheduleExceptionRepositoryPanache scheduleExceptionRepositoryPanache;

    @Inject
    public ScheduleExceptionRepositoryImpl(ScheduleExceptionRepositoryPanache scheduleExceptionRepositoryPanache) {
        this.scheduleExceptionRepositoryPanache = scheduleExceptionRepositoryPanache;
    }

    @Override
    public Optional<ScheduleException> findById(Long id) {
        return Optional.ofNullable(scheduleExceptionRepositoryPanache.findById(id))
                .map(ScheduleExceptionEntityMapper::toDomain);
    }

    @Override
    public PageResponse<ScheduleException> findAll(int page, int size) {
        var pageResponse = new PageResponse<ScheduleException>();

        pageResponse.setContent(scheduleExceptionRepositoryPanache
                .findAll()
                .page(page - 1, size)
                .list()
                .stream()
                .map(ScheduleExceptionEntityMapper::toDomain)
                .toList());

        pageResponse.setPage(page);

        pageResponse.setSize(size);

        long totalElements = scheduleExceptionRepositoryPanache
                .findAll()
                .count();

        pageResponse.setTotalElements(totalElements);

        var pages = (long) Math.ceil(totalElements / size);

        pageResponse.setTotalPages(pages + 1);

        return pageResponse;
    }

    @Override
    public List<ScheduleException> findBlockingExceptions(LocalDateTime start, LocalDateTime end) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("start", start);
        parameters.put("end", end);

        return scheduleExceptionRepositoryPanache
                .find("startDateTime < :end and endDateTime > :start", parameters)
                .list()
                .stream()
                .map(ScheduleExceptionEntityMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsOverlapping(LocalDateTime start, LocalDateTime end) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("start", start);
        parameters.put("end", end);

        return scheduleExceptionRepositoryPanache
                .find("startDateTime < :end and endDateTime > :start", parameters)
                .count() > 0;
    }

    @Override
    public ScheduleException save(ScheduleException scheduleException) {
        var entity = ScheduleExceptionEntityMapper.toEntity(scheduleException);
        var em = scheduleExceptionRepositoryPanache.getEntityManager();
        var merged = em.merge(entity);
        return ScheduleExceptionEntityMapper.toDomain(merged);
    }

    @Override
    public void removeById(Long id) {
        if (!scheduleExceptionRepositoryPanache.deleteById(id)) {
            throw new ScheduleExceptionNotFoundException(id);
        }
    }

}
