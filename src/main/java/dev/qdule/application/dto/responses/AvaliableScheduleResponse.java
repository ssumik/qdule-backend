package dev.qdule.application.dto.responses;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AvaliableScheduleResponse {
    private Long treatmentId;
    private String date;
    private List<LocalTime> hours = new ArrayList<>();

    public AvaliableScheduleResponse() {
    }

    public AvaliableScheduleResponse(Long treatmentId, String date, List<LocalTime> hours) {
        this.treatmentId = treatmentId;
        this.date = date;
        setHours(hours);
    }

    public Long getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(Long treatmentId) {
        this.treatmentId = treatmentId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<LocalTime> getHours() {
        return hours;
    }

    public void setHours(List<LocalTime> hours) {
        this.hours = hours;
    }
}
