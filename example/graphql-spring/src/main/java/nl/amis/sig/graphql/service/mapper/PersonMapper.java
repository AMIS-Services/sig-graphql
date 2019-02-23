package nl.amis.sig.graphql.service.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import nl.amis.sig.graphql.domain.Person;
import nl.amis.sig.graphql.service.dto.PersonDTO;

@Service
public class PersonMapper {
    public PersonDTO toDto(Person entity) {
        if (entity == null) {
            return null;
        } else {
            PersonDTO dto = new PersonDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setCreatedAt(entity.getCreatedAt());
            dto.setUpdatedAt(entity.getUpdatedAt());
            dto.setPractice(entity.getPractice());
            dto.setProjects(entity.getProjects());
            return dto;
        }
    }

    public List<PersonDTO> toDto(List<Person> entityList) {
        return entityList.stream().filter(Objects::nonNull).map(this::toDto).collect(Collectors.toList());
    }
}