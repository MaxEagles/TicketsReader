import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.FileNotFoundException;
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
        catch (JsonSyntaxException e) {
            System.out.println("Поменяйте кодировку файла tickets.json на ANSI");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printMinFlightTimes(Map<String, Integer> minFlightTimes) throws NoFlightException {
        if(minFlightTimes.size() == 0) {
            throw new NoFlightException(originName, destinationName);
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

    private static String readFile(String filename) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new File(filename))){
            StringBuilder stringBuilder = new StringBuilder();
            while (scanner.hasNext()) {
                stringBuilder.append(scanner.nextLine());
            }
            return stringBuilder.toString();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(e.getMessage());
        }

    }

    private static TicketsArray deserialize(String fileContent) throws JsonSyntaxException {
        Gson gson = new Gson();
        return gson.fromJson(fileContent, TicketsArray.class);
    }
}
