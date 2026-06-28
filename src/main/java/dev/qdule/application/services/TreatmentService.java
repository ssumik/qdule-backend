package dev.qdule.application.services;

import dev.qdule.application.dto.requests.TreatmentCreateRequest;
import dev.qdule.application.dto.requests.TreatmentUpdateRequest;
import dev.qdule.application.dto.responses.PageResponse;
import dev.qdule.application.dto.responses.TreatmentResponse;
import dev.qdule.application.exception.TreatmentNotFoundException;
import dev.qdule.application.mapper.TreatmentMapper;
import dev.qdule.domain.model.Treatment;
import dev.qdule.domain.model.TreatmentType;
import dev.qdule.domain.repository.TreatmentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class TreatmentService {
    private TreatmentRepository treatmentRepository;

    @Inject
    public TreatmentService(TreatmentRepository treatmentRepository) {
        this.treatmentRepository = treatmentRepository;
    }

    public PageResponse<TreatmentResponse> getTreatments(int page, int size,TreatmentType type) {
        // DEPENDENDO DE COMO FOR FICANDO, DEVE TROCAR PARA LOGICA DE FILTRO USANDO OBJETOS
        PageResponse<Treatment> treatmentList;
        if (type == null){
            treatmentList = treatmentRepository.findAll(page, size);
        }else {
            treatmentList = treatmentRepository.findAllByType(page, size, type);
        }

        PageResponse<TreatmentResponse> response = new PageResponse<>();

        response.setContent(
                treatmentList.getContent()
                        .stream()
                        .map(TreatmentMapper::toResponse)
                        .toList());

        response.setPage(page);
        response.setSize(size);
        response.setTotalElements(treatmentList.getTotalElements());
        response.setTotalPages(treatmentList.getTotalPages());

        return response;
    }

    public TreatmentResponse getTreatmentById(Long id) {
        Treatment treatment = treatmentRepository.findById(id)
                .orElseThrow(() -> new TreatmentNotFoundException(id));
        return TreatmentMapper.toResponse(treatment);
    }

    @Transactional
    public TreatmentResponse createTreatment(TreatmentCreateRequest treatmentRequest) {
        Treatment treatment = new Treatment(
                treatmentRequest.getName(),
                treatmentRequest.getDescription(),
                treatmentRequest.getDuration(),
                treatmentRequest.getPrice(),
                treatmentRequest.getImagePath(),
                treatmentRequest.getStatus(),
                treatmentRequest.getType()
            );

        var savedTreatment = treatmentRepository.save(treatment);

        return TreatmentMapper.toResponse(savedTreatment);
    }

    @Transactional
    public void deleteTreatmentById(Long id) {
        treatmentRepository.removeById(id);
    }

    @Transactional
    public TreatmentResponse updateTreatment(Long id, TreatmentUpdateRequest treatmentRequest) {
        Treatment treatment = treatmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Treatment not found"));

        treatment.setName(treatmentRequest.getName());
        treatment.setDescription(treatmentRequest.getDescription());
        treatment.setDuration(treatmentRequest.getDuration());
        treatment.setPrice(treatmentRequest.getPrice());
        treatment.setImagePath(treatmentRequest.getImagePath());
        treatment.setStatus(treatmentRequest.getStatus());

        var savedTreatment = treatmentRepository.save(treatment);
        return TreatmentMapper.toResponse(savedTreatment);
    }

}
