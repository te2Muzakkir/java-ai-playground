package dev.muzakkitlabs.java_ai_playground.chatbot.langchain4j.controller;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import dev.langchain4j.service.TokenStream;
import dev.muzakkitlabs.java_ai_playground.chatbot.langchain4j.dto.ChatRequest;
import dev.muzakkitlabs.java_ai_playground.chatbot.langchain4j.routing.ConfidenceResult;
import dev.muzakkitlabs.java_ai_playground.chatbot.langchain4j.service.ConfidenceRoutingService;
import dev.muzakkitlabs.java_ai_playground.chatbot.langchain4j.service.LangChainChatService;
import dev.muzakkitlabs.java_ai_playground.chatbot.langchain4j.service.RagIngestionService;
import dev.muzakkitlabs.java_ai_playground.chatbot.langchain4j.service.RagService;

@RestController
@RequestMapping("/langchain")
public class LangChainController {

    private final LangChainChatService langChainChatService;
    private final RagService ragService;
    private final RagIngestionService ragIngestionService;
    private final ConfidenceRoutingService routingService;

    public LangChainController(LangChainChatService langChainChatService, RagService ragService, 
    		RagIngestionService ragIngestionService, ConfidenceRoutingService routingService) {
        this.langChainChatService = langChainChatService;
		this.ragService = ragService;
		this.ragIngestionService = ragIngestionService;
		this.routingService = routingService;
    }

    @PostMapping("/lg-chat")
    public String ollamaChat(@RequestBody String prompt) {
        return langChainChatService.ollamaChat(prompt);
    }
    
    @PostMapping("/lg-assist-chat")
    public String lgAssistChat(@RequestBody ChatRequest chatRequest) {
        return langChainChatService.lgAssistChat(chatRequest.userId(), chatRequest.prompt());
    }
    
    @GetMapping(value="/lg-stream",
            produces=MediaType.TEXT_EVENT_STREAM_VALUE)
    public TokenStream lgStream(@RequestBody String prompt) {
    	return langChainChatService.stream(prompt);
    }
    
    @PostMapping("/ingest")
    public String ingest(@RequestParam MultipartFile file) throws IOException {
    	ragIngestionService.ingest(file);
        return "Document ingested successfully";
    }
    
    @PostMapping("/lg-rag")
    public String lgRag(@RequestBody ChatRequest request) {
        return ragService.ask(request.userId(), request.prompt());
    }
    
    @PostMapping("/chat")
    public ConfidenceResult chat(@RequestBody ChatRequest request) {
        return routingService.chat(request.userId(), request.prompt()
        );
    }

}