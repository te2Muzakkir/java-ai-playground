package dev.muzakkitlabs.java_ai_playground.chatbot.langchain4j.moderation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class XssDetector implements Detector {

    private static final List<String> PATTERNS = List.of(
            "<script",
            "</script>",
            "javascript:",
            "onload=",
            "onerror="
    );

    @Override
    public Optional<ModerationResult> detect(String input) {
        String text = input.toLowerCase();
        for(String pattern : PATTERNS){
            if(text.contains(pattern)){
                return Optional.of(
                        new ModerationResult(
                                false,
                                ModerationStatus.XSS,
                                "XSS detected"
                        ));
            }
        }
        return Optional.empty();
    }

}