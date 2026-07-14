package dev.muzakkitlabs.java_ai_playground.springai.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.springframework.ai.audio.tts.TextToSpeechPrompt;
import org.springframework.ai.audio.tts.TextToSpeechResponse;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ImageAndTextToSpeechService {
	
	private final OpenAiImageModel imageModel;
	private final OpenAiAudioSpeechModel aiAudioSpeechModel;
	
	
	public ImageAndTextToSpeechService(OpenAiImageModel imageModel, OpenAiAudioSpeechModel aiAudioSpeechModel) {
		this.imageModel = imageModel;
		this.aiAudioSpeechModel = aiAudioSpeechModel;
	}
	
	public ResponseEntity<Resource> generateImage(String prompt) {
        try {
        	OpenAiImageOptions aiImageOptions = OpenAiImageOptions.builder()
    				.quality("high")
    				.n(1)
    				.height(1024)
    				.width(1024)
    				.build();
    		ImageResponse response = imageModel.call(new ImagePrompt(prompt, aiImageOptions));
    		String b64jsonStr = response.getResult().getOutput().getB64Json();
    		if (b64jsonStr.contains(",")) {
    			b64jsonStr = b64jsonStr.split(",")[1];
            }
    		System.out.println(b64jsonStr);
            byte[] imageBytes = Base64.getDecoder().decode(b64jsonStr);
            Path outputPath = Paths.get("D:\\muzakkir\\muzakkirlabs\\output_image.png");
			Files.write(outputPath, imageBytes);
			Resource imgFile = new UrlResource(outputPath.toUri());
			System.out.println("done");
			return ResponseEntity.ok().body(imgFile);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ResponseEntity<String> generateAudio(String text) {
		OpenAiAudioSpeechOptions speechOptions = OpenAiAudioSpeechOptions.builder()
				.model("gpt-4o-mini-tts")
				.voice(OpenAiAudioSpeechOptions.Voice.BALLAD)
				.responseFormat(OpenAiAudioSpeechOptions.AudioResponseFormat.MP3)
				.speed(1.0)
				.build();
		TextToSpeechPrompt speechPrompt = new TextToSpeechPrompt(text, speechOptions);
		TextToSpeechResponse response = aiAudioSpeechModel.call(speechPrompt);
		byte[] audio = response.getResult().getOutput();
		try (FileOutputStream f = new FileOutputStream("D:\\muzakkir\\muzakkirlabs\\workshop\\June06Audio.mp3")) {
			f.write(audio);
			System.out.println("Audio file saved as output.mp3");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResponseEntity.badRequest().body("Audio generation successful. Check mp3 file.");
	}
	

}