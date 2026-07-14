package dev.muzakkitlabs.java_ai_playground.chatbot.langchain4j.moderation;

import java.util.Optional;

public interface Detector {
	
	Optional<ModerationResult> detect(String input);

}