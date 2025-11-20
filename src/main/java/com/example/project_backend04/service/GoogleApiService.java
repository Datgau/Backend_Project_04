package com.example.project_backend04.service;

import com.example.project_backend04.dto.response.Auth.GoogleUserData;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleApiService {

    public GoogleUserData getUserInfo(String accessToken) {
        String url = "https://www.googleapis.com/oauth2/v3/userinfo?access_token=" + accessToken;

        RestTemplate restTemplate = new RestTemplate();

        try {
            JsonNode response = restTemplate.getForObject(url, JsonNode.class);

            if (response == null || response.get("sub") == null) {
                return null;
            }

            GoogleUserData data = new GoogleUserData();
            data.setId(response.get("sub").asText()); // Google's user id
            data.setFullName(response.has("name") ? response.get("name").asText() : null);
            data.setEmail(response.has("email") ? response.get("email").asText() : null);
            data.setAvatar(response.has("picture") ? response.get("picture").asText() : null);

            return data;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
