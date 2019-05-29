package ua.den.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.den.model.entity.tables.Cruise;
import ua.den.model.exception.NoResultException;
import ua.den.model.repository.CruiseRepository;

import java.util.List;

@Service
public class CruiseService {
    @Autowired
    private CruiseRepository cruiseRepository;

    public Cruise getCruiseById(Long id) {
        return cruiseRepository.getOne(id);
    }

    public List<Cruise> searchCruiseByCountry(String country) throws NoResultException {
        List<Cruise> cruiseList = cruiseRepository.getCruiseByCountry(country);

        if (cruiseList.isEmpty()) {
            throw new NoResultException();
        }

        return cruiseList;
    }

    public List<Cruise> getAllCruises() {
        return cruiseRepository.findAll();
    }
}
