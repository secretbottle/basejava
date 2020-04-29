package ru.javawebinar.util;

import ru.javawebinar.model.Organization;

public class HtmlUtil {
    public static String formatDates(Organization.Position position) {
        return DateUtil.format(position.getStartPeriod()) + " - " + DateUtil.format(position.getEndPeriod());
    }
}
