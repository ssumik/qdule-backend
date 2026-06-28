package dev.qdule.application.dto.responses;

import java.time.DayOfWeek;

import dev.qdule.domain.model.WorkScheduleStatus;

public class WorkScheduleResponse {
    private Long id;
    private Long shiftId;
    private DayOfWeek dayOfWeek;
    private WorkScheduleStatus status;

    public WorkScheduleResponse() {
    }

    public WorkScheduleResponse(Long id, Long shiftId, DayOfWeek dayOfWeek) {
        this(id, shiftId, dayOfWeek, WorkScheduleStatus.ENABLED);
    }

    public WorkScheduleResponse(Long id, Long shiftId, DayOfWeek dayOfWeek, WorkScheduleStatus status) {
        this.id = id;
        this.shiftId = shiftId;
        this.dayOfWeek = dayOfWeek;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShift() {
        return shiftId;
    }

    public void setShift(Long shiftId) {
        this.shiftId = shiftId;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public WorkScheduleStatus getStatus() {
        return status;
    }

    public void setStatus(WorkScheduleStatus status) {
        this.status = status;
    }
}
