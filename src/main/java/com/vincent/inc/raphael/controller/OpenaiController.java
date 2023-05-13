package com.vincent.inc.raphael.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.theokanning.openai.completion.CompletionChoice;
import com.vincent.inc.raphael.model.Promt;
import com.vincent.inc.raphael.service.OpenaiService;

@RestController
@RequestMapping("openais")
public class OpenaiController {

    @Autowired
    private OpenaiService openaiService;
    
    @PostMapping
    public List<CompletionChoice> promt(@RequestBody Promt promt) {
        return this.openaiService.promt(promt);
    }
}
