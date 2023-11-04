package com.devsuperior.demo.Controllers;

import com.devsuperior.demo.Services.EventService;
import com.devsuperior.demo.dto.EventDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/events")
public class EventController {

    @Autowired
    private EventService service;

    @PutMapping(value = "/{id}")
    public ResponseEntity<EventDTO> update(@PathVariable Long id, @RequestBody EventDTO dto){
        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }
}
