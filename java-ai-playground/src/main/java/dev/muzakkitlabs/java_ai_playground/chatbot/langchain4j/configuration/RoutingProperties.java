package dev.muzakkitlabs.java_ai_playground.chatbot.langchain4j.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "routing.confidence")
public record RoutingProperties(double ragThreshold, int maxResults) {

}