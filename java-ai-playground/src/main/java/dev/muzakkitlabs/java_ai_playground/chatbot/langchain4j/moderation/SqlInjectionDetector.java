package dev.muzakkitlabs.java_ai_playground.chatbot.langchain4j.moderation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class SqlInjectionDetector implements Detector {

    private static final List<String> PATTERNS = List.of(
            "' or '1'='1",
            "union select",
            "drop table",
            "truncate",
            "delete from",
            "--",
            "xp_cmdshell"
    );

    @Override
    public Optional<ModerationResult> detect(String input) {
        String text = input.toLowerCase();
        for(String pattern : PATTERNS){
            if(text.contains(pattern)){
                return Optional.of(
                        new ModerationResult(
                                false,
                                ModerationStatus.SQL_INJECTION,
                                "SQL Injection detected"
                        ));
            }
        }
        return Optional.empty();
    }

}