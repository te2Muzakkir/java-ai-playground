package dev.muzakkitlabs.java_ai_playground.chatbot.langchain4j.assistant;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface SqlAssistant {

	@SystemMessage("""
			You are a senior Java architect.
			""")
	@UserMessage("""
			Explain {{topic}} in simple terms.
			""")
	String explainSQL(@V("topic") String topic);

}