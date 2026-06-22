package dev.qdule.application.services;

import dev.qdule.application.dto.requests.ChangelogCreateRequest;
import dev.qdule.application.dto.requests.ChangelogUpdateRequest;
import dev.qdule.application.dto.responses.PageResponse;
import dev.qdule.application.dto.responses.ChangelogResponse;
import dev.qdule.application.mapper.ChangelogMapper;
import dev.qdule.domain.model.Changelog;
import dev.qdule.domain.repository.ChangelogRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ChangelogService {
    private ChangelogRepository changelogRepository;

    @Inject
    public ChangelogService(ChangelogRepository changelogRepository) {
        this.changelogRepository = changelogRepository;
    }

    public PageResponse<ChangelogResponse> getChangelogs(int page, int size) {
        var changelogList = changelogRepository.findAll(page, size);

        PageResponse<ChangelogResponse> response = new PageResponse<>();

        response.setContent(
                changelogList.getContent()
                        .stream()
                        .map(ChangelogMapper::toResponse)
                        .toList());

        response.setPage(page);
        response.setSize(size);
        response.setTotalElements(changelogList.getTotalElements());
        response.setTotalPages(changelogList.getTotalPages());

        return response;
    }

    public ChangelogResponse getChangelogById(Long id) {
        Changelog changelog = changelogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Changelog not found"));
        return ChangelogMapper.toResponse(changelog);
    }

    @Transactional
    public ChangelogResponse createChangelog(ChangelogCreateRequest changelogRequest) {
        Changelog changelog = new Changelog(
                changelogRequest.getDateTime(),
                changelogRequest.getDescription(),
                changelogRequest.getScheduleId());

        var savedChangelog = changelogRepository.save(changelog);

        return ChangelogMapper.toResponse(savedChangelog);
    }

    @Transactional
    public ChangelogResponse updateChangelog(Long id, ChangelogUpdateRequest changelogRequest) {
        Changelog changelog = changelogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Changelog not found"));

        changelog.setDateTime(changelogRequest.getDateTime());
        changelog.setDescription(changelogRequest.getDescription());
        changelog.setScheduleId(changelogRequest.getScheduleId());

        var savedChangelog = changelogRepository.save(changelog);
        return ChangelogMapper.toResponse(savedChangelog);
    }

    @Transactional
    public void deleteChangelogById(Long id) {
        changelogRepository.removeById(id);
    }
}
