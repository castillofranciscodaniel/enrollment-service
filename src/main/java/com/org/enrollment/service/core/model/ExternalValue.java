package com.org.enrollment.service.core.model;

import java.util.Date;

public class ExternalValue {

    private Double percentage;

    private Date createdAt;

    public ExternalValue() {
    }

    public ExternalValue(Double percentage) {
        this.percentage = percentage;
        this.createdAt = new Date();
    }

    public ExternalValue(Double percentage, Date createdAt) {
        this.percentage = percentage;
        this.createdAt = createdAt;
    }

    public Double getPercentage() {
        return percentage;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "ExternalValue{" +
                "percentage=" + percentage +
                ", createdAt=" + createdAt +
                '}';
    }
}
