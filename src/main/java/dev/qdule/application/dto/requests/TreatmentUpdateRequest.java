package dev.qdule.application.dto.requests;

import java.math.BigDecimal;
import java.time.Duration;

import dev.qdule.domain.model.TreatmentStatus;
import dev.qdule.domain.model.TreatmentType;

public class TreatmentUpdateRequest {
    private String name;
    private String description;
    private Duration duration;
    private BigDecimal price;
    private String imagePath;
    private TreatmentStatus status;
    private TreatmentType type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public TreatmentStatus getStatus() {
        return status;
    }

    public void setStatus(TreatmentStatus status) {
        this.status = status;
    }

    public TreatmentType getType() {
        return type;
    }

    public void setType(TreatmentType type) {
        this.type = type;
    }
}
