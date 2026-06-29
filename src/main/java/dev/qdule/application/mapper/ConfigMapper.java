package dev.qdule.application.mapper;

import dev.qdule.application.dto.responses.ConfigResponse;
import dev.qdule.domain.model.Config;

public class ConfigMapper {
    public static ConfigResponse toResponse(Config config) {
        ConfigResponse response = new ConfigResponse();

        response.setId(config.getId());
        response.setSendEmail(config.getSendEmail());
        response.setContactLink(config.getContactLink());

        return response;
    }
}
