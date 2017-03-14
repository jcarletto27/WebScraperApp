package Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jcarlett on 3/8/2017.
 */
public class DateList {
    public static HashMap<String, String> currentDates = new HashMap<String, String>();
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

        List<String> elementList = new ArrayList<String>();
        elementList.add(startYear);
        elementList.add(startMonth);
        elementList.add(startDay);
        elementList.add(endYear);
        elementList.add(endMonth);
        elementList.add(endDay);
        String cat = startDate + "-" + endDate;
        String[] split = cat.split("-");
        for (int x = 0; x < elementList.size(); x++) {
            currentDates.put(elementList.get(x), String.valueOf(Integer.valueOf(split[x])));
        }

        return currentDates;
    }

    public static String startDate() {
        String s = currentDates.get(startYear) + "-" + currentDates.get(startMonth) + "-" + currentDates.get(startDay);
        //System.out.println("StartDate-" + s);
        return s;
    }

    public static String endDate() {
        String s = currentDates.get(endYear) + "-" + currentDates.get(endMonth) + "-" + currentDates.get(endDay);
        //System.out.println("EndDate-" + s);
        return s;
    }

    public static HashMap<String, String> getCurrentDates() {
        return currentDates;
    }

    public static LocalDate localDateParser(String dateString) {

        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }


    public static LocalDate currentStartDate() {
        int day = Integer.getInteger(currentDates.get(startDay));
        int month = Integer.getInteger(currentDates.get(startMonth));
        int year = Integer.getInteger(currentDates.get(startYear));
        return LocalDate.of(year, month, day);
    }

    public static LocalDate currentEndDate() {
        int day = Integer.getInteger(currentDates.get(endDay));
        int month = Integer.getInteger(currentDates.get(endMonth));
        int year = Integer.getInteger(currentDates.get(endYear));
        return LocalDate.of(year, month, day);
    }


    public static LocalDate convertStringToDate(String string) {
        int day = Integer.getInteger(getDay(string));
        int month = Integer.getInteger(getMonth(string));
        int year = Integer.getInteger(getYear(string));
        return LocalDate.of(year, month, day);
    }


    public static String getDay(String dateValue) {
        LocalDate date = LocalDate.parse(dateValue, formatter);
        return String.valueOf(date.getDayOfMonth());
    }

    public static String getMonth(String dateValue) {
        LocalDate date = LocalDate.parse(dateValue, formatter);
        return String.valueOf(date.getMonth());
    }

    public static String getYear(String dateValue) {
        LocalDate date = LocalDate.parse(dateValue, formatter);
        return String.valueOf(date.getYear());
    }


}
