package com.vincent.inc.raphael.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import com.vincent.inc.raphael.model.Promt;

@Service
public class OpenaiService {

    OpenAiService ai;

    public OpenaiService(@Value("${openai.token}") String token) {
        ai = new OpenAiService(token);
    }

    public List<CompletionChoice> promt(Promt promt) {

        CompletionRequest completionRequest = CompletionRequest.builder()
        .prompt(promt.getMessage())
        .model("gpt-3.5-turbo")
        .echo(true)
        .build();
        return ai.createCompletion(completionRequest).getChoices();
    }
}
