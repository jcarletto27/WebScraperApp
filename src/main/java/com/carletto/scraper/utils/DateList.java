package com.carletto.scraper.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jcarlett on 3/8/2017.
 */
public class DateList {
    static HashMap<String, String> currentDates = new HashMap<>();
    static String startDay = "MasterPageReports_m_mFr_reportParameters1_startDay";
    static String endDay = "MasterPageReports_m_mFr_reportParameters1_endDay";
    static String startMonth = "MasterPageReports_m_mFr_reportParameters1_startMonth";
    static String endMonth = "MasterPageReports_m_mFr_reportParameters1_endMonth";
    static String startYear = "MasterPageReports_m_mFr_reportParameters1_startYear";
    static String endYear = "MasterPageReports_m_mFr_reportParameters1_endYear";
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");

    public static void addCurrentDatesPair(String s, String s1) {
        currentDates.put(s, s1);
    }

    public static HashMap<String, String> changedDates(String startDate, String endDate) {
        //expecting the strings like 2017-03-09

        currentDates.clear();

        List<String> elementList = Arrays.asList(startYear, startMonth, startDay, endYear, endMonth, endDate);

        String cat = startDate + "-" + endDate;
        String[] split = cat.split("-");

        for (int x = 0; x < elementList.size(); x++) {
            currentDates.put(elementList.get(x), String.valueOf(Integer.valueOf(split[x])));
        }

        return currentDates;
    }

    public static String startDate() {
        return currentDates.get(startYear) + "-" + currentDates.get(startMonth) + "-" + currentDates.get(startDay);
    }

    public static String endDate() {
        return currentDates.get(endYear) + "-" + currentDates.get(endMonth) + "-" + currentDates.get(endDay);
    }

    public static HashMap<String, String> getCurrentDates() {
        return currentDates;
    }

    public static LocalDate localDateParser(String dateString) {
        return LocalDate.parse(dateString, formatter);
    }

}
