package com.weathertracker.measurements;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import io.swagger.annotations.ApiModelProperty;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

/**
 * Measurement model class to hold timestamp and metrics
 */
public class Measurement {
  @JsonProperty(value="timestamp") @NotNull
  @ApiModelProperty(notes = "The timestamp of the statistics")
  private ZonedDateTime timestamp;

  @JsonAnySetter
  @ApiModelProperty(notes = "The metrics of the statistics")
  private Map<String, Double> metrics = new HashMap<>();
  private Map<String, Double> metricsView;

  /**
   * Method to return stored timestamp
   * @return timestamp
   */
  public ZonedDateTime getTimestamp() {
    return timestamp;
  }

  /**
   * Method to return all stored metrics
   * @return Map<Name-of-the-metric, Value>
   */
  @JsonAnyGetter
  public Map<String, Double> getMetrics() {
    if (metricsView == null)
      metricsView = Collections.unmodifiableMap(metrics);

    return metricsView;
  }

  /**
   * Method to return the stored value of the given metric
   * @param metricName Name of the metric
   * @return Value of the stored metric
   */
  public Double getMetric(final String metricName) {
    return metrics.get(metricName);
  }

  /**
   * Helper builder static class
   */
  public static class Builder {
    private ZonedDateTime timestamp;
    private Map<String, Double> metrics = new HashMap<>();

    public Builder withTimestamp(final ZonedDateTime timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    public Builder withMetric(final String name, final Double value) {
      metrics.put(name, value);
      return this;
    }

    public Measurement build() {
      if (timestamp == null) {
        throw new IllegalArgumentException("Timestamp is required");
      }

      Measurement result = new Measurement();
      result.timestamp = timestamp;
      result.metrics = metrics;

      return result;
    }
  }
}
