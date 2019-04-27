package com.weathertracker.measurements;

import java.util.List;
import java.time.ZonedDateTime;

/**
 * Interface to manage query stored values.
 */
public interface MeasurementQueryService {
  List<Measurement> queryDateRange(ZonedDateTime from, ZonedDateTime to);
}
