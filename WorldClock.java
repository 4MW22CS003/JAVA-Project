import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class WorldClock {
    public static void main(String[] args) {
        Map<String, String> cities = readCitiesFromFile("C:\\Cities\\cities.txt");
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("Your local time: " + formatLocalDateTime(localDateTime));
        int count = 0;
        for (Map.Entry<String, String> entry : cities.entrySet()) {
            if (count >= 8) break; // Display only 8 cities
            String cityName = entry.getKey();
            String timeZone = entry.getValue();
            String cityTime = formatCityTime(localDateTime, timeZone);
            System.out.println(cityName + ": " + cityTime);
            count++;
        }
    }

    private static Map<String, String> readCitiesFromFile(String filename) {
        Map<String, String> cities = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    cities.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cities;
    }

    private static String formatLocalDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }

    private static String formatCityTime(LocalDateTime localDateTime, String timeZoneId) {
        ZoneId zoneId = ZoneId.of(timeZoneId);
        LocalDateTime cityDateTime = localDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(zoneId).toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return cityDateTime.format(formatter);
    }
}
