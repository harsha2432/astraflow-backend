package com.astraflow.astraflow_backend.dto;

public class SaveChartRequest {

    private String birthDate;
    private String birthCity;
    private String birthCountry;
    private String chartResult;
    private String label;  // optional

    public SaveChartRequest() {}

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
}