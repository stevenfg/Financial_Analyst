package example;
import example.chat.ChatBot;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ChatBot chatBot = new ChatBot();
        System.out.println("Welcome to the GenAI Financial Chatbot! Type 'exit' to quit.");
        while (true) {
            System.out.print("You: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Goodbye!");
                break;
            }
            String response = chatBot.getResponse(input);
            System.out.println("Bot: " + response);
        }
        scanner.close();
    }
} 