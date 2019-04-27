package com.weathertracker.measurements;

import java.time.ZonedDateTime;
import java.util.HashMap;

/**
 * Interface to manage storage of measurements.
 */
public interface MeasurementStore {
  void add(Measurement measurement);

  Measurement fetch(ZonedDateTime timestamp);
}
