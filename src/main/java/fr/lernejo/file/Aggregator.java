package fr.lernejo.file;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class Aggregator {

    public static Optional<Double> aggregate(List<CsvRecord> records, LocalDateTime startDate, LocalDateTime endDate, String type, String dayNight) {
        return records.stream()
            .filter(r -> r.date.isAfter(startDate) && r.date.isBefore(endDate))
            .filter(r -> dayNight.equals("NIGHT") ? r.isDay == 0 : r.isDay == 1)
            .map(r -> {
                switch (type) {
                    case "temperature_2m":
                        return r.temperature;
                    case "wind_speed_10m":
                        return r.windSpeed;
                    case "pressure_msl":
                        return r.pressure;
                    default:
                        return null;
                }
            })
            .filter(value -> value != null)
            .reduce(Double::sum); // Adjust this based on the aggregation type (SUM, MIN, MAX, AVG)
    }
}
