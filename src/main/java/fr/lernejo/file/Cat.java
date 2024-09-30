package fr.lernejo.file;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class Cat {

    public static void main(String[] args) {
        if (args.length != 5) {
            System.out.println("Usage: <start_date> <end_date> <type> <day_night> <aggregation>");
            return;
        }

        String startDateStr = args[0];
        String endDateStr = args[1];
        String type = args[2];
        String dayNight = args[3];
        String aggregation = args[4]; // Type d'agrégation : SUM, AVG, MIN, MAX

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime startDate, endDate;

        try {
            startDate = LocalDateTime.parse(startDateStr, formatter);
            endDate = LocalDateTime.parse(endDateStr, formatter);
        } catch (Exception e) {
            System.err.println("Invalid date format. Use yyyy-MM-dd.");
            return;
        }

        Reader reader = new Reader();
        List<CsvRecord> records = reader.readCsv("data.csv");

        Optional<Double> result = Aggregator.aggregate(records, startDate, endDate, type, dayNight, aggregation);

        result.ifPresentOrElse(
            r -> System.out.println("Result: " + r),
            () -> System.out.println("No matching records found.")
        );
    }
}
