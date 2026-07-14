package dev.muzakkitlabs.java_ai_playground.springai.configuration;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.OllamaEmbeddingModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AiPlaygroundConfiguration {
	
	@Bean
	@Primary
	public EmbeddingModel embeddingModel(OllamaEmbeddingModel model) {
	    return model;
	}
	
	@Bean
	@Qualifier("ollamaChatModelImpl")
	public ChatModel ollamaChatModelImpl(OllamaChatModel ollamaChatModel) {
	    return ollamaChatModel;
	}
	
	@Bean
	@Qualifier("openAiChatModelImpl")
	public ChatModel openAiChatModelImpl(OpenAiChatModel openAiChatModel) {
	    return openAiChatModel;
	}
	
	@Bean
	public ChatMemory chatMemory() {
	    return MessageWindowChatMemory.builder().maxMessages(20).build();
	}

}