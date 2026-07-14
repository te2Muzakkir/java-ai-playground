package dev.muzakkitlabs.java_ai_playground.chatbot.langchain4j.moderation;

public record ModerationResult(boolean allowed, ModerationStatus status, String reason) {

}