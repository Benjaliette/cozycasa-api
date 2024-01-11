package me.cozycosa.api.events.services;

import me.cozycosa.api.events.DTO.EventDto;
import me.cozycosa.api.events.entities.EventEntity;
import me.cozycosa.api.events.mappers.EventMapper;
import me.cozycosa.api.events.repositories.EventRepository;
import me.cozycosa.api.exceptions.RecordNotAllowedException;
import me.cozycosa.api.homes.entities.HomeEntity;
import me.cozycosa.api.homes.repositories.HomeRepository;
import me.cozycosa.api.users.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    @Autowired
    EventRepository eventRepository;

    @Autowired
    HomeRepository homeRepository;

    @Autowired
    EventMapper eventMapper;

    public List<EventDto> findAll(Long homeId) {
        List<EventEntity> eventEntitiesList = eventRepository.findAll(homeId);

        return eventMapper.listEventEntityToListDto(eventEntitiesList);
    }

    public EventDto findById(Long id, Long homeId) {
        EventEntity eventEntity = eventRepository.findById(id, homeId).orElseThrow(() -> {
            return new RecordNotAllowedException(HttpStatus.FORBIDDEN.value(),
                    "L'évènement n'a pas pu être récupéré");
        });

        return eventMapper.eventEntityToDto(eventEntity);
    }

    public EventDto create(EventDto eventDto, UserEntity userEntity, Long homeId) {
        EventEntity eventEntity = eventMapper.eventDtoToEntity(eventDto);
        eventEntity.setUser(userEntity);

        HomeEntity homeEntity = homeRepository.findById(homeId).orElseThrow(() -> {
            return new RecordNotAllowedException(HttpStatus.FORBIDDEN.value(),
                    "Le foyer n'a pas pu être récupéré");
        });
        eventEntity.setHome(homeEntity);

        EventEntity savedEntity = eventRepository.save(eventEntity);

        return eventMapper.eventEntityToDto(savedEntity);
    }

    public EventDto update(EventDto eventDto, Long id) {
        EventEntity eventEntity = eventRepository.findById(id).orElseThrow(() -> {
            return new RecordNotAllowedException(HttpStatus.FORBIDDEN.value(),
                    "L'évènement n'a pas pu être récupéré");
        });

        eventEntity.setTitle(eventDto.getTitle());
        eventEntity.setContent(eventDto.getContent());
        eventEntity.setStartDate(eventDto.getStartDate());
        eventEntity.setEndDate(eventDto.getEndDate());

        EventEntity updatedEntity = eventRepository.save(eventEntity);

        return eventMapper.eventEntityToDto(updatedEntity);
    }

    public String delete(Long id) {
        eventRepository.deleteById(id);

        return "L'évènement a été supprimé";
    }
}
