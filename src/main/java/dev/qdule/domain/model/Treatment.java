package dev.qdule.domain.model;

import java.math.BigDecimal;
import java.time.Duration;

public class Treatment {
    private Long id;
    private String name;
    private String description;
    private Duration duration;
    private BigDecimal price;
    private String imagePath;
    private TreatmentStatus status;

    public Treatment(Long id, String name, String description, Duration duration, BigDecimal price, String imagePath,
            TreatmentStatus status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.price = price;
        this.imagePath = imagePath;
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
}
