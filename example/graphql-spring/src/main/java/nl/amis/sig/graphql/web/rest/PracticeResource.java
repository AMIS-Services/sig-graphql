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

import nl.amis.sig.graphql.domain.Practice;
import nl.amis.sig.graphql.service.PracticeService;

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
    public ResponseEntity<List<Practice>> getPractices() {
        return new ResponseEntity<List<Practice>>(practiceService.getPractices(), HttpStatus.OK);
    }

    @GetMapping("/practices/{id}")
    public ResponseEntity<Practice> getPractice(@PathVariable Integer id) {
        return practiceService.getPractice(id).map(practice -> new ResponseEntity<Practice>(practice, HttpStatus.OK))
                .orElse(new ResponseEntity<Practice>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/practices")
    public ResponseEntity<Practice> createPractice(@RequestBody Practice newPractice) throws URISyntaxException {
        Practice practice = practiceService.createPractice(newPractice);
        return ResponseEntity.created(new URI("/api/practices/" + practice.getId())).body(practice);
    }

    @PutMapping("/practices/{id}")
    public ResponseEntity<Practice> updatePractice(@PathVariable Integer id, @RequestBody Practice practiceUpdate) {
        return practiceService.updatePractice(id, practiceUpdate)
                .map(practice -> new ResponseEntity<Practice>(practice, HttpStatus.OK))
                .orElse(new ResponseEntity<Practice>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/practices/{id}")
    public ResponseEntity<Void> deletePractice(@PathVariable Integer id) {
        return practiceService.deletePractice(id)
                .map(deletedPractice -> new ResponseEntity<Void>(HttpStatus.NO_CONTENT))
                .orElse(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }
}
