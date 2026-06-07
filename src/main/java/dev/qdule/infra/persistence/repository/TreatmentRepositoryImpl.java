package dev.qdule.infra.persistence.repository;

import java.util.Optional;

import dev.qdule.application.exception.TreatmentNotFoundException;
import dev.qdule.domain.model.Treatment;
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

}
