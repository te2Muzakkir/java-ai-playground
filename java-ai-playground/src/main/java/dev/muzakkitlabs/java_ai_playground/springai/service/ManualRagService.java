package dev.muzakkitlabs.java_ai_playground.springai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

@Service
public class ManualRagService {
	
	private final OllamaChatModel ollamaChatModel;
	private final VectorStore vectorStore;
	
	public ManualRagService(OllamaChatModel ollamaChatModel, VectorStore vectorStore) {
		this.ollamaChatModel = ollamaChatModel;
		this.vectorStore = vectorStore;
	}
	
	public String retriveForRag(String prompt) {
		return ChatClient.builder(ollamaChatModel)
				.build()
				.prompt()
				.advisors(QuestionAnswerAdvisor.builder(vectorStore).build())
				.user(prompt)
				.call()
				.content();
	}
	

}