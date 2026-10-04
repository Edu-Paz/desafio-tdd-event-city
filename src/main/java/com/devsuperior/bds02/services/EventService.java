package com.devsuperior.bds02.services;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.parser.Entity;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final CityRepository cityRepository;

    public EventService(EventRepository eventRepository, CityRepository cityRepository) {
        this.eventRepository = eventRepository;
        this.cityRepository = cityRepository;
    }

    @Transactional
    public EventDTO update(Long id, EventDTO eventDTO) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));

        if (!cityRepository.existsById(eventDTO.getCityId())) {
            throw new ResourceNotFoundException("City not found with id " + eventDTO.getCityId());
        }

        event = eventRepository.save(copyDtoToEntity(eventDTO, event));

        return new EventDTO(event);
    }

    public Event copyDtoToEntity(EventDTO eventDTO, Event event) {
        event.setName(eventDTO.getName());
        event.setDate(eventDTO.getDate());
        event.setUrl(eventDTO.getUrl());
        event.setCity(cityRepository.getReferenceById(eventDTO.getCityId()));

        return event;
    }
}
