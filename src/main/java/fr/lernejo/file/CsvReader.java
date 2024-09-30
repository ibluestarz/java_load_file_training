package fr.lernejo.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    public static void main(String[] args) {
        if (args.length != 6) {
            System.out.println("Incorrect number of arguments.");
            System.exit(1);
        }

        String csvFile = args[0];
        String startDateStr = args[1];
        String endDateStr = args[2];
        String metric = args[3];
        String dayNight = args[4];
        String aggregation = args[5];

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            List<Double> values = new ArrayList<>();

            // Skip preamble (first 4 lines)
            for (int i = 0; i < 4; i++) {
                br.readLine();
            }

            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");
                LocalDateTime date = LocalDateTime.parse(columns[0], formatter);

                // Check date range and day/night filter
                if (date.isAfter(LocalDateTime.parse(startDateStr)) && date.isBefore(LocalDateTime.parse(endDateStr))) {
                    if ("DAY".equals(dayNight) && "1".equals(columns[7]) || "NIGHT".equals(dayNight) && "0".equals(columns[7])) {
                        double value = extractMetric(columns, metric);
                        values.add(value);
                    }
                }
            }

            double result = calculateAggregation(values, aggregation);
            System.out.println(result + " " + getMetricUnit(metric));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static double extractMetric(String[] columns, String metric) {
        switch (metric) {
            case "temperature_2m":
                return Double.parseDouble(columns[1]);
            case "pressure_msl":
                return Double.parseDouble(columns[3]);
            case "wind_speed_10m":
                return Double.parseDouble(columns[5]);
            case "direct_normal_irradiance_instant":
                return Double.parseDouble(columns[8]);
            default:
                throw new IllegalArgumentException("Unknown metric: " + metric);
        }
    }

    private static double calculateAggregation(List<Double> values, String aggregation) {
        switch (aggregation) {
            case "SUM":
                return values.stream().mapToDouble(Double::doubleValue).sum();
            case "AVG":
                return values.stream().mapToDouble(Double::doubleValue).average().orElse(0);
            case "MIN":
                return values.stream().mapToDouble(Double::doubleValue).min().orElse(0);
            case "MAX":
                return values.stream().mapToDouble(Double::doubleValue).max().orElse(0);
            default:
                throw new IllegalArgumentException("Unknown aggregation: " + aggregation);
        }
    }

    private static String getMetricUnit(String metric) {
        switch (metric) {
            case "temperature_2m":
                return "°C";
            case "pressure_msl":
                return "hPa";
            case "wind_speed_10m":
                return "km/h";
            case "direct_normal_irradiance_instant":
                return "W/m²";
            default:
                throw new IllegalArgumentException("Unknown metric: " + metric);
        }
    }
}
