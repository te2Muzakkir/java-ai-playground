package dev.muzakkitlabs.java_ai_playground.chatbot.langchain4j.assistant;

import dev.langchain4j.service.TokenStream;

public interface StreamingAssistant {

    TokenStream chat(String message);

}