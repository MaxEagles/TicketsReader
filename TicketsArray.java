import lombok.Data;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@Data
public class TicketsArray {
    private Ticket[] tickets;

    public Map<String, Integer> findMinFlightTimeForAllCarriers(String originName, String destinationName) throws ParseException {
        Map<String, Integer> results = new HashMap<>();
        for(Ticket e: tickets) {
            if(e.getOrigin_name().equals(originName) && e.getDestination_name().equals(destinationName)) {
                updateResults(results, e);
            }
        }
        return results;
    }

    private void updateResults(Map<String, Integer> results, Ticket ticket) throws ParseException {
        String carrier = ticket.getCarrier();
        int flightTime = ticket.getFlightTime();
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
