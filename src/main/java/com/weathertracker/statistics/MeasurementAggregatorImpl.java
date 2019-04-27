package com.weathertracker.statistics;

import com.weathertracker.measurements.Measurement;
import com.weathertracker.statistics.AggregateResult;
import com.weathertracker.statistics.Metrics;
import com.weathertracker.statistics.Statistic;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Service class to manage measurement aggregations.
 */
@Service
public class MeasurementAggregatorImpl implements MeasurementAggregator {

    private boolean checkTemperature;
    private boolean checkDewPoint;
    private boolean checkPrecipitation;

    private double temperatureTotal;
    private double dewPointTotal;
    private double precipitationTotal;

    private double temperatureMax = Double.MIN_VALUE;
    private double temperatureLow = Double.MAX_VALUE;
    private double temperatureCount = 0;

    private double dewPointLow = Double.MAX_VALUE;
    private double dewPointMax = Double.MIN_VALUE;
    private double dewPointCount = 0;

    private double precipitationMax = Double.MIN_VALUE;
    private double precipitationLow = Double.MAX_VALUE;
    private double precipitationCount = 0;

    /**
     * Method to retun the computed aggreation results
     * @param measurements List of measurements to be included in the reuslt
     * @param metrics List of metrics to be incluced in the result
     * @param stats List of stats to be included in the result.
     * @return List of AggregationResults
     */
    public List<AggregateResult> analyze(List<Measurement> measurements, List<String> metrics, List<Statistic> stats) {
        metricsToCheck(metrics);
        List<AggregateResult> aggregateResults = new ArrayList<>();

        for (Measurement measurement : measurements) {

            Double currentTemperature = measurement.getMetric(Metrics.TEMPERATURE.getMetric());
            System.out.println("currentTemperature="+currentTemperature);

            Double currentDewPoint = measurement.getMetric(Metrics.DEWPOINT.getMetric());
            Double currentPrecipitation = measurement.getMetric(Metrics.PRECIPITATION.getMetric());

            if (currentTemperature != null) {
                temperatureTotal += currentTemperature;
                temperatureCount++;
                temperatureLow = Math.min(currentTemperature, temperatureLow);
                temperatureMax = Math.max(currentTemperature, temperatureMax);
            }

            if (currentDewPoint != null) {
                dewPointTotal += currentDewPoint;
                dewPointCount++;
                dewPointLow = Math.min(currentDewPoint, dewPointLow);
                dewPointMax = Math.max(currentDewPoint, dewPointMax);
            }

            if (currentPrecipitation != null) {
                precipitationTotal += currentPrecipitation;
                precipitationCount++;
                precipitationLow = Math.min(currentPrecipitation, precipitationLow);
                precipitationMax = Math.max(currentPrecipitation, precipitationMax);
            }
        }

        loadAggregateResults(stats, aggregateResults);
        return aggregateResults;
    }

    /**
     * Method to identfy the metric to use.
     * @param metrics List of metric names - Temperature, DewPoint, Precipitation
     */
    private void metricsToCheck(List<String> metrics) {
        for (String metric : metrics) {
            if (metric.equals(Metrics.TEMPERATURE.getMetric())) {
                checkTemperature = true;
            } else if (metric.equals(Metrics.DEWPOINT.getMetric())) {
                checkDewPoint = true;
            } else if (metric.equals(Metrics.PRECIPITATION.getMetric())) {
                checkPrecipitation = true;
            }
        }
    }


    /**
     * Interim method to compute aggregate results
     * @param stats List of statistics to be used in the result
     * @param aggregateResults List of aggregate results for each statistic
     */
    private void loadAggregateResults(List<Statistic> stats, List<AggregateResult> aggregateResults) {
        if (checkTemperature && temperatureCount > 0) {
            loadAggregateResultsByMetric(stats, temperatureLow, temperatureMax, temperatureTotal / temperatureCount, Metrics.TEMPERATURE.getMetric(), aggregateResults);
        }
        if (checkDewPoint && dewPointCount > 0) {
            loadAggregateResultsByMetric(stats, dewPointLow, dewPointMax, dewPointTotal / dewPointCount, Metrics.DEWPOINT.getMetric(), aggregateResults);
        }
        if (checkPrecipitation && precipitationCount > 0) {
            loadAggregateResultsByMetric(stats, precipitationLow, precipitationMax, precipitationTotal / precipitationCount, Metrics.PRECIPITATION.getMetric(), aggregateResults);
        }
    }

    /**
     * Method to compute aggregate results for each metrics requested by the user
     * @param stats List of statistics to be used in the result
     * @param low The lowest value of a particular metric
     * @param high The heightest value of a particular metric
     * @param count The average of a particular metric
     * @param metric Name of the metric
     * @param aggregateResults List of aggregate results
     */
    private void loadAggregateResultsByMetric(List<Statistic> stats, double low, double high, double count, String metric, List<AggregateResult> aggregateResults) {
        BigDecimal bigDecimalCalc;

        for (Statistic stat : stats) {
            if (stat.equals(Statistic.MIN)) {
                aggregateResults.add(new AggregateResult(metric, Statistic.MIN, low));
            } else if (stat.equals(Statistic.MAX)) {
                aggregateResults.add(new AggregateResult(metric, Statistic.MAX, high));
            } else if (stat.equals(Statistic.AVERAGE)) {
                // Note: Process to round the average to 1 decimal, as per business rules.
                bigDecimalCalc = new BigDecimal(count).setScale(1, RoundingMode.HALF_DOWN);
                count = bigDecimalCalc.doubleValue();

                aggregateResults.add(new AggregateResult(metric, Statistic.AVERAGE, count));
            }
        }
    }
}

