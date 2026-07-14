package dev.muzakkitlabs.java_ai_playground.chatbot.langchain4j.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface Assistant {

	//You are an expert Java Architect.
    @SystemMessage("""
        Answer accurately.
        Keep answers concise.
        """)
    String chat(@MemoryId String userId,
            @UserMessage String message);

}