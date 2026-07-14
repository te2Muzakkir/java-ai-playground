package dev.muzakkitlabs.java_ai_playground.chatbot.langchain4j.moderation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class PromptInjectionDetector implements Detector {

    private static final List<String> PATTERNS = List.of(
            "ignore previous",
            "ignore all previous",
            "system prompt",
            "developer message",
            "reveal your instructions",
            "act as",
            "jailbreak",
            "bypass",
            "forget previous"
    );

    @Override
    public Optional<ModerationResult> detect(String input) {
        String text = input.toLowerCase();
        for(String pattern : PATTERNS){
            if(text.contains(pattern)){
                return Optional.of(
                        new ModerationResult(
                                false,
                                ModerationStatus.PROMPT_INJECTION,
                                "Prompt Injection detected"
                        ));
            }
        }
        return Optional.empty();
    }

}