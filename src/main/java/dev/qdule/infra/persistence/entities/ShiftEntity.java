package dev.qdule.infra.persistence.entities;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import dev.qdule.domain.model.ShiftStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.DayOfWeek;
import java.time.Duration;

@Entity
@Table(name = "shifts")
public class ShiftEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "rest_time_between_appointments")
    private Duration restTimeBetweenAppointments;

    @OneToMany(mappedBy = "shift", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShiftBreakEntity> breaks = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false, unique = true)
    private DayOfWeek dayOfWeek;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ShiftStatus status;

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public ShiftStatus getStatus() {
        return status;
    }

    public void setStatus(ShiftStatus status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Duration getRestTimeBetweenAppointments() {
        return restTimeBetweenAppointments;
    }

    public void setRestTimeBetweenAppointments(Duration restTimeBetweenAppointments) {
        this.restTimeBetweenAppointments = restTimeBetweenAppointments;
    }

    public List<ShiftBreakEntity> getBreaks() {
        return breaks;
    }

    public void setBreaks(List<ShiftBreakEntity> breaks) {
        this.breaks = breaks == null ? new ArrayList<>() : breaks;
    }

}
