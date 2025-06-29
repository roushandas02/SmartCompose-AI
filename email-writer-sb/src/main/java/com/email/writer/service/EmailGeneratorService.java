package com.email.writer.service;

import com.email.writer.entity.EmailRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class EmailGeneratorService {
    //can also be autowired (i.g.)
    private final WebClient webClient;

    //To inject webClient during runtime
    public EmailGeneratorService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @Value("${gemini.api.url}")
    public String geminiApiUrl;



    public String generateEmailReply (EmailRequest emailRequest){
        // Build the prompt
        String prompt = buildPrompt(emailRequest);

        // Craft a request
        Map<String, Object> requestBody = Map.of(
                "contents", new Object[] {
                        Map.of("parts", new Object[]{
                                Map.of("text", prompt)
                        })
                }
        );

        // Do request and get response
        //Asynchronous non-blocking http request using webClient built on top of project reactor (related to webflux - spring reactive web dependency)
        String response = webClient.post()
                .uri(geminiApiUrl)
                .header("Content-Type","application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // Extract Response and Return
        return extractResponseContent(response);
    }
    private String extractResponseContent(String response) {
        try {
            //It's a tool from jackson library to read, write and convert JSON data to java object and vice versa
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response);
            return rootNode.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();
        } catch (Exception e) {
            return "Error processing request: " + e.getMessage();
        }
    }

    private String buildPrompt(EmailRequest emailRequest) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Generate a professional reply to the following email content recieved. Please don't generate a subject line for these. Generate a salutation of sender at beginning and signature at end.");
        if (emailRequest.getTone() != null && !emailRequest.getTone().isEmpty()) {
            prompt.append("Use a ").append(emailRequest.getTone()).append(" tone.");
        }
        prompt.append(emailRequest.getCustomPrompt());
        prompt.append("\nOriginal email: \n").append(emailRequest.getEmailContent());
        return prompt.toString();//StringBuilder to String
    }

}
