package project.Utility;

import java.sql.Timestamp;
import java.time.*;
import java.util.TimeZone;
/** Holds time conversion methods. */
public abstract class timeConversion {
    /** Converts the given date and time to eastern time zone.
     * @param localDateTime datetime to convert.
     * */
    public static LocalDateTime localToET(LocalDateTime localDateTime) {
        LocalDate inputDate = localDateTime.toLocalDate();
        LocalTime inputTime = localDateTime.toLocalTime();
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
        ZonedDateTime inputDateTime = ZonedDateTime.of(inputDate, inputTime, localZoneId);
        ZoneId easternTimeZone = ZoneId.of("America/New_York");

        ZonedDateTime localToEasternZDT = inputDateTime.withZoneSameInstant(easternTimeZone);
        LocalDateTime output = localToEasternZDT.toLocalDateTime();
        return output;
    }

}
