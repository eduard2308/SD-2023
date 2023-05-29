package com.lab4.demo.user;

import com.lab4.demo.mail.MailService;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.mapper.UserMapper;
import com.lab4.demo.user.model.EStatus;
import com.lab4.demo.user.model.Status;
import com.lab4.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    private MailService mailService;

    public List<UserListDTO> allUsersForList() {
        return userRepository.findAll()
                .stream().map(userMapper::userListDtoFromUser)
                .collect(toList());
    }

    private User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

    public UserListDTO edit(Long id, UserListDTO userListDTO) {
        User actUser = findById(id);
        actUser.setUsername(userListDTO.getUsername());
        actUser.setEmail(userListDTO.getEmail());
        if(userListDTO.getStatusName().equals("BANNED")) {
            actUser.setStatus(new Status(2, EStatus.BANNED));
            mailService.sendEmail(actUser.getEmail(), "You have been banned from the forum");
        }
        else {
            actUser.setStatus(new Status(1, EStatus.ACTIVE));
            mailService.sendEmail(actUser.getEmail(), "You have been unbanned from the forum");
        }
        return userMapper.userListDtoFromUser(
                userRepository.save(actUser)
        );
    }

    public UserListDTO get(Long id) {
        return userMapper.userListDtoFromUser(findById(id));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

}
