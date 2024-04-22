package com.GreenCycleSolutions.gcsbackend.service;

import com.GreenCycleSolutions.gcsbackend.entity.UserEntity;
import com.GreenCycleSolutions.gcsbackend.exception.UsernameAlreadyExistsException;
import com.GreenCycleSolutions.gcsbackend.repository.UserRepository;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountGenerationService {

    private final UserRepository userRepository;

    public AccountGenerationService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

    public Boolean validateUsernameChange(String newUsername) {
        var users = userRepository.findAll();
        List<String> usernames = users.stream().map(UserEntity::getUsername).toList();
        if(!usernames.contains(newUsername)) {
            return true;
        }
        throw new UsernameAlreadyExistsException("This username is already used. Try again!");
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
}
