package com.weathertracker.statistics;

import com.weathertracker.statistics.Statistic;
import com.fasterxml.jackson.annotation.JsonGetter;
import io.swagger.annotations.ApiModelProperty;

/**
 * Model class to hold the computed aggregate result
 */
public class AggregateResult {
  @ApiModelProperty(notes = "Name of the metric")
  private String metric;

  @ApiModelProperty(notes = "Type of statistic")
  private Statistic statistic;

  @ApiModelProperty(notes = "Value of the statistic")
  private double value;

  public AggregateResult(String metric, Statistic statistic, double value) {
    this.metric = metric;
    this.statistic = statistic;
    this.value = value;
  }

  @JsonGetter("metric")
  public String getMetric() {
    return this.metric;
  }

  @JsonGetter("stat")
  public Statistic getStatistic() {
    return this.statistic;
  }

  @JsonGetter("value")
  public double getValue() {
    return this.value;
  }
}
