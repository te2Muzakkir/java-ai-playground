package dev.muzakkitlabs.java_ai_playground.springai.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.muzakkitlabs.java_ai_playground.springai.service.ImageAndTextToSpeechService;
import dev.muzakkitlabs.java_ai_playground.springai.service.ManualRagService;
import dev.muzakkitlabs.java_ai_playground.springai.service.SpringAIChatService;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
public class PlaygroundController {

	private final ManualRagService manualRagService;
	private final SpringAIChatService springAIChatService;
	private final ImageAndTextToSpeechService imageAndTextToSpeechService;

	public PlaygroundController(ManualRagService manualRagService, SpringAIChatService springAIChatService, 
			ImageAndTextToSpeechService imageAndTextToSpeechService) {
		this.manualRagService = manualRagService;
		this.springAIChatService = springAIChatService;
		this.imageAndTextToSpeechService = imageAndTextToSpeechService;
	}

	@PostMapping("/spring-rag")
	public String springRag(@RequestBody String prompt) {
		return manualRagService.retriveForRag(prompt);
	}

	@PostMapping("/ollama-chat")
	public String ollamaChat(@RequestParam String conversationId, @RequestBody String prompt) {
		return springAIChatService.localChat(conversationId, prompt);
	}

	@PostMapping(value="/ollama-chat-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> ollamaChatStream(@RequestParam String conversationId, @RequestBody String prompt) {
		return springAIChatService.localStreamingChat(conversationId, prompt);
	}

	@PostMapping("/openai-chat")
	public String openaiChat(@RequestBody String prompt) {
		return springAIChatService.openAiChat(prompt);
	}
	
	@PostMapping("/openai-image")
	public ResponseEntity<Resource> openaiImage(@RequestBody String prompt) {
		return imageAndTextToSpeechService.generateImage(prompt);
	}
	
	@PostMapping("/springai-tool")
	public String springAiTool(@RequestBody String prompt) {
		return springAIChatService.localChatTool(prompt);
	}
	
	@PostMapping("/springai-git-mcp")
	public String githubMcpServer(@RequestBody String prompt) {
		return springAIChatService.githubMcpServer(prompt);
	}


}