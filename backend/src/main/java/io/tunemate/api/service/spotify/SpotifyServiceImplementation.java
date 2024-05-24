package io.tunemate.api.service.spotify;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@Service
public class SpotifyServiceImplementation implements SpotifyService {

    @Value("${application.spotify.client-id}")
    private String clientId;
    @Value("${application.spotify.client-secret}")
    private String clientSecret;

    @Override
    public String getAccessToken() {
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://accounts.spotify.com/api/token";
        String authString = clientId + ":" + clientSecret;
        String encodedAuthString = Base64.getEncoder().encodeToString(authString.getBytes(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + encodedAuthString);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = "grant_type=client_credentials";
        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);

        Map<String, Object> responseBody = response.getBody();
        return responseBody.get("access_token").toString();
    }
}
