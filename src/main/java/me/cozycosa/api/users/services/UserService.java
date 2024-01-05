package me.cozycosa.api.users.services;

import me.cozycosa.api.exceptions.DuplicateException;
import me.cozycosa.api.notes.mappers.NoteMapper;
import me.cozycosa.api.users.DTO.UserDto;
import me.cozycosa.api.users.entities.UserEntity;
import me.cozycosa.api.users.mappers.UserMapper;
import me.cozycosa.api.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    NoteMapper noteMapper;

    @Autowired
    UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto create(UserDto user) {
        Optional<UserEntity> existingUser = repository.findByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            throw new DuplicateException(String.format("L'utilisateur avec l'email %s existe déjà", user.getEmail()));
        }

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        UserEntity savedUser = repository.save(userMapper.userDtoToEntity(user));

        return userMapper.userEntityToDto(savedUser);
    }
/*

    public UserDto update(UserDto newUser, Long id) {
        UserEntity foundUser = repository.findById(id).map(user -> {
            user.setUsername(newUser.getUsername());
            user.setEmail(newUser.getEmail());
            user.setPassword(newUser.getPassword());
            return repository.save(user);
        }).orElseGet(() -> {
            UserEntity newUserEntity = userMapper.userDtoToEntity(newUser);
            newUserEntity.setId(id);
            return repository.save(newUserEntity);
        });

        return userMapper.userEntityToDto(foundUser);
    }
*/

    public String delete(Long id) {
        repository.deleteById(id);

        return "L'utilisateur a été supprimé";
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = repository.findByEmail(email).orElseThrow();

        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    public UserEntity getUserBy(String email) {
        return repository.findByEmail(email).orElseThrow();
    }

    public UserDto getUserById(long id) {
        UserEntity userEntity = repository.findById(id).orElseThrow();

        UserDto user = userMapper.userEntityToDto(userEntity);

        return user;
    }
}
