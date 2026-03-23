package com.astraflow.astraflow_backend.dto;

import java.time.LocalDateTime;

public class ChartResponse {

    private Long id;
    private String birthDate;
    private String birthCity;
    private String birthCountry;
    private String chartResult;
    private String label;
    private LocalDateTime savedAt;

    public ChartResponse() {}

    public ChartResponse(Long id, String birthDate, String birthCity,
                         String birthCountry, String chartResult,
                         String label, LocalDateTime savedAt) {
        this.id = id;
        this.birthDate = birthDate;
        this.birthCity = birthCity;
        this.birthCountry = birthCountry;
        this.chartResult = chartResult;
        this.label = label;
        this.savedAt = savedAt;
    }

    public Long getId() { return id; }
    public String getBirthDate() { return birthDate; }
    public String getBirthCity() { return birthCity; }
    public String getBirthCountry() { return birthCountry; }
    public String getChartResult() { return chartResult; }
    public String getLabel() { return label; }
    public LocalDateTime getSavedAt() { return savedAt; }
}