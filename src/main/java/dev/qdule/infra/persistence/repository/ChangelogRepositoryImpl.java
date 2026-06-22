package dev.qdule.infra.persistence.repository;

import java.util.Optional;

import dev.qdule.application.dto.responses.PageResponse;
import dev.qdule.application.exception.ChangelogNotFoundException;
import dev.qdule.domain.model.Changelog;
import dev.qdule.domain.repository.ChangelogRepository;
import dev.qdule.infra.mapper.ChangelogEntityMapper;
import dev.qdule.infra.persistence.entities.ChangelogEntity;
import dev.qdule.infra.persistence.panache.ChangelogRepositoryPanache;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ChangelogRepositoryImpl implements ChangelogRepository {
    private ChangelogRepositoryPanache changelogRepositoryPanache;

    @Inject
    public ChangelogRepositoryImpl(ChangelogRepositoryPanache changelogRepositoryPanache) {
        this.changelogRepositoryPanache = changelogRepositoryPanache;
    }

    @Override
    public Optional<Changelog> findById(Long id) {
        return Optional.ofNullable(
                changelogRepositoryPanache.findById(id))
                .map(ChangelogEntityMapper::toDomain);
    }

    @Override
    public Changelog save(Changelog changelog) {
        ChangelogEntity changelogEntity = changelogRepositoryPanache.findById(changelog.getId());
        if (changelogEntity == null) {
            changelogEntity = new ChangelogEntity();
        }
        var entity = ChangelogEntityMapper.toEntity(changelog, changelogEntity);
        var em = changelogRepositoryPanache.getEntityManager();
        var merged = em.merge(entity);
        return ChangelogEntityMapper.toDomain(merged);
    }

    @Override
    public void removeById(Long id) {
        if (!changelogRepositoryPanache.deleteById(id)) {
            throw new ChangelogNotFoundException(id);
        }
    }

    @Override
    public PageResponse<Changelog> findAll(int page, int size) {
        var pageResponse = new PageResponse<Changelog>();

        pageResponse.setContent(changelogRepositoryPanache
                .findAll()
                .page(page, size)
                .list()
                .stream()
                .map(ChangelogEntityMapper::toDomain)
                .toList());
        pageResponse.setPage(page);
        pageResponse.setSize(size);
        pageResponse.setTotalElements(changelogRepositoryPanache
                .findAll().count());
        pageResponse.setTotalPages(changelogRepositoryPanache.getEntityManager()
                .createQuery("SELECT COUNT(c) FROM ChangelogEntity c", Long.class)
                .getSingleResult()
                .intValue() / size);

        return pageResponse;
    }
}
