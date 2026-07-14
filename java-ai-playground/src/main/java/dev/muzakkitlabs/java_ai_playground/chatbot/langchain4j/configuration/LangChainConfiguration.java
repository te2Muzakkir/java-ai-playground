package dev.muzakkitlabs.java_ai_playground.chatbot.langchain4j.configuration;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.langchain4j.data.document.DocumentParser;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.pgvector.PgVectorEmbeddingStore;
import dev.muzakkitlabs.java_ai_playground.chatbot.langchain4j.assistant.Assistant;
import dev.muzakkitlabs.java_ai_playground.chatbot.langchain4j.assistant.RagAssistant;
import dev.muzakkitlabs.java_ai_playground.chatbot.langchain4j.assistant.StreamingAssistant;

@Configuration
public class LangChainConfiguration {
	
	private final Map<Object, ChatMemory> memories = new ConcurrentHashMap<>();

	/**
    @Bean
    public ChatModel chatLanguageModel(OllamaProperties properties) {
        return OllamaChatModel.builder()
                .baseUrl(properties.baseUrl())
                .modelName(properties.modelName())
                .temperature(properties.temperature())
                .build();
    }
    */
    
    @Bean
    public ChatModel openAiChatModellg() {
        return OpenAiChatModel.builder()
                .modelName("gpt-5.5")
                .temperature(1.0)
                .apiKey(System.getenv("OPENAI_API_KEY"))
                .build();
    }
    
    @Bean
    public EmbeddingModel langChainEmbeddingModel() {
        return OllamaEmbeddingModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("mxbai-embed-large")
                .timeout(Duration.ofMinutes(10))
                .build();

    }
    
    @Bean
    public Assistant assistant(ChatModel chatModel, ChatMemoryProvider chatMemoryProvider) {
        return AiServices.builder(Assistant.class)
                .chatModel(chatModel)
                .chatMemoryProvider(chatMemoryProvider)
                .build();
    }
    
    @Bean
    public ChatMemoryProvider chatMemoryProvider() {
        return memoryId ->  memories.computeIfAbsent(
                memoryId,
                id -> MessageWindowChatMemory.withMaxMessages(20)
        );
    }
    
    @Bean
    StreamingChatModel streamingChatModel(
            @Value("${langchain4j.ollama.chat-model.base-url}") String url,
            @Value("${langchain4j.ollama.chat-model.model-name}") String model) {
        return OllamaStreamingChatModel.builder()
                .baseUrl(url)
                .modelName(model)
                .build();
    }
    
    @Bean
    public StreamingAssistant streamingAssistant(StreamingChatModel model, ChatMemoryProvider chatMemoryProvider) {
        return AiServices.builder(StreamingAssistant.class)
                .streamingChatModel(model)
                .chatMemoryProvider(chatMemoryProvider)
                .build();
    }
    
    @Bean
    public EmbeddingStore<TextSegment> embeddingStore() {
        return PgVectorEmbeddingStore.builder()
                .host("localhost")
                .port(5433)
                .database("vectordb")
                .user("postgres")
                .password("postgres")
                .table("langchain_embeddings")
                .dimension(1024)
                .build();
    }
    
    @Bean
    public DocumentSplitter documentSplitter() {
        return DocumentSplitters.recursive(1000,200);
    }
    
    @Bean
    public DocumentParser documentParser() {
        return new ApachePdfBoxDocumentParser();
    }
    
    @Bean
    public ContentRetriever contentRetriever(EmbeddingStore<TextSegment> embeddingStore, 
    		EmbeddingModel langChainEmbeddingModel) {
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(langChainEmbeddingModel)
                .maxResults(2)
                .minScore(0.85)
                .build();
    }
    
    @Bean
    public RagAssistant ragAssistant(ChatModel chatModel,
            ChatMemoryProvider memoryProvider, ContentRetriever retriever) {
        return AiServices.builder(RagAssistant.class)
                .chatModel(chatModel)
                .chatMemoryProvider(memoryProvider)
                //.contentRetriever(retriever) //commented for routing
                .build();
    }
    
}