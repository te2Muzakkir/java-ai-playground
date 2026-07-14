package dev.muzakkitlabs.java_ai_playground.chatbot.langchain4j.moderation;

import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class SecretDetector implements Detector {

    private static final Pattern OPENAI = Pattern.compile("sk-[A-Za-z0-9]{20,}");

    private static final Pattern AWS = Pattern.compile("AKIA[0-9A-Z]{16}");

    @Override
    public Optional<ModerationResult> detect(String input) {
        if(OPENAI.matcher(input).find()){
            return Optional.of(
                    new ModerationResult(
                            false,
                            ModerationStatus.SECRET,
                            "OpenAI key detected"
                    ));
        }
        if(AWS.matcher(input).find()){
            return Optional.of(
                    new ModerationResult(
                            false,
                            ModerationStatus.SECRET,
                            "AWS key detected"
                    ));
        }
        return Optional.empty();
    }

}