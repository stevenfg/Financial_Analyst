package example.chat;

import example.llm.LLMClient;

public class ChatBot {
    private LLMClient llmClient;

    public ChatBot() {
        this.llmClient = new LLMClient();
    }

    public String getResponse(String input) {
        return llmClient.ask(input);
    }
} 