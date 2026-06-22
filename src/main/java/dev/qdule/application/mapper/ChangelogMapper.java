package dev.qdule.application.mapper;

import dev.qdule.application.dto.responses.ChangelogResponse;
import dev.qdule.domain.model.Changelog;

public class ChangelogMapper {
    public static ChangelogResponse toResponse(Changelog changelog) {
        ChangelogResponse response = new ChangelogResponse();

        response.setId(changelog.getId());
        response.setDateTime(changelog.getDateTime());
        response.setDescription(changelog.getDescription());
        response.setScheduleId(changelog.getScheduleId());

        return response;
    }
}
