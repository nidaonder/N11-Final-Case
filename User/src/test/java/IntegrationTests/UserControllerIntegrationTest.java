package IntegrationTests;

import com.nidaonder.User.UserServiceApplication;
import com.nidaonder.User.dto.request.UserSaveRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.nidaonder.User.core.RestResponse;

import java.time.LocalDate;

@SpringBootTest(classes = UserServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void getAllUsersReturn200() {
        ResponseEntity<RestResponse> response = testRestTemplate.getForEntity(
                "http://localhost:" + port + "/api/v1/users", RestResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void getUserByIdShouldReturnUser() {
        Long userId = 1L;

        ResponseEntity<RestResponse> response = testRestTemplate.getForEntity(
                "http://localhost:" + port + "/api/v1/users/{id}", RestResponse.class, userId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void addUserShouldReturn409(){

        UserSaveRequest request = new UserSaveRequest(
                "test",
                "testsurname",
                "testtesttest@test.com",
                "testtest",
                LocalDate.of(1900, 1, 1),
                10.0,
                10.0);

        ResponseEntity<String> response = testRestTemplate.postForEntity("http://localhost:" + port + "/api/v1/users", request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }


}
