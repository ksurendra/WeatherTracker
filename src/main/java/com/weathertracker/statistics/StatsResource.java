package com.weathertracker.statistics;

import java.time.ZonedDateTime;
import java.util.List;

import com.weathertracker.measurements.Measurement;
import com.weathertracker.measurements.MeasurementQueryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stats")
@Api(value="StatsResource", description="Operations for Statistics")
public class StatsResource {
  private final MeasurementQueryService queryService;
  private final MeasurementAggregator aggregator;

  public StatsResource(MeasurementQueryService queryService, MeasurementAggregator aggregator) {
    this.queryService = queryService;
    this.aggregator = aggregator;
  }

  @ApiOperation(value="Get computed statistics based on user input", response = AggregateResult.class)
  @GetMapping
  public List<AggregateResult> getStats(
    @ApiParam(value = "List of Metrics", required = true) @RequestParam("metric") List<String> metrics,
    @ApiParam(value = "List of Stats", required = true) @RequestParam("stat") List<Statistic> stats,
    @ApiParam(value = "From Date", required = true) @RequestParam("fromDateTime") ZonedDateTime from,
    @ApiParam(value = "To Date", required = true) @RequestParam("toDateTime") ZonedDateTime to
    ) {
      List<Measurement> measurements = queryService.queryDateRange(from, to);
      return aggregator.analyze(measurements, metrics, stats);
  }
}
