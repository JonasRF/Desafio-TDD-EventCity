package com.devsuperior.demo.Services;

import com.devsuperior.demo.Services.Exceptions.ResourceNotFoundException;
import com.devsuperior.demo.dto.EventDTO;
import com.devsuperior.demo.entities.City;
import com.devsuperior.demo.entities.Event;
import com.devsuperior.demo.repositories.EventRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventService {

    @Autowired
    private EventRepository repository;

    @Transactional
    public EventDTO update(Long id, EventDTO dto) {
        try {
            Event entity = repository.getReferenceById(id);
            entity.setName(dto.getName());
            entity.setDate(dto.getDate());
            entity.setUrl(dto.getUrl());
            entity.setCity(new City(dto.getCityId(), null));
            entity = repository.save(entity);
            return new EventDTO(entity);
        }catch(EntityNotFoundException e) {
            throw new ResourceNotFoundException("Entity not found! " + id);
        }
    }
}
