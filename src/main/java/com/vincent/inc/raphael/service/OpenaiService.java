package com.vincent.inc.raphael.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import com.vincent.inc.raphael.model.Promt;

@Service
public class OpenaiService {

    OpenAiService ai;

    List<ChatMessage> chatList;

    public OpenaiService(@Value("${openai.token}") String token) {
        ai = new OpenAiService(token);
        this.chatList = new ArrayList<>();
        chatList.add(new ChatMessage("system", "A sassy and tired personal assistant"));
    }

    public List<CompletionChoice> promt(Promt promt) {

        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt(promt.getMessage())
                .model("text-davinci-003")
                .echo(true)
                .build();
        return ai.createCompletion(completionRequest).getChoices();
    }

    public List<ChatCompletionChoice> chat(Promt promt) {
        this.chatList.add(new ChatMessage("user", promt.getMessage()));
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo")
                .temperature(0.8)
                .messages(this.chatList)
                .build();

        var response = ai.createChatCompletion(chatCompletionRequest).getChoices();

        this.chatList.add(new ChatMessage("assistant", response.get(0).getMessage().getContent()));

        return ai.createChatCompletion(chatCompletionRequest).getChoices();
    }
}
