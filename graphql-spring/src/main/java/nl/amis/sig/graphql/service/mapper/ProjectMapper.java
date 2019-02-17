package nl.amis.sig.graphql.service.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import nl.amis.sig.graphql.domain.Project;
import nl.amis.sig.graphql.service.dto.ProjectDTO;

@Service
public class ProjectMapper {
    public ProjectDTO toDto(Project entity) {
        if (entity == null) {
            return null;
        } else {
            ProjectDTO dto = new ProjectDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setCreatedAt(entity.getCreatedAt());
            dto.setUpdatedAt(entity.getUpdatedAt());
            dto.setPeople(entity.getPeople());
            dto.setPractices(entity.getPractices());
            return dto;
        }
    }

    public List<ProjectDTO> toDto(List<Project> entityList) {
        return entityList.stream().filter(Objects::nonNull).map(this::toDto).collect(Collectors.toList());
    }
}