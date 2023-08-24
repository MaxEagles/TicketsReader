import lombok.Data;

import java.util.*;

@Data
public class TicketsArray {
    private Ticket[] tickets;

    public Map<String, Integer> findMinFlightTimeForAllCarriers(String originName, String destinationName) throws Exception {
        Map<String, Integer> results = new HashMap<>();
        for(Ticket e: tickets) {
            if(e.getOrigin_name().equals(originName) && e.getDestination_name().equals(destinationName)) {
                updateResults(results, e);
            }
        }
        return results;
    }

    public double findAvgPrice(String originName, String destinationName) throws Exception {
        int sum = 0;
        int k = 0;
        for(Ticket e: tickets) {
            if(e.getOrigin_name().equals(originName) && e.getDestination_name().equals(destinationName)) {
                sum += e.getPrice();
                k++;
            }
        }
        if(k == 0) {
            throw new Exception("Нет подходящих рейсов");
        }
        return (sum * 1.0) / k;
    }

    public double findMedianPrice(String originName, String destinationName) throws Exception {
        List<Integer> prices = new ArrayList<>();
        for(Ticket e: tickets) {
            if(e.getOrigin_name().equals(originName) && e.getDestination_name().equals(destinationName)) {
                prices.add(e.getPrice());
            }
        }
        if(prices.size() == 0) {
            throw new Exception("Нет подходящих рейсов");
        }
        prices = prices.stream().sorted().toList();
        if(prices.size() % 2 == 0) {
            return (prices.get(prices.size() / 2) + prices.get(prices.size() / 2 - 1)) / (2 * 1.0);
        }
        else {
            return prices.get(prices.size() / 2);
        }
    }

    private void updateResults(Map<String, Integer> results, Ticket ticket) throws Exception {
        String carrier = ticket.getCarrier();
        int flightTime = ticket.getFlightTime();
        if(flightTime <= 0) {
            throw new Exception("Невозможное время полёта");
        }
        if(results.containsKey(carrier)) {
            int oldTime = results.get(carrier);
            if(flightTime < oldTime) {
                results.put(carrier, flightTime);
            }
        }
        else {
            results.put(carrier, flightTime);
        }
    }
}
