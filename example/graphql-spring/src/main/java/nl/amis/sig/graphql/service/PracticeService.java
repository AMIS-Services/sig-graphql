package nl.amis.sig.graphql.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import nl.amis.sig.graphql.repository.PracticeRepository;
import nl.amis.sig.graphql.service.dto.PracticeDTO;
import nl.amis.sig.graphql.service.mapper.PracticeMapper;

@Service
public class PracticeService {

    private final PracticeRepository practiceRepository;

    private final PracticeMapper practiceMapper;

    public PracticeService(PracticeRepository practiceRepository, PracticeMapper practiceMapper) {
        this.practiceRepository = practiceRepository;
        this.practiceMapper = practiceMapper;
    }

    public List<PracticeDTO> getPractices() {
        return practiceMapper.toDto(practiceRepository.findAll());
    }

    public Optional<PracticeDTO> getPractice(Integer id) {
        return practiceRepository.findById(id).map(practice -> practiceMapper.toDto(practice));
    }
}