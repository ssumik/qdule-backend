package dev.qdule.application.dto.requests;

import java.time.DayOfWeek;

public class WorkScheduleCreateRequest {
    private long shiftId;
    private DayOfWeek dayOfWeek;

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
}
