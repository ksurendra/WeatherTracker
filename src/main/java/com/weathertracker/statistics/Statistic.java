package com.weathertracker.statistics;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enumeration of Statistics
 */
public enum Statistic {
  @JsonProperty("min") MIN,
  @JsonProperty("max") MAX,
  @JsonProperty("average") AVERAGE,
}
