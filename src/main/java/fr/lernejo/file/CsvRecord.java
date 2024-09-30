package fr.lernejo.file;

import java.time.LocalDateTime;

public class CsvRecord {
    public final LocalDateTime date;
    public final double temperature;
    public final double windSpeed;
    public final double pressure;
    public final int isDay;

    public CsvRecord(LocalDateTime date, double temperature, double windSpeed, double pressure, int isDay) {
        this.date = date;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.pressure = pressure;
        this.isDay = isDay;
    }
}
