package dev.muzakkitlabs.java_ai_playground.springai.configuration;

import java.util.List;

import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.muzakkitlabs.java_ai_playground.springai.service.ToolsUtility;

@Configuration
public class McpServerConfiguration {
	
	/** Uncomment for MCP server
	@Bean
    public List<ToolCallback> toolCallbacks(ToolsUtility toolsUtility) {
        return List.of(MethodToolCallbackProvider.builder()
                .toolObjects(toolsUtility)
                .build()
                .getToolCallbacks());
    } 
    */

}