package com.astraflow.astraflow_backend.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.model}")
    private String model;

    @Value("${gemini.api.url}")
    private String apiUrl;

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(30))
            .build();

    public String generateBirthChartNarrative(
            String userName,
            String sunSign,
            String moonSign,
            String risingSign,
            String dominantPlanet,
            String signatureAspect) {

        String prompt =
            "You are AstraFlow Chief Astrological AI Interpreter. " +
            "Transform astrological data into deeply personal warm engaging insights. " +
            "TONE: Like a wise friend who just really gets them. " +
            "OUTPUT: 3-4 paragraphs 250-300 words. " +
            "Make it feel like a love letter written by the universe.\n\n" +
            "Create a Welcome to Your Cosmic Profile message for " + userName + ". " +
            "Sun Sign: " + sunSign + ". " +
            "Moon Sign: " + moonSign + ". " +
            "Rising Sign: " + risingSign + ". " +
            "Ruling Planet: " + dominantPlanet + ". " +
            "Signature Aspect: " + signatureAspect + ". " +
            "Start with a warm greeting. Make them feel seen. " +
            "End with an empowerment statement.";

        String requestBody =
            "{" +
            "\"contents\": [{" +
            "\"parts\": [{" +
            "\"text\": \"" + escapeJson(prompt) + "\"" +
            "}]" +
            "}]," +
            "\"generationConfig\": {" +
            "\"temperature\": 0.9," +
            "\"maxOutputTokens\": 1024" +
            "}" +
            "}";

        try {
            String url = apiUrl + "/" + model
                    + ":generateContent?key=" + apiKey;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .timeout(Duration.ofSeconds(60))
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() == 200) {
                return parseGeminiResponse(response.body());
            } else {
                return "Gemini API error. Status: "
                        + response.statusCode()
                        + " Body: " + response.body();
            }

        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Error calling Gemini: " + e.getMessage();
        }
    }

    private String parseGeminiResponse(String responseBody) {
        try {
            String searchKey = "\"text\": \"";
            int startIndex = responseBody.indexOf(searchKey);
            if (startIndex == -1) {
                searchKey = "\"text\":\"";
                startIndex = responseBody.indexOf(searchKey);
            }
            if (startIndex == -1) {
                return "Could not parse response: " + responseBody;
            }
            startIndex += searchKey.length();

            StringBuilder result = new StringBuilder();
            int i = startIndex;
            while (i < responseBody.length()) {
                char c = responseBody.charAt(i);
                if (c == '\\' && i + 1 < responseBody.length()) {
                    char next = responseBody.charAt(i + 1);
                    if (next == '"')  { result.append('"');  i += 2; continue; }
                    if (next == 'n')  { result.append('\n'); i += 2; continue; }
                    if (next == '\\') { result.append('\\'); i += 2; continue; }
                }
                if (c == '"') break;
                result.append(c);
                i++;
            }
            return result.toString();

        } catch (Exception e) {
            return "Parse error: " + e.getMessage();
        }
    }

    private String escapeJson(String text) {
        return text
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}