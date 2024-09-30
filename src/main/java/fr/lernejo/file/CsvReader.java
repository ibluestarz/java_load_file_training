package fr.lernejo.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    public List<CsvRecord> readCsv(String filePath) {
        List<CsvRecord> records = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 5) {
                    LocalDateTime date = LocalDateTime.parse(values[0], formatter);
                    double temperature = Double.parseDouble(values[1]);
                    double windSpeed = Double.parseDouble(values[2]);
                    double pressure = Double.parseDouble(values[3]);
                    int isDay = Integer.parseInt(values[4]);

                    records.add(new CsvRecord(date, temperature, windSpeed, pressure, isDay));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }
}
