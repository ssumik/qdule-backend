package dev.qdule.application.services;

import dev.qdule.application.dto.requests.ConfigUpdateRequest;
import dev.qdule.application.dto.responses.ConfigResponse;
import dev.qdule.application.mapper.ConfigMapper;
import dev.qdule.domain.model.Config;
import dev.qdule.domain.repository.ConfigRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ConfigService {
    private ConfigRepository configRepository;

    @Inject
    public ConfigService(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    public ConfigResponse getConfig() {
        Config config = configRepository
                .findCurrent()
                .orElse(new Config(null, null));

        return ConfigMapper.toResponse(config);
    }

    @Transactional
    public ConfigResponse updateConfig(ConfigUpdateRequest request) {
        Config config = configRepository
                .findCurrent()
                .orElse(new Config());

        config.setSendEmail(request.getSendEmail());
        config.setContactLink(request.getContactLink());

        var savedConfig = configRepository.save(config);

        return ConfigMapper.toResponse(savedConfig);
    }
}
