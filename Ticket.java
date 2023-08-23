import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class Ticket {
    private String origin;
    private String origin_name;
    private String destination;
    private String destination_name;
    private String departure_date;
    private String departure_time;
    private String arrival_date;
    private String arrival_time;
    private String carrier;
    private int stops;
    private int price;

    public int getFlightTime() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yy HH:mm");
        Date start = simpleDateFormat.parse(this.departure_date + " " + this.departure_time);
        Date finish = simpleDateFormat.parse(this.arrival_date + " " + this.arrival_time);

        return (int)(finish.getTime() - start.getTime()) / 60000;
    }
}
