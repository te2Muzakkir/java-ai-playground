package dev.muzakkitlabs.java_ai_playground.chatbot.langchain4j.routing;

public record ConfidenceResult(RoutingDecision decision, double similarity, 
		int retrievedChunks, long routingTimeMs, String answer) {

}