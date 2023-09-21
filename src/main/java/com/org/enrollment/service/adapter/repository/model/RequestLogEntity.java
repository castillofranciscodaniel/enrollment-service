package com.org.enrollment.service.adapter.repository.model;

import com.org.enrollment.service.core.model.RequestLog;
import com.org.enrollment.service.core.model.RequestLogState;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

@Table("request_log")
public class RequestLogEntity {

    @Id
    private String id;

    @Column("request_number_a")
    private Double requestNumberA;

    @Column("request_number_b")
    private Double requestNumberB;

    private Double result;

    @Column("created_at")
    private LocalDateTime createdAt;

    private RequestLogState state;

    public RequestLogEntity() {
    }

    public RequestLogEntity(RequestLog requestLog) {
        BeanUtils.copyProperties(requestLog, this);
        this.setCreatedAt(requestLog.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
    }

    public RequestLogEntity(String id, Double requestNumberA, Double requestNumberB,
                            Double result, LocalDateTime createdAt, RequestLogState state) {
        this.id = id;
        this.requestNumberA = requestNumberA;
        this.requestNumberB = requestNumberB;
        this.result = result;
        this.createdAt = createdAt;
        this.state = state;
    }

    public RequestLog toModel() {
        var model = new RequestLog();
        BeanUtils.copyProperties(this, model);
        model.setCreatedAt(Date.from(this.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant()));

        return model;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getRequestNumberA() {
        return requestNumberA;
    }

    public void setRequestNumberA(Double requestNumberA) {
        this.requestNumberA = requestNumberA;
    }

    public Double getRequestNumberB() {
        return requestNumberB;
    }

    public void setRequestNumberB(Double requestNumberB) {
        this.requestNumberB = requestNumberB;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public RequestLogState getState() {
        return state;
    }

    public void setState(RequestLogState state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLogEntity that = (RequestLogEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(requestNumberA, that.requestNumberA) && Objects.equals(requestNumberB, that.requestNumberB) && Objects.equals(result, that.result) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, requestNumberA, requestNumberB, result, createdAt);
    }

    @Override
    public String toString() {
        return "RequestLogEntity{" +
                "id='" + id + '\'' +
                ", requestNumberA=" + requestNumberA +
                ", requestNumberB=" + requestNumberB +
                ", result=" + result +
                ", createdAt=" + createdAt +
                '}';
    }
}
