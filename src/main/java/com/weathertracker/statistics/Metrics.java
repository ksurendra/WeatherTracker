package com.weathertracker.statistics;

/**
 * Enumeration of Metrics
 */
public enum Metrics {
    TEMPERATURE("temperature"),
    DEWPOINT("dewPoint"),
    PRECIPITATION ("precipitation");

    private String metric;

    public String getMetric(){
        return this.metric;
    }

    Metrics(String metric){
        this.metric = metric;
    }
}
