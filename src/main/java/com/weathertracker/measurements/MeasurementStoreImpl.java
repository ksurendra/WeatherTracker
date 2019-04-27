package com.weathertracker.measurements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.HashMap;

/**
 * Service class to store measurements.
 */
@Service
public class MeasurementStoreImpl implements MeasurementStore {

    private HashMap<ZonedDateTime, Measurement> measurementHashMap;

    @Autowired
    public MeasurementStoreImpl(HashMap<ZonedDateTime, Measurement> measurementHashMap) {
        this.measurementHashMap = measurementHashMap;
    }

    /**
     * Method to add a measurement
     * @param measurement The measurement to add
     */
    public void add(Measurement measurement) {
        measurementHashMap.put(measurement.getTimestamp(), measurement);
    }

    /**
     * Method to get a measurement at a given date/time
     * @param timestamp The timestamp at which a measurement needs to be searched.
     * @return Measurement object if found.
     */
    public Measurement fetch(ZonedDateTime timestamp) {
        for (ZonedDateTime zdt : measurementHashMap.keySet()) {
            if (timestamp.toInstant().compareTo(measurementHashMap.get(zdt).getTimestamp().toInstant()) == 0) {
                return measurementHashMap.get(zdt);
            }
        }

        return null;
    }

}
