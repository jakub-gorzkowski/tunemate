package io.tunemate.api.repository;

import io.tunemate.api.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserRepositoryIntegrationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void UserRepository_Create_CreatesUser() {
        User user = User.builder()
                .email("test@mail.com")
                .username("test_username")
                .password("test_password")
                .build();

        User savedUser = userRepository.save(user);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser).isEqualTo(user);
        assertThat(savedUser.getId()).isGreaterThan(0);
        assertThat(savedUser.getId()).isEqualTo(user.getId());
        assertThat(savedUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(savedUser.getUsername()).isEqualTo(user.getUsername());
        assertThat(savedUser.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    public void UserRepository_FindById_ReturnsUser() {
        User user = User.builder().build();
        userRepository.save(user);

        User foundUser = userRepository.findById(user.getId()).get();

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getId()).isEqualTo(user.getId());
        assertThat(foundUser).isEqualTo(user);
    }

    @Test
    public void UserRepository_Update_UpdatesUser() {
        User user = User.builder()
                .email("test@mail.com")
                .username("test_username")
                .password("test_password")
                .build();
        userRepository.save(user);

        user.setEmail("new_test@mail.com");
        user.setUsername("new_test_username");
        user.setPassword("new_test_password");
        userRepository.save(user);

        User foundUser = userRepository.findById(user.getId()).get();

        assertThat(foundUser).isNotNull();
        assertThat(foundUser).isEqualTo(user);
        assertThat(foundUser.getId()).isEqualTo(user.getId());
        assertThat(foundUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(foundUser.getUsername()).isEqualTo(user.getUsername());
        assertThat(foundUser.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    public void UserRepository_Delete_RemovesUser() {
        User user = User.builder().build();

        userRepository.save(user);
        User foundUser = userRepository.findById(user.getId()).get();

        userRepository.deleteById(user.getId());

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getId()).isEqualTo(user.getId());
        assertThat(foundUser).isEqualTo(user);
        assertThat(userRepository.findById(user.getId())).isEmpty();
    }
}
