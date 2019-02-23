package nl.amis.sig.graphql.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<List<PracticeDTO>> getAll() {
        final List<PracticeDTO> practices = practiceMapper.toDto(practiceRepository.findAll());
        return new ResponseEntity<List<PracticeDTO>>(practices, HttpStatus.OK);
    }

    @GetMapping("/practices/{id}")
    public ResponseEntity<PracticeDTO> getOne(@PathVariable Integer id) {
        return practiceRepository.findById(id)
                .map(practice -> new ResponseEntity<PracticeDTO>(practiceMapper.toDto(practice), HttpStatus.OK))
                .orElse(new ResponseEntity<PracticeDTO>(HttpStatus.NOT_FOUND));
    }
}
