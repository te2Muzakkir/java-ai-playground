package dev.muzakkitlabs.java_ai_playground.chatbot.langchain4j.moderation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class ModerationService {

    private final List<Detector> detectors;
    
    public ModerationService(List<Detector> detectors) {
		this.detectors = detectors;
	}

	public ModerationResult moderate(String question){
        for(Detector detector : detectors){
            Optional<ModerationResult> result = detector.detect(question);
            if(result.isPresent())
                return result.get();
        }
        return new ModerationResult(true, ModerationStatus.SAFE, "Safe");
    }

}