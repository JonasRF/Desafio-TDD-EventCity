package com.devsuperior.demo.Services;

import com.devsuperior.demo.Services.Exceptions.DataBaseException;
import com.devsuperior.demo.Services.Exceptions.ResourceNoContentException;
import com.devsuperior.demo.Services.Exceptions.ResourceNotFoundException;
import com.devsuperior.demo.dto.CityDTO;
import com.devsuperior.demo.entities.City;
import com.devsuperior.demo.repositories.CityRepository;
import org.apache.catalina.webresources.EmptyResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.NotContextException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService {

    @Autowired
    private CityRepository repository;

    @Transactional(readOnly = true)
    public List<CityDTO> findAll(){
        List<City> list = repository.findAll(Sort.by("name"));
        return list.stream().map(x -> new CityDTO(x)).collect(Collectors.toList());
    }

    @Transactional
    public CityDTO insert(CityDTO dto) {

        City entity = new City();
        entity.setName(dto.getName());
        entity = repository.save(entity);
        return new CityDTO(entity);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        try{
            repository.deleteById(id);
        }

        catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Resource not found!");
        }

        catch (DataIntegrityViolationException e){
            throw new DataBaseException("Falha de integridade referencial!");
        }
    }
}
