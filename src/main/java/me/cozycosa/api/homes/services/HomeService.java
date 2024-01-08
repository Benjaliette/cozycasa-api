package me.cozycosa.api.homes.services;

import me.cozycosa.api.exceptions.RecordNotAllowedException;
import me.cozycosa.api.homes.DTO.HomeDto;
import me.cozycosa.api.homes.entities.HomeEntity;
import me.cozycosa.api.homes.mappers.HomeMapper;
import me.cozycosa.api.homes.repositories.HomeRepository;
import me.cozycosa.api.users.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class HomeService {
    @Autowired
    HomeMapper homeMapper;

    @Autowired
    HomeRepository homeRepository;

    public List<HomeDto> findAll() {
        List<HomeEntity> listHomes = homeRepository.findAll();

        return homeMapper.listEntityToListDto(listHomes);
    }

    public HomeDto findById(Long id) {
        HomeEntity homeEntity = homeRepository.findById(id).orElseThrow(() -> {
            return new RecordNotAllowedException(HttpStatus.FORBIDDEN.value(),
                    "Le foyer n'a pas pu être récupéré");
        });

        return homeMapper.homeEntityToDto(homeEntity);
    }

    public HomeDto create(HomeDto home, UserEntity currentUser) {
        try {
            HomeEntity homeEntity = homeMapper.homeDtoToEntity(home);

            if (homeEntity.getUsers() == null) {
                homeEntity.setUsers(new ArrayList<>(Arrays.asList(currentUser)));
            } else {
                homeEntity.getUsers().add(currentUser);
            }

            HomeEntity savedHome = homeRepository.save(homeEntity);

            return homeMapper.homeEntityToDto(savedHome);
        } catch (Exception e) {
            throw e;
        }
    }

    public HomeDto update(Long id, HomeDto home) {
        try {
            HomeEntity homeEntity = homeRepository.findById(id).orElseThrow(() -> {
                return new RecordNotAllowedException(HttpStatus.FORBIDDEN.value(),
                        "Le foyer n'a pas pu être récupéré");
            });

            homeEntity.setName(home.getName());
            HomeEntity updatedHomeEntity = homeRepository.save(homeEntity);

            return homeMapper.homeEntityToDto(updatedHomeEntity);
        } catch (Exception e) {
            throw e;
        }
    }

    public String delete(Long id) {
        try {
            homeRepository.deleteById(id);
            return "Le foyer a été supprimé";
        } catch (Exception e) {
            throw e;
        }
    }
}
