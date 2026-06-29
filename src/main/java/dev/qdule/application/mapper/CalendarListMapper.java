package dev.qdule.application.mapper;

import java.util.List;

import dev.qdule.application.dto.responses.CalendarListResponse;
import dev.qdule.application.dto.responses.CalendarResponse;

public class CalendarListMapper {
    public static CalendarListResponse toResponse(List<CalendarResponse> calendarResponse) {
        return new CalendarListResponse(calendarResponse);
    }
}
