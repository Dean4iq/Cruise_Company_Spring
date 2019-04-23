package ua.den.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.den.model.entity.tables.Excursion;
import ua.den.model.repository.ExcursionRepository;

import java.util.List;

@Service
public class ExcursionService {
    @Autowired
    private ExcursionRepository excursionRepository;

    public Excursion getExcursionById(Long excursionId) {
        return excursionRepository.getOne(excursionId);
    }

    public List<Excursion> getExcursionForCruise(Long cruiseId) {
        return excursionRepository.getExcursionsForCruise(cruiseId);
    }
}
