package dev.qdule.application.dto.requests;

import java.time.DayOfWeek;

import dev.qdule.domain.model.WorkScheduleStatus;

public class WorkScheduleCreateRequest {
    private long shiftId;
    private DayOfWeek dayOfWeek;
    private WorkScheduleStatus status;

    public long getShiftId() {
        return shiftId;
    }

    public void setShiftId(long shiftId) {
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
