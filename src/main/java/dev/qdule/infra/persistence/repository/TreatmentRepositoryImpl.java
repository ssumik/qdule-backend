package dev.qdule.infra.persistence.repository;

import java.util.HashMap;
import java.util.Map;
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
    public PageResponse<Treatment> findAll(int page, int size, TreatmentType type, String text) {
        var pageResponse = new PageResponse<Treatment>();

        Map<String, Object> parameters = new HashMap<>();
        String query = "";

        if (type != null) {
            query = addToQuery(query, "type = :type");
            parameters.put("type", type);
        }

        if (text != null && !text.equals("")) {
            query = addToQuery(query, "name like :text or description like :text");
            parameters.put("text", "%" + text + "%");
        }

        pageResponse.setContent(treatmentRepositoryPanache
                .find(query, parameters)
                .page(page - 1, size)
                .list()
                .stream()
                .map(TreatmentEntityMapper::toDomain)
                .toList());

        pageResponse.setPage(page);

        pageResponse.setSize(size);

        pageResponse.setTotalElements(treatmentRepositoryPanache
                .find(query, parameters)
                .count());

        pageResponse.setTotalPages(treatmentRepositoryPanache
                .find(query, parameters)
                .count() / size);

        return pageResponse;
    }

    private String addToQuery(String query, String aditionalQuery) {
        if (query.equals("")) {
            return aditionalQuery;
        }

        return query + " AND " + aditionalQuery;
    }
}
