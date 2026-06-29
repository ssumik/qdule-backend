package dev.qdule.application.services;

import java.time.ZonedDateTime;

import dev.qdule.application.dto.requests.ScheduleExceptionCreateRequest;
import dev.qdule.application.dto.requests.ScheduleExceptionUpdateRequest;
import dev.qdule.application.dto.responses.PageResponse;
import dev.qdule.application.dto.responses.ScheduleExceptionResponse;
import dev.qdule.application.exception.ConflictException;
import dev.qdule.application.exception.ScheduleExceptionNotFoundException;
import dev.qdule.application.mapper.ScheduleExceptionMapper;
import dev.qdule.domain.model.ScheduleException;
import dev.qdule.domain.repository.ScheduleExceptionRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ScheduleExceptionService {
    private ScheduleExceptionRepository scheduleExceptionRepository;

    @Inject
    public ScheduleExceptionService(ScheduleExceptionRepository scheduleExceptionRepository) {
        this.scheduleExceptionRepository = scheduleExceptionRepository;
    }

    public PageResponse<ScheduleExceptionResponse> getScheduleExceptions(int page, int size) {
        PageResponse<ScheduleException> scheduleExceptionList = scheduleExceptionRepository.findAll(page, size);

        PageResponse<ScheduleExceptionResponse> response = new PageResponse<>();

        response.setContent(
                scheduleExceptionList.getContent()
                        .stream()
                        .map(ScheduleExceptionMapper::toResponse)
                        .toList());

        response.setPage(page);
        response.setSize(size);
        response.setTotalElements(scheduleExceptionList.getTotalElements());
        response.setTotalPages(scheduleExceptionList.getTotalPages());

        return response;
    }

    public ScheduleExceptionResponse getScheduleExceptionById(Long id) {
        ScheduleException scheduleException = scheduleExceptionRepository.findById(id)
                .orElseThrow(() -> new ScheduleExceptionNotFoundException(id));
        return ScheduleExceptionMapper.toResponse(scheduleException);
    }

    @Transactional
    public ScheduleExceptionResponse createScheduleException(ScheduleExceptionCreateRequest request) {
        validatePeriod(request.getStartDateTime(), request.getEndDateTime());

        ScheduleException scheduleException = new ScheduleException(
                request.getStartDateTime(),
                request.getEndDateTime(),
                request.getReason());

        var savedScheduleException = scheduleExceptionRepository.save(scheduleException);

        return ScheduleExceptionMapper.toResponse(savedScheduleException);
    }

    @Transactional
    public ScheduleExceptionResponse updateScheduleException(Long id, ScheduleExceptionUpdateRequest request) {
        ScheduleException scheduleException = scheduleExceptionRepository.findById(id)
                .orElseThrow(() -> new ScheduleExceptionNotFoundException(id));

        validatePeriod(request.getStartDateTime(), request.getEndDateTime());

        scheduleException.setStartDateTime(request.getStartDateTime());
        scheduleException.setEndDateTime(request.getEndDateTime());
        scheduleException.setReason(request.getReason());

        var savedScheduleException = scheduleExceptionRepository.save(scheduleException);

        return ScheduleExceptionMapper.toResponse(savedScheduleException);
    }

    @Transactional
    public void deleteScheduleExceptionById(Long id) {
        scheduleExceptionRepository.removeById(id);
    }

    private void validatePeriod(ZonedDateTime startDateTime, ZonedDateTime endDateTime) {
        if (startDateTime == null || endDateTime == null) {
            throw new ConflictException("Schedule exception start date and end date are required");
        }

        if (!endDateTime.isAfter(startDateTime)) {
            throw new ConflictException("The schedule exception end date must be after the start date");
        }
    }
}
