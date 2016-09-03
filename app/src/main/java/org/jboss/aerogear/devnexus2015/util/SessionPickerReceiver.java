package br.com.odra.devnexus.util;

import org.devnexus.vo.ScheduleItem;
import org.devnexus.vo.UserCalendar;

/**
 * Created by summers on 2/22/15.
 */
public interface SessionPickerReceiver {
    void receiveSessionItem(UserCalendar calendarItem, ScheduleItem session);

}
