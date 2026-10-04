package com.devsuperior.bds02.services;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.services.exceptions.DatabaseException;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CityService {

    private final CityRepository cityRepository;
    private final EventRepository eventRepository;

    public CityService(CityRepository cityRepository, EventRepository eventRepository) {
        this.cityRepository = cityRepository;
        this.eventRepository = eventRepository;
    }

    @Transactional(readOnly = true)
    public List<CityDTO> findAll(){
        List<City> list = cityRepository.findAll(Sort.by("name"));

        return list.stream().map(CityDTO::new).toList();
    }

    @Transactional
    public CityDTO insert (CityDTO cityDTO){
        City city = new City();
        city.setName(cityDTO.getName());

        cityRepository.save(city);

        return new CityDTO(city);
    }

    @Transactional
    public void delete(Long id){
        if (!cityRepository.existsById(id)){
            throw new ResourceNotFoundException("City not found with id " + id);
        }
        if (eventRepository.existsByCityId(id)){
            throw new DatabaseException("Referential Integrity Fail");
        }
        cityRepository.deleteById(id);
    }
}
