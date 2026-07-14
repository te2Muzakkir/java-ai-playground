package dev.muzakkitlabs.java_ai_playground.chatbot.langchain4j.service;

import org.springframework.stereotype.Service;

import dev.muzakkitlabs.java_ai_playground.chatbot.langchain4j.assistant.RagAssistant;

@Service
public class RagService {

    private final RagAssistant assistant;

    public RagService(RagAssistant assistant) {
        this.assistant = assistant;
    }

    public String ask(String userId, String question) {
        return assistant.ask(userId, question);
    }

}