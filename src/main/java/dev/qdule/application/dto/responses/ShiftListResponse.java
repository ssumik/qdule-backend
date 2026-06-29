package dev.qdule.application.dto.responses;

import java.util.List;

public class ShiftListResponse {
    List<ShiftResponse> workShifts;

    public List<ShiftResponse> getWorkShifts() {
        return workShifts;
    }

    public void setWorkShifts(List<ShiftResponse> workShifts) {
        this.workShifts = workShifts;
    }
}
