package com.heon.sns.service;

import com.heon.sns.exception.SnsApplicationException;
import com.heon.sns.fixture.UserEntityFixture;
import com.heon.sns.model.entity.UserEntity;
import com.heon.sns.repository.UserEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserEntityRepository userEntityRepository;

    @Test
    void 회원가입이_정상적으로_동작하는_경우() {
        String userName = "userName";
        String password = "password";

        // mocking
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());
        // when(userEntityRepository.save(any())).thenReturn(Optional.of(mock(UserEntity.class)));
        when(userEntityRepository.save(any())).thenReturn(Optional.of(UserEntityFixture.get(userName, password)));

        assertThatCode(() -> userService.join(userName, password)).doesNotThrowAnyException();
    }

    @Test
    void 회원가입시_userName으로_회원가입한_유저가_이미_있는경우() {
        String userName = "userName";
        String password = "password";
        UserEntity fixture = UserEntityFixture.get(userName, password);

        // mocking
        // when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(mock(UserEntity.class)));
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));
        // when(userEntityRepository.save(any())).thenReturn(Optional.of(mock(UserEntity.class)));
        when(userEntityRepository.save(any())).thenReturn(Optional.of(fixture));


        assertThatThrownBy(() -> userService.join(userName, password))
                .isInstanceOf(SnsApplicationException.class);
    }

    @Test
    void 로그인이_정상적으로_동작하는_경우() {
        String userName = "userName";
        String password = "password";

        UserEntity fixture = UserEntityFixture.get(userName, password);

        // mocking
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));
        assertThatCode(() -> userService.login(userName, password)).doesNotThrowAnyException();
    }

    @Test
    void 로그인시_userName으로_회원가입한_유저가_이미_있는경우() {
        String userName = "userName";
        String password = "password";

        // mocking
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());
        when(userEntityRepository.save(any())).thenReturn(Optional.of(mock(UserEntity.class)));

        assertThatThrownBy(() -> userService.login(userName, password))
                .isInstanceOf(SnsApplicationException.class);
    }

    @Test
    void 로그인시_패스워드가_틀린_경우() {
        String userName = "userName";
        String password = "password";
        String wrongPassword = "wrong";

        UserEntity fixture = UserEntityFixture.get(userName, password);

        // mocking
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));

        assertThatThrownBy(() -> userService.login(userName, wrongPassword))
                .isInstanceOf(SnsApplicationException.class);
    }

}