package dev.muzakkitlabs.java_ai_playground.springai.service;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class DocumentIngestionService implements CommandLineRunner {
	
	@Value("classpath:/pdf/spring-boot-reference.pdf")
	private Resource resource;
	private final VectorStore vectorStore;
	
	public DocumentIngestionService(VectorStore vectorStore) {
		this.vectorStore = vectorStore;
	}

	@Override
	public void run(String... args) throws Exception {
		TikaDocumentReader documentReader = new TikaDocumentReader(resource);
		TokenTextSplitter splitter = TokenTextSplitter.builder()
	            .withChunkSize(1500)
	            .withMinChunkSizeChars(400)
	            .withMinChunkLengthToEmbed(10)
	            .withMaxNumChunks(5000)
	            .withKeepSeparator(true)
	            .build();
	        List<Document> documentList =  splitter.apply(documentReader.read());
	        vectorStore.accept(documentList);
	}

}