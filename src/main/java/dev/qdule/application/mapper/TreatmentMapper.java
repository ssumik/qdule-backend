package dev.qdule.application.mapper;

import dev.qdule.application.dto.responses.TreatmentResponse;
import dev.qdule.domain.model.Treatment;

public class TreatmentMapper {

    public static TreatmentResponse toResponse(Treatment treatment) {
        TreatmentResponse response = new TreatmentResponse();

        response.setId(treatment.getId());
        response.setName(treatment.getName());
        response.setDescription(treatment.getDescription());
        response.setDuration(treatment.getDuration());
        response.setPrice(treatment.getPrice());
        response.setImagePath(treatment.getImagePath());
        response.setStatus(treatment.getStatus());

        return response;
    }
}