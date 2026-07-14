package dev.muzakkitlabs.java_ai_playground.chatbot.langchain4j.service;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.muzakkitlabs.java_ai_playground.chatbot.langchain4j.assistant.Assistant;
import dev.muzakkitlabs.java_ai_playground.chatbot.langchain4j.assistant.RagAssistant;
import dev.muzakkitlabs.java_ai_playground.chatbot.langchain4j.configuration.RoutingProperties;
import dev.muzakkitlabs.java_ai_playground.chatbot.langchain4j.moderation.ModerationResult;
import dev.muzakkitlabs.java_ai_playground.chatbot.langchain4j.moderation.ModerationService;
import dev.muzakkitlabs.java_ai_playground.chatbot.langchain4j.routing.ConfidenceResult;
import dev.muzakkitlabs.java_ai_playground.chatbot.langchain4j.routing.RoutingDecision;

@Service
public class ConfidenceRoutingService {
	
	private final EmbeddingModel embeddingModel;
    private final EmbeddingStore<TextSegment> embeddingStore;
    private final RagAssistant ragAssistant;
    private final Assistant assistant;
    private final RoutingProperties properties;
    private final ModerationService moderationService;
    
	public ConfidenceRoutingService(EmbeddingModel embeddingModel, EmbeddingStore<TextSegment> embeddingStore,
			RagAssistant ragAssistant, Assistant assistant, RoutingProperties properties, ModerationService moderationService) {
		this.embeddingModel = embeddingModel;
		this.embeddingStore = embeddingStore;
		this.ragAssistant = ragAssistant;
		this.assistant = assistant;
		this.properties = properties;
		this.moderationService = moderationService;
	}
    
	public ConfidenceResult chat(String memoryId, String question) {
        long start = System.currentTimeMillis();
        //check for moderation
        //ModerationResult moderation = moderationService.moderate(question);
        //if(!moderation.allowed())
        //    return new ConfidenceResult(null, 0, 0, 0, moderation.reason());
        // Generate embedding for the user question
        Embedding embedding = embeddingModel.embed(question).content();
        // Search Vector DB
        EmbeddingSearchResult<TextSegment> searchResult =
                embeddingStore.search(EmbeddingSearchRequest.builder()
                                .queryEmbedding(embedding)
                                .maxResults(properties.maxResults())
                                .build());
        double similarity = 0.0;
        int retrievedChunks = 0;
        String context = searchResult.matches()
                        .stream()
                        .map(match -> match.embedded().text())
                        .collect(Collectors.joining("\n\n"));
        String prompt = """
        		Context
        		========

        		%s

        		Question
        		========

        		%s
        		""".formatted(context, question);
        if (!searchResult.matches().isEmpty()) {
            similarity = searchResult.matches().getFirst().score();
            retrievedChunks = searchResult.matches().size();
        }
        RoutingDecision decision;
        String answer;

        if (similarity >= properties.ragThreshold()) {
        	System.out.println("Calling RAG");
            decision = RoutingDecision.RAG;
            answer = ragAssistant.ask(memoryId, prompt);
        } else {
        	System.out.println("Calling LLM");
            decision = RoutingDecision.LLM;
            answer = assistant.chat(memoryId, question);
        }
        long routingTime = System.currentTimeMillis() - start;
        return new ConfidenceResult(
                decision,
                similarity,
                retrievedChunks,
                routingTime,
                answer
        );
    }

}