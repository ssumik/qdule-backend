package dev.qdule.infra.persistence.repository;

import java.util.Optional;

import dev.qdule.application.dto.responses.PageResponse;
import dev.qdule.application.exception.TreatmentNotFoundException;
import dev.qdule.domain.model.Treatment;
import dev.qdule.domain.model.TreatmentType;
import dev.qdule.domain.repository.TreatmentRepository;
import dev.qdule.infra.mapper.TreatmentEntityMapper;
import dev.qdule.infra.persistence.entities.TreatmentEntity;
import dev.qdule.infra.persistence.panache.TreatmentRepositoryPanache;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TreatmentRepositoryImpl implements TreatmentRepository {
    private TreatmentRepositoryPanache treatmentRepositoryPanache;

    @Inject
    public TreatmentRepositoryImpl(TreatmentRepositoryPanache treatmentRepositoryPanache) {
        this.treatmentRepositoryPanache = treatmentRepositoryPanache;
    }

    @Override
    public Optional<Treatment> findById(Long id) {
        return Optional.ofNullable(
                treatmentRepositoryPanache.findById(id))
                .map(TreatmentEntityMapper::toDomain);
    }

    @Override
    public Treatment save(Treatment treatment) {
        TreatmentEntity treatmentEntity = TreatmentEntityMapper.toEntity(treatment);
        var em = treatmentRepositoryPanache.getEntityManager();
        var entity = em.merge(treatmentEntity);
        return TreatmentEntityMapper.toDomain(entity);
    }

    @Override
    public void removeById(Long id) {
        if (!treatmentRepositoryPanache.deleteById(id)) {
            throw new TreatmentNotFoundException(id);
        }
    }

    @Override
    public PageResponse<Treatment> findAll(int page, int size) {
        var pageResponse = new PageResponse<Treatment>();

        pageResponse.setContent(treatmentRepositoryPanache
                .findAll()
                .page(page - 1, size)
                .list()
                .stream()
                .map(TreatmentEntityMapper::toDomain)
                .toList());

        pageResponse.setPage(page);

        pageResponse.setSize(size);

        pageResponse.setTotalElements(treatmentRepositoryPanache
                .findAll()
                .count()
            );
                
        pageResponse.setTotalPages(treatmentRepositoryPanache
                .getEntityManager()
                .createQuery("SELECT COUNT(t) FROM TreatmentEntity t", Long.class)
                .getSingleResult()
                .intValue() / size);

        return pageResponse;
    }

    @Override
    public PageResponse<Treatment> findAllByType(int page, int size, TreatmentType type) {
        var pageResponse = new PageResponse<Treatment>();

        pageResponse.setContent(treatmentRepositoryPanache
                .find("type = ?1", type)
                .page(page - 1, size)
                .list()
                .stream()
                .map(TreatmentEntityMapper::toDomain)
                .toList());

        pageResponse.setPage(page);

        pageResponse.setSize(size);

        pageResponse.setTotalElements(treatmentRepositoryPanache
                .find("type = ?1", type)
                .count()
            );
                

        long totalPages = treatmentRepositoryPanache
                .find("type = ?1", type)
                .count();

        pageResponse.setTotalPages(totalPages);

        return pageResponse;
    }

}
