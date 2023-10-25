package dev.onurb.travelassistant;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

import java.io.IOException;
import java.time.Duration;
import java.util.Scanner;

public class TravelAgency {

    public static void main(String[] args) throws IOException {

        String apiKey = System.getenv("OPENAPI_KEY");

        TravelAssistant assistant = AiServices.builder(TravelAssistant.class)
                .chatLanguageModel(OpenAiChatModel.builder().apiKey(apiKey).timeout(Duration.ofMinutes(3)).build())
                .tools(new TripServices())
                .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                .build();

        String input = readInput();

        while (!"bye".equalsIgnoreCase(input)) {

            String answer = assistant.chat(input);

            System.out.println("\u001B[33m" + answer + "\u001B[37m");

            input = readInput();
        }

    }

    private static String readInput() {
        Scanner in = new Scanner(System.in);
        System.out.print("> ");
        return in.nextLine();
    }
}
