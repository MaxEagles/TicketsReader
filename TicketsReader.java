import com.google.gson.Gson;

import java.io.File;
import java.util.Map;
import java.util.Scanner;

public class TicketsReader {
    private static final String filename = "tickets.json";
    private static final String originName = "Владивосток";
    private static final String destinationName = "Тель-Авив";
    public static void main(String[] args) {
        try {
            TicketsArray tickets = deserialize(readFile(filename));
            Map<String, Integer> minFlightTimes = tickets.findMinFlightTimeForAllCarriers(originName, destinationName);
            printMinFlightTimes(minFlightTimes);
            double avgPrice = tickets.findAvgPrice(originName, destinationName);
            double medianPrice = tickets.findMedianPrice(originName, destinationName);
            double diff = Math.abs(avgPrice - medianPrice);
            System.out.println("Разница между средней ценой (" + avgPrice +
                    ") и медианой (" + medianPrice +
                    ") для полёта между городами " + originName +
                    " и " + destinationName +
                    " составляет " + diff);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printMinFlightTimes(Map<String, Integer> minFlightTimes) {
        if(minFlightTimes.size() == 0) {
            System.out.println("Не найдены маршруты между городами " + originName + " и " + destinationName);
        }
        else {
            System.out.println("Минимальное время полёта между городами " + originName + " и " + destinationName + ":");
            for(Map.Entry<String, Integer> e: minFlightTimes.entrySet()) {
                int hours = e.getValue() / 60;
                int minutes = e.getValue() % 60;
                System.out.println(e.getKey() + "\t" + hours + "ч.\t" + minutes + "мин.");
            }
        }
    }

    private static String readFile(String filename) throws Exception {
        try (Scanner scanner = new Scanner(new File(filename))){
            StringBuilder stringBuilder = new StringBuilder();
            while (scanner.hasNext()) {
                stringBuilder.append(scanner.nextLine());
            }
            return stringBuilder.toString();
        }
        catch (Exception e) {
            throw new Exception(e);
        }

    }

    private static TicketsArray deserialize(String fileContent) {
        Gson gson = new Gson();
        return gson.fromJson(fileContent, TicketsArray.class);
    }
}
