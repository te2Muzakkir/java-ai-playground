package dev.muzakkitlabs.java_ai_playground.chatbot.langchain4j.service;

import org.springframework.stereotype.Service;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.TokenStream;
import dev.muzakkitlabs.java_ai_playground.chatbot.langchain4j.assistant.Assistant;
import dev.muzakkitlabs.java_ai_playground.chatbot.langchain4j.assistant.StreamingAssistant;

@Service
public class LangChainChatService {

    private final ChatModel chatModel;
    private final Assistant assistant;
    private final StreamingAssistant streamingAssistant;

    public LangChainChatService(ChatModel chatModel, Assistant assistant, StreamingAssistant streamingAssistant) {
        this.chatModel = chatModel;
		this.assistant = assistant;
		this.streamingAssistant = streamingAssistant;
    }

    public String ollamaChat(String prompt) {
        return chatModel.chat(prompt);
    }
    
    public String lgAssistChat(String userId, String prompt) {
        return assistant.chat(userId, prompt);
    }
    
    public TokenStream stream(String prompt) {
        return streamingAssistant.chat(prompt);
    }

}