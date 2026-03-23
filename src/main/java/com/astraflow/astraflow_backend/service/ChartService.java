package com.astraflow.astraflow_backend.service;

import com.astraflow.astraflow_backend.dto.ChartResponse;
import com.astraflow.astraflow_backend.dto.SaveChartRequest;
import com.astraflow.astraflow_backend.model.BirthChart;
import com.astraflow.astraflow_backend.repository.BirthChartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChartService {

    @Autowired
    private BirthChartRepository chartRepository;

    /**
     * Save a birth chart for the logged-in user.
     * userId is extracted from JWT in the controller (not from request body).
     */
    public ChartResponse saveChart(Long userId, SaveChartRequest request) {

        // Validate inputs
        if (request.getChartResult() == null || request.getChartResult().isBlank()) {
            throw new RuntimeException("Chart result cannot be empty");
        }

        // Build entity
        BirthChart chart = new BirthChart();
        chart.setUserId(userId);
        chart.setBirthDate(request.getBirthDate());
        chart.setBirthCity(request.getBirthCity());
        chart.setBirthCountry(request.getBirthCountry());
        chart.setChartResult(request.getChartResult());
        chart.setLabel(request.getLabel() != null ? request.getLabel() : "My Chart");

        // Save to DB
        BirthChart saved = chartRepository.save(chart);

        return toResponse(saved);
    }

    /**
     * Get all charts for the logged-in user, newest first.
     */
    public List<ChartResponse> getUserCharts(Long userId) {
        return chartRepository
            .findByUserIdOrderBySavedAtDesc(userId)
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    /**
     * Delete a chart — only if it belongs to the logged-in user.
     */
    public void deleteChart(Long chartId, Long userId) {
        BirthChart chart = chartRepository.findByIdAndUserId(chartId, userId)
            .orElseThrow(() -> new RuntimeException("Chart not found or access denied"));

        chartRepository.delete(chart);
    }

    /**
     * Convert BirthChart entity to ChartResponse DTO.
     * Never expose internal entity directly to the client.
     */
    private ChartResponse toResponse(BirthChart chart) {
        return new ChartResponse(
            chart.getId(),
            chart.getBirthDate(),
            chart.getBirthCity(),
            chart.getBirthCountry(),
            chart.getChartResult(),
            chart.getLabel(),
            chart.getSavedAt()
        );
    }
}