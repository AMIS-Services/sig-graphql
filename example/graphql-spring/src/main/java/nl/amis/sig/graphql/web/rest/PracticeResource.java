package nl.amis.sig.graphql.web.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.amis.sig.graphql.service.PracticeService;
import nl.amis.sig.graphql.service.dto.PracticeDTO;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PracticeResource {

    private final PracticeService practiceService;

    public PracticeResource(PracticeService practiceService) {
        this.practiceService = practiceService;
    }

    @GetMapping("/practices")
    public ResponseEntity<List<PracticeDTO>> getPractices() {
        return new ResponseEntity<List<PracticeDTO>>(practiceService.getPractices(), HttpStatus.OK);
    }

    @GetMapping("/practices/{id}")
    public ResponseEntity<PracticeDTO> getPractice(@PathVariable Integer id) {
        return practiceService.getPractice(id).map(practice -> new ResponseEntity<PracticeDTO>(practice, HttpStatus.OK))
                .orElse(new ResponseEntity<PracticeDTO>(HttpStatus.NOT_FOUND));
    }
}
