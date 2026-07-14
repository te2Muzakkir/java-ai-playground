package dev.muzakkitlabs.java_ai_playground.chatbot.langchain4j.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface RagAssistant {

	@SystemMessage("""
			You are a helpful AI assistant.
			Use ONLY the supplied context.
			If the answer is not present in the context,
			clearly say that the information is unavailable.
			   """)
	String ask(@MemoryId String userId, @UserMessage String question);

}