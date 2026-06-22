package dev.qdule.infra.persistence.repository;

import java.util.Optional;

import dev.qdule.application.dto.responses.PageResponse;
import dev.qdule.application.exception.ShiftNotFoundException;
import dev.qdule.domain.model.Shift;
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
        ShiftEntity shiftEntity = shiftRepositoryPanache.findById(shift.getId());
        if (shiftEntity == null) {
            shiftEntity = new ShiftEntity();
        }
        var entity = ShiftEntityMapper.toEntity(shift);
        if (shift.getId() != null) {
            entity.setId(shift.getId());
        }
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
    public PageResponse<Shift> findAll(int page, int size) {
        var pageResponse = new PageResponse<Shift>();

        pageResponse.setContent(shiftRepositoryPanache
                .findAll()
                .page(page, size)
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
}
