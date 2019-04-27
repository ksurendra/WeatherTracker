package com.weathertracker.statistics;

import java.util.List;

import com.weathertracker.measurements.Measurement;

/**
 * Interface to manage measurement aggregators.
 */
public interface MeasurementAggregator {
  List<AggregateResult> analyze(List<Measurement> measurements, List<String> metrics, List<Statistic> stats);
}
