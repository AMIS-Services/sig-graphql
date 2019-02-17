package nl.amis.sig.graphql.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.amis.sig.graphql.repository.PracticeRepository;
import nl.amis.sig.graphql.service.dto.PracticeDTO;
import nl.amis.sig.graphql.service.mapper.PracticeMapper;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PracticeResource {

    private final PracticeRepository practiceRepository;

    private final PracticeMapper practiceMapper;

    public PracticeResource(PracticeRepository practiceRepository, PracticeMapper practiceMapper) {
        this.practiceRepository = practiceRepository;
        this.practiceMapper = practiceMapper;
    }

    @GetMapping("/practices")
    public List<PracticeDTO> getAll() {
        return practiceMapper.toDto(practiceRepository.findAll());
    }
}
