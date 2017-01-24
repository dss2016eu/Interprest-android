package coop.biantik.traductor.utils;

import android.content.Context;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import coop.biantik.traductor.R;

/**
 * Created by Sergio on 18/5/15.
 */
public class DateTimeUtils {

    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final DateFormat[] df = new DateFormat[] { new SimpleDateFormat("dd/MM/yyyy"), new SimpleDateFormat("HH:mm") };


    public static String getPostDisplayTime(Context context, Date dateStart, Date dateStop){
            Log.i("dateSTART", dateStart.toString());
            Log.i("dateSTOP", dateStop.toString());
            String result = "";

            try {

                //in milliseconds
                long diff =  dateStop.getTime() - dateStart.getTime();

                long diffSeconds = diff / 1000 % 60;
                long diffMinutes = diff / (60 * 1000) % 60;
                long diffHours = diff / (60 * 60 * 1000) % 24;
                long diffDays = diff / (24 * 60 * 60 * 1000);

                long diffMonths = diffDays / 30;
                long diffYears = diffDays / 365;


                if (diffYears >= 1 ){
                    result = diffYears + context.getString(R.string.year);
                }else if (diffMonths >= 1){
                    result = diffMonths + context.getString(R.string.month);
                }
                else if (diffDays >= 1){
                    result = diffDays + context.getString(R.string.day);
                }
                else if (diffHours >= 1){
                    result = diffHours + context.getString(R.string.hour);
                }
                else if (diffMinutes >= 1){
                    result = diffMinutes + context.getString(R.string.minute);
                }
                else{
                    result = diffSeconds + context.getString(R.string.second);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        return result;

    }

    public static String getDisplayTime(String datetime) {
        try {
            Date dt = DATE_FORMAT.parse(datetime);
            Date now = new Date();
            if (now.getYear() == dt.getYear() && now.getMonth() == dt.getMonth() && now.getDate() == dt.getDate()) {
                return df[1].format(dt);
            }
            return df[0].format(dt);
        } catch (ParseException e) {
            return datetime;
        }
    }

}
