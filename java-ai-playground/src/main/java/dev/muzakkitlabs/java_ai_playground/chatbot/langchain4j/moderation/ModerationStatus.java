package dev.muzakkitlabs.java_ai_playground.chatbot.langchain4j.moderation;

public enum ModerationStatus {
	
	SAFE,
    PROMPT_INJECTION,
    SQL_INJECTION,
    XSS,
    SECRET,
    TOXIC

}