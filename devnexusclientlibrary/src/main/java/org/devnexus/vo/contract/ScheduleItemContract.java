package org.devnexus.vo.contract;

import android.content.ContentValues;
import android.net.Uri;

import com.google.gson.Gson;

import org.devnexus.util.GsonUtils;
import org.devnexus.vo.Schedule;

/**
 * Created by summers on 2/14/14.
 */
public class ScheduleItemContract {
    public static final Uri URI = Uri.parse("content://org.devnexus.sync/ScheduleItem");
    public static final String _ID = "id";
    public static final String DATA = "DATA";

    public static final String NOTIFY = "NOTIFY";
    public static final String PRESENTATION_ID = "scheduleItemList.scheduleItems.presentation.id";
    public static final String TITLE = "scheduleItemList.scheduleItems.presentation.title";
    public static final String SPEAKER_FNAME = "scheduleItemList.scheduleItems.presentation.speaker.firstName";
    public static final String SPEAKER_NAME = "scheduleItemList.scheduleItems.presentation.speaker.lastName";

    public static final String[] COLUMNS = {DATA, NOTIFY};

    private static final Gson GSON = GsonUtils.GSON;
    public static final String FROM_TIME = "scheduleItemList.scheduleItems.fromTime";


    /**
     * This method will turn a schedule into the appropriate ContentValues for the app.
     *
     * @param schedule
     * @return
     */
    public static ContentValues valueize(Schedule schedule) {
        ContentValues values = new ContentValues();
        values.put(DATA, GSON.toJson(schedule));
        return values;
    }

    /**
     * This method will make a query out of the selection arguments.
     *
     * @param selection the selections you wish to to query
     * @return a string formatted $selection && $selection1 (ex presentation.speaker.firstName && presentation.speaker.lastName)
     */
    public static String toQuery(String... selection) {
        StringBuilder builder = new StringBuilder();
        String and = "";
        for ( String key : selection ) {
            builder.append(and);
            builder.append(key.trim());
            and = " && ";
        }

        return builder.toString();
    }
}
