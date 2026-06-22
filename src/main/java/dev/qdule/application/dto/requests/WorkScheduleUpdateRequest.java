package dev.qdule.application.dto.requests;

import java.time.DayOfWeek;

import dev.qdule.domain.model.Shift;

public class WorkScheduleUpdateRequest {
    private Shift shift;
    private DayOfWeek dayOfWeek;

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
