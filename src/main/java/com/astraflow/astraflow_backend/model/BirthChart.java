package com.astraflow.astraflow_backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "birth_charts")
public class BirthChart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Foreign key to users table
    @Column(name = "user_id", nullable = false)
    private Long userId;

    // Birth details used to generate the chart
    @Column(nullable = false)
    private String birthDate;

    @Column(nullable = false)
    private String birthCity;

    @Column(nullable = false)
    private String birthCountry;

    // The actual chart result from Gemini (stored as large text)
    @Column(columnDefinition = "TEXT", nullable = false)
    private String chartResult;

    // Optional: a short label the user can give this chart
    private String label;

    @Column(nullable = false, updatable = false)
    private LocalDateTime savedAt;

    @PrePersist
    protected void onCreate() {
        savedAt = LocalDateTime.now();
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getBirthDate() { return birthDate; }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }

    public String getBirthCity() { return birthCity; }
    public void setBirthCity(String birthCity) { this.birthCity = birthCity; }

    public String getBirthCountry() { return birthCountry; }
    public void setBirthCountry(String birthCountry) { this.birthCountry = birthCountry; }

    public String getChartResult() { return chartResult; }
    public void setChartResult(String chartResult) { this.chartResult = chartResult; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public LocalDateTime getSavedAt() { return savedAt; }
}