package dev.qdule.infra.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.DayOfWeek;

import dev.qdule.domain.model.WorkScheduleStatus;

@Entity
@Table(name = "work_schedules")
public class WorkScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shift_id", nullable = false)
    private ShiftEntity shift;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false, unique = true)
    private DayOfWeek dayOfWeek;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private WorkScheduleStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ShiftEntity getShift() {
        return shift;
    }

    public void setShift(ShiftEntity shift) {
        this.shift = shift;
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
