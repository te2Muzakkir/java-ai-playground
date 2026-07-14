package dev.muzakkitlabs.java_ai_playground.springai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

@Service
public class SpringAIChatService {
	
	private final ChatModel ollamaChatModelImpl;
	private final ChatModel openAiChatModelImpl;
	private final ChatMemory chatMemory;
	private final ToolsUtility toolsUtility;
	private final SyncMcpToolCallbackProvider toolProvider;

	public SpringAIChatService(@Qualifier("ollamaChatModelImpl") ChatModel ollamaChatModelImpl, 
			@Qualifier("openAiChatModelImpl") ChatModel openAiChatModelImpl,
			ChatMemory chatMemory, ToolsUtility toolsUtility, SyncMcpToolCallbackProvider toolProvider) {
		this.ollamaChatModelImpl = ollamaChatModelImpl;
		this.openAiChatModelImpl = openAiChatModelImpl;
		this.chatMemory = chatMemory;
		this.toolsUtility = toolsUtility;
		this.toolProvider = toolProvider;
	}
	
	public String localChat(String conversationId, String prompt) {
		String response = ChatClient.builder(ollamaChatModelImpl)
		.build().prompt()
		.options(OllamaChatOptions.builder().temperature(0.0))
		.advisors(a -> a.advisors(
				MessageChatMemoryAdvisor.builder(chatMemory).build())
				.param(ChatMemory.CONVERSATION_ID, conversationId)
				)
		.user(prompt)
		.call()
		//.entity method parses the result based on the entity structure. 
		//Used for back-end processing only
		//.entity(InterviewQuestion.class)
		.content();
		System.out.println("response: "+ response);
		return response;
	}
	
	public Flux<String> localStreamingChat(String conversationId, String prompt) {
		return ChatClient.builder(ollamaChatModelImpl)
		.build().prompt()
		.options(OllamaChatOptions.builder().temperature(0.0))
		.advisors(a -> a.advisors(
				MessageChatMemoryAdvisor.builder(chatMemory).build())
				.param(ChatMemory.CONVERSATION_ID, conversationId)
				)
		.user(prompt)
		.stream()
		.content();
	}
	
	public String openAiChat(String prompt) {
		OpenAiChatOptions chatOptions = OpenAiChatOptions.builder()
				.model("gpt-5.5")
				.maxCompletionTokens(500)
				.temperature(1.0)
				.build();
		ChatResponse response = openAiChatModelImpl.call(new Prompt(prompt, chatOptions));
		System.out.println(response.getResult().getOutput().getText());
		return response.getResult().getOutput().getText();
	}
	
	public String localChatTool(String prompt) {
		String response = ChatClient.builder(ollamaChatModelImpl)
		.build().prompt()
		.options(OllamaChatOptions.builder().temperature(0.0))
		.tools(toolsUtility)
		.user(prompt)
		.call()
		.content();
		System.out.println("response: "+ response);
		return response;
	}
	
	public String githubMcpServer(String prompt) {
		return ChatClient.builder(openAiChatModelImpl)
				.build()
				.prompt()
				.tools(toolProvider)
				.user(prompt)
				.call()
				.content();
	}

}