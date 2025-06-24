package example.llm;
import org.json.JSONArray;
import org.json.JSONObject;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class LLMClient {
    // private static final String API_URL = "https://generativelanguage.googleapis.com/v1/models/gemini-pro:generateContent";
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1/models/gemini-2.0-flash:generateContent";
    private static final String API_KEY = "AIzaSyAT6vDhZQBH6718zzyVBqVQgEHaaVqg2eU";

    public String ask(String input) {
        if (API_KEY == null || API_KEY.isEmpty()) {
            return "[Error] Gemini API key not found. Please set the GEMINI_API_KEY environment variable.";
        }
        try {
            String requestBody = "{\"contents\":[{\"parts\":[{\"text\":\"" + escapeJson(input) + "\"}]}]}";
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "?key=" + API_KEY))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                .build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return extractGeminiResponse(response.body());
            } else {
                return "[Error] Gemini API call failed: " + response.body();
            }
        } catch (Exception e) {
            return "[Error] Exception during Gemini API call: " + e.getMessage();
        }
    }

    // Extracts the text response from Gemini's JSON

    private String extractGeminiResponse(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray candidates = jsonObject.getJSONArray("candidates");
            if (candidates.length() > 0) {
                JSONObject content = candidates.getJSONObject(0).getJSONObject("content");
                JSONArray parts = content.getJSONArray("parts");
                if (parts.length() > 0) {
                    return parts.getJSONObject(0).getString("text").replaceAll("\\\\n", "\n");
                }
            }
            return "[Error] Unexpected Gemini API response structure.";
        } catch (Exception e) {
            return "[Error] Failed to parse Gemini API response: " + e.getMessage();
        }
    }

    // Escapes quotes and backslashes for JSON
    private String escapeJson(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
} 