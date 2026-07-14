package dev.muzakkitlabs.java_ai_playground.springai.service;

import java.time.LocalDate;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class ToolsUtility {

	@Tool(description = "Returns today's date")
	public String today() {
		return LocalDate.now().toString();
	}
	
	@Tool(description = "Adds two numbers")
    public double add(double a, double b) {
        return a + b;
    }

    @Tool(description = "Subtracts two numbers")
    public double subtract(double a, double b) {
        return a - b;
    }

    @Tool(description = "Multiplies two numbers")
    public double multiply(double a, double b) {
        return a * b;
    }

    @Tool(description = "Divides two numbers")
    public double divide(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("Division by zero");
        }
        return a / b;
    }

}