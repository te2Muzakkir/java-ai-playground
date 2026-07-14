package dev.muzakkitlabs.java_ai_playground.chatbot.langchain4j.service;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentParser;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;

@Service
public class RagIngestionService {
	
	private final EmbeddingStore<TextSegment> embeddingStore;
    private final EmbeddingModel embeddingModel;
    private final DocumentParser parser;

    public RagIngestionService(EmbeddingStore<TextSegment> embeddingStore,
            EmbeddingModel embeddingModel, DocumentParser parser) {
        this.embeddingStore = embeddingStore;
        this.embeddingModel = embeddingModel;
        this.parser = parser;
    }

    public void ingest(MultipartFile file) {
    	try (InputStream inputStream = file.getInputStream()) {
    	    Document document = parser.parse(inputStream);
            EmbeddingStoreIngestor.builder()
                    .embeddingStore(embeddingStore)
                    .embeddingModel(embeddingModel)
                    .build()
                    .ingest(document);
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }

}