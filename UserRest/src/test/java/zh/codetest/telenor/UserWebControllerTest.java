package zh.codetest.telenor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static zh.codetest.telenor.UserWebController.FAILURE_MESSAGE_THE_PATH_IS_NOT_YET_IMPLEMENTED;
import static zh.codetest.telenor.UserWebController.FAILURE_PREFIX;
import static zh.codetest.telenor.UserWebController.SUCCESS_MESSAGE_WELCOME_BUSINESS_USER;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserWebControllerTest {
    @LocalServerPort
    int serverPort = 5000;

    // Positive tests

    @Test
    void testShowAllUsers() throws URISyntaxException {
        final RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:" + serverPort + "/all";
        final URI uri = new URI(baseUrl);

        final ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

        Assertions.assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    void testShowUser_GIVEN_accountPersonalAndUserId_THEN_returnPositiveResponse() throws URISyntaxException {
        final RestTemplate restTemplate = new RestTemplate();
        final String inputParams = "/greeting?account=personal&id=123";
        final String baseUrl = "http://localhost:" + serverPort + inputParams;
        final URI uri = new URI(baseUrl);
        final String expectedResponse = "Hi, userid 123";

        final ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

        Assertions.assertEquals(HttpStatus.OK.value(), result.getStatusCodeValue());
        Assertions.assertTrue(result.getBody().contains(expectedResponse));
    }

    @Test
    void testShowUser_GIVEN_accountBusinessAndTypeSmall_THEN_returnFailureResponse() throws URISyntaxException {
        final RestTemplate restTemplate = new RestTemplate();
        final String inputParams = "/greeting?account=business&type=small";
        final String baseUrl = "http://localhost:" + serverPort + inputParams;
        final URI uri = new URI(baseUrl);
        final String expectedResponse = FAILURE_MESSAGE_THE_PATH_IS_NOT_YET_IMPLEMENTED;

        final ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

        Assertions.assertEquals(HttpStatus.OK.value(), result.getStatusCodeValue());
        Assertions.assertTrue(result.getBody().contains(expectedResponse));
    }

    @Test
    void testShowUser_GIVEN_accountBusinessAndTypeBig_THEN_returnSuccessResponse() throws URISyntaxException {
        final RestTemplate restTemplate = new RestTemplate();
        final String inputParams = "/greeting?account=business&type=big";
        final String baseUrl = "http://localhost:" + serverPort + inputParams;
        final URI uri = new URI(baseUrl);
        final String expectedResponse = SUCCESS_MESSAGE_WELCOME_BUSINESS_USER;

        final ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

        Assertions.assertEquals(HttpStatus.OK.value(), result.getStatusCodeValue());
        Assertions.assertTrue(result.getBody().contains(expectedResponse));
    }

    // Negative tests

    @Test
    void testShowUser_GIVEN_wrongAccount_THEN_returnFailureMessage() throws URISyntaxException {
        final RestTemplate restTemplate = new RestTemplate();
        final String inputParams = "/greeting?account=whatever";
        final String baseUrl = "http://localhost:" + serverPort + inputParams;
        final URI uri = new URI(baseUrl);
        final String expectedResponse = FAILURE_PREFIX;

        final ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

        Assertions.assertEquals(HttpStatus.OK.value(), result.getStatusCodeValue());
        Assertions.assertTrue(result.getBody().contains(expectedResponse));
    }

    @Test
    void testShowUser_GIVEN_accountPersonalAndOverRangedUserId_THEN_returnNotFoundMessage() throws URISyntaxException {
        final RestTemplate restTemplate = new RestTemplate();
        final long id = 10000;
        final String inputParams = "/greeting?account=personal&id=" + id;
        final String baseUrl = "http://localhost:" + serverPort + inputParams;
        final URI uri = new URI(baseUrl);
        final String expectedResponse = "not found";

        final ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

        Assertions.assertEquals(HttpStatus.OK.value(), result.getStatusCodeValue());
        Assertions.assertTrue(result.getBody().contains(expectedResponse));
    }

    @Test
    void testShowUser_GIVEN_accountPersonalAndUnderRangedUserId_THEN_returnNotFoundMessage() throws URISyntaxException {
        final RestTemplate restTemplate = new RestTemplate();
        final long id = -1;
        final String inputParams = "/greeting?account=personal&id=" + id;
        final String baseUrl = "http://localhost:" + serverPort + inputParams;
        final URI uri = new URI(baseUrl);
        final String expectedResponse = "Wrong input";

        final ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

        Assertions.assertEquals(HttpStatus.OK.value(), result.getStatusCodeValue());
        Assertions.assertTrue(result.getBody().contains(expectedResponse));
    }
}