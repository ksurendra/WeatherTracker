package com.weathertracker.measurements;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;

@RestController
@RequestMapping("/measurements")
@Api(value="MeasurementsResource", description="Operations for Measurements")
public class MeasurementsResource {
  private final MeasurementStore store;
  private final DateTimeFormatter dateTimeFormatter;

  public MeasurementsResource(MeasurementStore store, DateTimeFormatter dateTimeFormatter) {
    this.store = store;
    this.dateTimeFormatter = dateTimeFormatter;
  }

  // features/01-measurements/01-add-measurement.feature
  @ApiOperation(value="Create Measurements")
  @PostMapping
  public ResponseEntity<?> createMeasurement(@Valid @RequestBody Measurement measurement) {
    store.add(measurement);

    return ResponseEntity
      .created(URI.create("/measurements/" +  dateTimeFormatter.format(measurement.getTimestamp())))
      .build();
  }

  // features/01-measurements/02-get-measurement.feature
  @ApiOperation(value="Get Measurements Based on a Timestamp", response = Measurement.class)
  @GetMapping("/{timestamp:.+}")
  public ResponseEntity<Measurement> getMeasurement(@PathVariable ZonedDateTime timestamp) {
    Measurement measurement = store.fetch(timestamp);

    if (measurement != null) {
      return ResponseEntity.ok(measurement);
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
