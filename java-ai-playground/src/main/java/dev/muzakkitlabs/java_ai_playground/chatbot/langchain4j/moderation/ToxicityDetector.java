package dev.muzakkitlabs.java_ai_playground.chatbot.langchain4j.moderation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class ToxicityDetector implements Detector {

    private static final List<String> WORDS = List.of(
            "...",
            "..."
    );

    @Override
    public Optional<ModerationResult> detect(String input) {
        String text = input.toLowerCase();
        for(String word : WORDS){
            if(text.contains(word)){
                return Optional.of(
                        new ModerationResult(
                                false,
                                ModerationStatus.TOXIC,
                                "Toxic language detected"
                        ));
            }
        }
        return Optional.empty();
    }

}