package com.GreenCycleSolutions.gcsbackend.service;

import com.GreenCycleSolutions.gcsbackend.dto.UserDTO;
import com.GreenCycleSolutions.gcsbackend.entity.UserEntity;
import com.GreenCycleSolutions.gcsbackend.exception.AccountGenerationException;
import com.GreenCycleSolutions.gcsbackend.exception.UsernameAlreadyExistsException;
import com.GreenCycleSolutions.gcsbackend.repository.UserRepository;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AccountGenerationService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AccountGenerationService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public String generateUsername(String firstName, String lastName) {
        var users = userRepository.findAll();
        List<String> usernames = users.stream().map(UserEntity::getUsername).toList();
        String username = String.format("%s_%s", firstName, lastName);
        var index = 1;
        while (usernames.contains(username)) {
            username = String.format("%s_%s%d", firstName, lastName, index);
            index++;
        }
        return username;
    }

    public void changeUsername(String oldUsername, String newUsername) {
        var users = userRepository.findAll();
        List<String> usernames = users.stream().map(UserEntity::getUsername).toList();
        if(!usernames.contains(newUsername)) {
            var userOptional = userRepository.findByUsername(oldUsername);
            if(userOptional.isPresent()) {
                var user = userOptional.get();
                user.setUsername(newUsername);
                userRepository.save(user);
            } else {
                throw new AccountGenerationException("The old username value is invalid");
            }
        } else {
            throw new UsernameAlreadyExistsException("This username is already used. Try again!");
        }
    }

    public void changePassword(String username, String newPassword) {
        var userOptional = userRepository.findByUsername(username);
        if(userOptional.isPresent()) {
            var user = userOptional.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        } else {
            throw new AccountGenerationException("The username value is invalid");
        }
    }

    public String generatePassword() {
        PasswordGenerator gen = new PasswordGenerator();
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(2);

        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(2);

        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(2);

        CharacterData specialChars = new CharacterData() {
            @Override
            public String getErrorCode() {
                return null;
            }

            public String getCharacters() {
                return "!@#$%^&*()_+";
            }
        };
        CharacterRule splCharRule = new CharacterRule(specialChars);
        splCharRule.setNumberOfCharacters(2);

        return gen.generatePassword(10, splCharRule, lowerCaseRule,
                upperCaseRule, digitRule);
    }

    public void generateAccount(UserDTO userDTO) {
        Optional<UserEntity> userOptional = userRepository.findByCnp(userDTO.getCnp());
        if(userOptional.isPresent()) {
            var user = userOptional.get();
            if(Objects.equals(user.getFirstName(), userDTO.getFirstName()) &&
                    Objects.equals(user.getLastName(), userDTO.getLastName())) {
                if(user.getUsername() != null) {
                    //the data is valid, but an account was already generated for this user if there is a username
                    throw new AccountGenerationException("There is already an account registered with this data.");
                }
                var username = generateUsername(user.getFirstName(), user.getLastName());
                var password = generatePassword();
                user.setPassword(passwordEncoder.encode(password));
                user.setUsername(username);
                userRepository.save(user);
                emailService.sendSuccessEmail(userDTO, username, password);
            } else {
                throw new AccountGenerationException("The data you provided is incorrect");
            }
        } else {
            throw new AccountGenerationException("The data you provided is incorrect");
        }
    }
}
