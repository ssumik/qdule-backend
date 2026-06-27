package dev.qdule.infra.mapper;

import dev.qdule.domain.model.Treatment;
import dev.qdule.infra.persistence.entities.TreatmentEntity;

public class TreatmentEntityMapper {
    public static Treatment toDomain(TreatmentEntity entity) {
        return new Treatment(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getDuration(),
                entity.getPrice(),
                entity.getImagePath(),
                entity.getStatus(),
                entity.getType()
            );
    }

    public static TreatmentEntity toEntity(Treatment treatment) {
        TreatmentEntity entity = new TreatmentEntity();

        entity.setId(treatment.getId());
        entity.setName(treatment.getName());
        entity.setDescription(treatment.getDescription());
        entity.setDuration(treatment.getDuration());
        entity.setPrice(treatment.getPrice());
        entity.setImagePath(treatment.getImagePath());
        entity.setStatus(treatment.getStatus());
        entity.setType(treatment.getType());

        return entity;
    }
}
