package com.org.enrollment.service.infraestructure.delivery.dto;

public class CalculateRequest {

    private Double numberA;

    private Double numberB;

    public CalculateRequest() {
    }

    public CalculateRequest(Double numberA, Double numberB) {
        this.numberA = numberA;
        this.numberB = numberB;
    }

    public Double getNumberA() {
        return numberA;
    }

    public void setNumberA(Double numberA) {
        this.numberA = numberA;
    }

    public Double getNumberB() {
        return numberB;
    }

    public void setNumberB(Double numberB) {
        this.numberB = numberB;
    }

    @Override
    public String toString() {
        return "CalculateRequest{" +
                "numberA=" + numberA +
                ", numberB=" + numberB +
                '}';
    }
}
