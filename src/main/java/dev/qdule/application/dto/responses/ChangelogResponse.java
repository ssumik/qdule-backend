package dev.qdule.application.dto.responses;

import java.time.LocalDateTime;

public class ChangelogResponse {
    private Long id;
    private LocalDateTime dateTime;
    private String description;
    private Long scheduleId;

    public ChangelogResponse() {
    }

    public ChangelogResponse(Long id, LocalDateTime dateTime, String description, Long scheduleId) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.scheduleId = scheduleId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }
}
