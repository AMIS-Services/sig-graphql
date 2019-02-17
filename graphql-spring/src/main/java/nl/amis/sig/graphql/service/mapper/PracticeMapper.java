package nl.amis.sig.graphql.service.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import nl.amis.sig.graphql.domain.Practice;
import nl.amis.sig.graphql.service.dto.PracticeDTO;

@Service
public class PracticeMapper {
    public PracticeDTO toDto(Practice entity) {
        if (entity == null) {
            return null;
        } else {
            PracticeDTO dto = new PracticeDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setCreatedAt(entity.getCreatedAt());
            dto.setUpdatedAt(entity.getUpdatedAt());
            dto.setPeople(entity.getPeople());
            dto.setProjects(entity.getProjects());
            return dto;
        }
    }

    public List<PracticeDTO> toDto(List<Practice> entityList) {
        return entityList.stream().filter(Objects::nonNull).map(this::toDto).collect(Collectors.toList());
    }
}