package dev.qdule.application.dto.responses;

import java.util.List;

public class CalendarListResponse {
    private List<CalendarResponse> calendarList;

    public CalendarListResponse(List<CalendarResponse> calendarList) {
        this.calendarList = calendarList;
    }

    public List<CalendarResponse> getCalendarList() {
        return calendarList;
    }

    public void setCalendarList(List<CalendarResponse> calendarList) {
        this.calendarList = calendarList;
    }
}
