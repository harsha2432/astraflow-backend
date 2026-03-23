package com.astraflow.astraflow_backend.repository;

import com.astraflow.astraflow_backend.model.BirthChart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BirthChartRepository extends JpaRepository<BirthChart, Long> {

    // Get all charts for a specific user, newest first
    List<BirthChart> findByUserIdOrderBySavedAtDesc(Long userId);

    // Find a chart by ID — but only if it belongs to the given user (security)
    Optional<BirthChart> findByIdAndUserId(Long id, Long userId);

    // Count how many charts a user has saved
    long countByUserId(Long userId);
}