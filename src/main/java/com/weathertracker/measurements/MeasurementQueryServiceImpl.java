package com.weathertracker.measurements;

import com.weathertracker.measurements.Measurement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Service class to query and return statistics.
 */
@Service
public class MeasurementQueryServiceImpl implements MeasurementQueryService {
    private HashMap<ZonedDateTime, Measurement> measurementHashMap;

    @Autowired
    public MeasurementQueryServiceImpl(HashMap<ZonedDateTime, Measurement> measurementHashMap) {
        this.measurementHashMap = measurementHashMap;
    }

    /**
     * Queries data between a given date range and returns a List of Measurement object
     * @param from Timestamp including
     * @param to Timestamp until. This will be excluded
     * @return List<Measurement objects>
     */
    public List<Measurement> queryDateRange(ZonedDateTime from, ZonedDateTime to) {
        List<Measurement> measurements = new ArrayList<>();
        Measurement measurement;

        Set<ZonedDateTime> zonedDateTimes = measurementHashMap.keySet();
        for (ZonedDateTime key : zonedDateTimes) {
            if (key.isEqual(from)) { // Note: This is added to include the "from" date
                measurement = measurementHashMap.get(key);
                measurements.add(measurement);
            }

            if (key.isAfter(from) && key.isBefore(to)) { // Note: Including "from" and excluding "to"
                measurement = measurementHashMap.get(key);
                measurements.add(measurement);
            }
        }

        return measurements;
    }
}
