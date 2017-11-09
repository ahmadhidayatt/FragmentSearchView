package androlite.androidfragmentsearchview;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ahmad on 07/09/17.
 */

public class CommonMethods {
    private static DateFormat dateFormat = new SimpleDateFormat("d/MMM/yyyy");
    private static DateFormat timeFormat = new SimpleDateFormat("K:mm");
    private static DateFormat fulldateFormat = new SimpleDateFormat("d/MMM/yyyy HH:mm:ss");
    static SimpleDateFormat format_full = new SimpleDateFormat("d/MMM/yyyy HH:mm:ss", Locale.ENGLISH);

    public static String getCurrentTime() {

        Date today = Calendar.getInstance().getTime();
        return timeFormat.format(today);
    }

    public static String getCurrentDate() {

        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);
    }
    public static String getFullCurrentDate() {

        Date today = Calendar.getInstance().getTime();
        return fulldateFormat.format(today);
    }
    public static Date getFullParseCurrentDate(String date_parse) {

        Date tdate = null;
        try {
            tdate = format_full.parse(date_parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return tdate;
    }

}
