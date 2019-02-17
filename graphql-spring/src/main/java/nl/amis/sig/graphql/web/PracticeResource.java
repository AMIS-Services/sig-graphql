package nl.amis.sig.graphql.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.amis.sig.graphql.domain.Practice;
import nl.amis.sig.graphql.repository.PracticeRepository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
public class PracticeResource {

    private final Logger log = LoggerFactory.getLogger(PracticeResource.class);

    private final PracticeRepository practiceRepository;

    public PracticeResource(PracticeRepository practiceRepository) {

        this.practiceRepository = practiceRepository;
    }

    @GetMapping("/practices")
    public List<Practice> getAll() {
        log.debug("Rest call to get all practices");
        return practiceRepository.findAll();
    }
}
