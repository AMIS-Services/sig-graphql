package nl.amis.sig.graphql.web.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.amis.sig.graphql.service.PracticeService;
import nl.amis.sig.graphql.service.dto.PracticeDTO;

import java.net.URI;
import java.net.URISyntaxException;
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

    @PostMapping("/practices")
    public ResponseEntity<PracticeDTO> createPractice(@RequestBody PracticeDTO practice) throws URISyntaxException {
        PracticeDTO createdPractice = practiceService.createPractice(practice);
        return ResponseEntity.created(new URI("/api/practices/" + createdPractice.getId())).body(createdPractice);
    }

    @PutMapping("/practices/{id}")
    public ResponseEntity<PracticeDTO> updatePractice(@PathVariable Integer id, @RequestBody PracticeDTO practice) {
        return practiceService.updatePractice(id, practice)
                .map(updatedPractice -> new ResponseEntity<PracticeDTO>(updatedPractice, HttpStatus.OK))
                .orElse(new ResponseEntity<PracticeDTO>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/practices/{id}")
    public ResponseEntity<Void> deletePractice(@PathVariable Integer id) {
        return practiceService.deletePractice(id)
                .map(deletedPractice -> new ResponseEntity<Void>(HttpStatus.NO_CONTENT))
                .orElse(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }
}
