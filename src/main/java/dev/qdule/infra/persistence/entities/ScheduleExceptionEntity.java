package dev.qdule.infra.persistence.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "schedule_exceptions")
public class ScheduleExceptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date_time", nullable = false)
    private LocalDateTime startDateTime;

    @Column(name = "end_date_time", nullable = false)
    private LocalDateTime endDateTime;

    @Column(name = "reason", columnDefinition = "TEXT")
    private String reason;

    @OneToMany(mappedBy = "scheduleException", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScheduleExceptionBreakEntity> breaks = new ArrayList<>();

    public ScheduleExceptionEntity() {
    }

    public ScheduleExceptionEntity(Long id, LocalDateTime startDateTime, LocalDateTime endDateTime, String reason) {
        this.id = id;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.reason = reason;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<ScheduleExceptionBreakEntity> getBreaks() {
        return breaks;
    }

    public void setBreaks(List<ScheduleExceptionBreakEntity> breaks) {
        this.breaks = breaks == null ? new ArrayList<>() : breaks;
    }
}
