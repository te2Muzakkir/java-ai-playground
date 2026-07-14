package dev.muzakkitlabs.java_ai_playground.chatbot.langchain4j.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "langchain4j.ollama.chat-model")
public record OllamaProperties(String baseUrl, String modelName, Double temperature) {

}