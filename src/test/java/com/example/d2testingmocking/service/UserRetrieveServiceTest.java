package com.example.d2testingmocking.service;

import com.example.d2testingmocking.domain.User;
import com.example.d2testingmocking.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Duration;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserRetrieveServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserRetrieveService userRetrieveService;

    @Test
    @DisplayName("should retrieve user from the database by email")
    public void shouldRetrieveUserByEmail() {
        // given
        User userInDb = new User(123L, "some@gmail.com", "Lilu", "Multipasport");
        when(userRepository.findByEmail(userInDb.getEmail())).thenReturn(Optional.of(userInDb));
        // when
        User foundUser = userRetrieveService.findByEmail(userInDb.getEmail());
        // then
        assertAll("foundUser",
                () -> assertEquals(userInDb.getId(), foundUser.getId()),
                () -> assertEquals(userInDb.getEmail(), foundUser.getEmail()),
                () -> assertEquals(userInDb.getFirstName(), foundUser.getFirstName()),
                () -> assertEquals(userInDb.getLastName(), foundUser.getLastName())
        );
    }

    @Test
    @DisplayName("should throw exception if user not found by email")
    public void shouldThrowExceptionWhenUserNotFoundByEmail() {
        // given
        String email = "some@gmail.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        // when
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userRetrieveService.findByEmail(email));
        // then
        assertEquals("User with email " + email + " was not found", exception.getMessage());
    }

    @Test
    @DisplayName("should find user in database by email within a certain amount of time")
    public void shouldFindUserByEmailWithinACertainAmountOfTime() {
        // given
        User userInDb = new User(123L, "some@gmail.com", "Lilu", "Multipasport");
        when(userRepository.findByEmail(userInDb.getEmail())).thenReturn(Optional.of(userInDb));
        // when
        assertTimeout(Duration.ofMillis(500), () -> {
            userRetrieveService.findByEmail(userInDb.getEmail());
        });
    }
}