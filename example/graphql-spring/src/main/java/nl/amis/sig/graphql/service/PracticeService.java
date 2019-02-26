package nl.amis.sig.graphql.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.amis.sig.graphql.domain.Practice;
import nl.amis.sig.graphql.repository.PracticeRepository;

@Service
@Transactional
public class PracticeService {

    private final PracticeRepository practiceRepository;

    public PracticeService(PracticeRepository practiceRepository) {
        this.practiceRepository = practiceRepository;
    }

    @Transactional(readOnly = true)
    public List<Practice> getPractices() {
        return practiceRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Practice> getPractice(Integer id) {
        return practiceRepository.findById(id);
    }

    public Practice createPractice(Practice newPractice) {
        newPractice.setCreatedAt(LocalDate.now());
        newPractice.setUpdatedAt(null);
        practiceRepository.save(newPractice);
        return newPractice;
    }

    public Optional<Practice> updatePractice(Integer id, Practice practiceUpdate) {
        return practiceRepository.findById(id).map(practice -> {
            practice.setName(practiceUpdate.getName());
            practice.setPeople(practiceUpdate.getPeople());
            practice.setProjects(practiceUpdate.getProjects());
            practice.setUpdatedAt(LocalDate.now());
            practiceRepository.save(practice);
            return practice;
        });
    }

    public Optional<Integer> deletePractice(Integer id) {
        return practiceRepository.findById(id).map(practice -> {
            practiceRepository.delete(practice);
            return practice.getId();
        });
    }
}