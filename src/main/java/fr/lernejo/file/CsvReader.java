package fr.lernejo.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.System.exit;

public class CsvReader {
    public static void main(String[] args) throws FileNotFoundException, ParseException {
        if (args.length == 0) {
            System.out.println("Missing argument");
            exit(3);
        } else if (args.length > 6) {
            System.out.println("Too many arguments");
            exit(4);
        } else {
            File file = new File(args[0]);
            if (file.isDirectory()) {
                System.out.println("A file is required");
                exit(6);
            } else if (!file.exists()) {
                System.out.println("File not found");
                exit(5);
            }
            if (file.isFile()) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                Date startDate = formatter.parse(args[1]);
                Date endDate = formatter.parse(args[2]);
                String metricName = args[3];
                String selector = args[4];
                String aggregate = args[5];
                Scanner inputFile = new Scanner(file);
                Date lastDate = new Date();
                for (int i = 0 ; i < 4; i++) {
                    inputFile.nextLine();
                }
                ArrayList<Double> doubleValue = new ArrayList<>();
                while (inputFile.hasNext())
                {
                    String[] line = inputFile.nextLine().split(",");
                    if (line.length != 9) {
                        continue;
                    }
                    if (Objects.equals(selector, "NIGHT") && (Integer.parseInt(line[7]) == 1)) {
                        continue;
                    } else if (Objects.equals(selector, "DAY") && (Integer.parseInt(line[7]) == 0)) {
                        continue;
                    }
                    Date dateCsv = formatter.parse(line[0]);
                    if (( dateCsv.equals(startDate) || dateCsv.after(startDate)) && dateCsv.before(endDate))
                    {
                        if (dateCsv.compareTo(lastDate) != 0) {
                            lastDate = dateCsv;
                        }
                        switch (metricName) {
                            case "temperature_2m":
                                doubleValue.add(Double.parseDouble(line[1]));
                                break;
                            case "pressure_msl":
                                doubleValue.add(Double.parseDouble(line[3]));
                                break;
                            case "wind_speed_10m":
                                doubleValue.add(Double.parseDouble(line[5]));
                                break;
                            case "direct_normal_irradiance_instant":
                                doubleValue.add(Double.parseDouble(line[8]));
                                break;
                        }
                    }
                }
                double result = 0.0;
                if (Objects.equals(aggregate, "SUM") || Objects.equals(aggregate, "AVG")) {
                    for (Double aDouble : doubleValue) {
                        result = result + aDouble;
                    }
                    if (Objects.equals(aggregate, "AVG")) {
                        result = (result / (double) doubleValue.size());
                    }
                    System.out.print(Math.round(result * 10.0) / 10.0);
                } else if (Objects.equals(aggregate, "MIN")) {
                        Double min = doubleValue.getFirst();
                        for (Double aDouble : doubleValue) {
                            if (aDouble < min) {
                                min = aDouble;
                            }
                        }
                        System.out.print(Math.round(min * 10.0) / 10.0);
                } else {
                    //MAX
                    Double max = doubleValue.getFirst();
                    for (Double aDouble : doubleValue) {
                        if (aDouble > max) {
                            max = aDouble;
                        }
                    }
                    System.out.print(Math.round(max* 10.0) / 10.0);
                }
                switch (metricName) {
                    case "temperature_2m":
                        System.out.println(" °C");
                        break;
                    case "pressure_msl":
                        System.out.println(" hPa");
                        break;
                    case "wind_speed_10m":
                        System.out.println(" km/h");
                        break;
                    case "direct_normal_irradiance_instant":
                        System.out.println(" W/m²");
                        break;
                }
                inputFile.close();
            }
        }
    }
}
