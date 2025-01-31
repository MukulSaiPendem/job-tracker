package com.app.job_tracker.IntegrationTests;

import com.app.job_tracker.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class UserIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepo userRepo;

    private String generateRandomEmail() {
        return "testuser+" + UUID.randomUUID().toString() + "@example.com";
    }

    private String generateRandomUsername() {
        return "testuser_" + UUID.randomUUID().toString();
    }

    private String registerUserAndGetToken(String username, String email, String password) throws JSONException {
        String registerUrl = "/api/auth/register";
        String loginUrl = "/api/auth/login";
        // Create request body matching UserDto structure
        Map<String, Object> userDto = new HashMap<>();
        userDto.put("username", username);
        userDto.put("email", email);
        userDto.put("password", password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(userDto, headers);

        // Register the user
        ResponseEntity<String> registerResponse = restTemplate.postForEntity(registerUrl, request, String.class);
        System.out.println(registerResponse.getBody());
        assertThat(registerResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        // Login payload matching UserDto
        Map<String, Object> loginDto = new HashMap<>();
        loginDto.put("username", username);
        loginDto.put("password", password);
        HttpEntity<Map<String, Object>> loginRequest = new HttpEntity<>(loginDto, headers);

        // Login to get JWT token
        ResponseEntity<String> loginResponse = restTemplate.postForEntity(loginUrl, loginRequest, String.class);
        assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Extract JWT token from response
        JSONObject jsonResponse = new JSONObject(loginResponse.getBody());
        return jsonResponse.getString("jwt-token");
    }

    @Test
    public void testCreateUser() throws JSONException {
        String getUserUrl = "/api/user/info";

        String username = generateRandomUsername();
        String email = generateRandomEmail();
        String password = "SecurePass123@";
        String jwtToken = registerUserAndGetToken(username, email, password);

        // Simulate authenticated request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(jwtToken); // Use JWT token for authentication
        HttpEntity<String> request = new HttpEntity<>(headers);

        // Get user details
        ResponseEntity<String> response = restTemplate.exchange(getUserUrl, HttpMethod.GET, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        JSONObject jsonResponse = new JSONObject(response.getBody());
        assertThat(jsonResponse.getString("username")).isEqualTo(username);
        assertThat(jsonResponse.getString("email")).isEqualTo(email);

        userRepo.deleteUserByUsername(username);
    }

    @Test
    public void testUpdateUserAndValidate() throws JSONException {
        String updateUserUrl = "/api/user/update";
        String getUserUrl = "/api/user/info";

        String username = generateRandomUsername();
        String email = generateRandomEmail();
        String password = "SecurePass123@";
        String jwtToken = registerUserAndGetToken(username, email, password);

        // Prepare headers with JWT token
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(jwtToken);

        // Update user payload
        Map<String, Object> updatedUserDetails = new HashMap<>();
        updatedUserDetails.put("email", "updated_" + email);
        updatedUserDetails.put("password", "NewSecurePassword123@");

        HttpEntity<Map<String, Object>> updateRequest = new HttpEntity<>(updatedUserDetails, headers);

        // Send update request
        ResponseEntity<String> updateResponse = restTemplate.exchange(updateUserUrl, HttpMethod.PUT, updateRequest, String.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Retrieve updated user details
        HttpEntity<String> getRequest = new HttpEntity<>(headers);
        ResponseEntity<String> getResponse = restTemplate.exchange(getUserUrl, HttpMethod.GET, getRequest, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        JSONObject jsonResponse = new JSONObject(getResponse.getBody());
        assertThat(jsonResponse.getString("email")).isEqualTo("updated_" + email);

        userRepo.deleteUserByUsername(username);
    }
}
