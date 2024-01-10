package com.vincent.inc.raphael.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vincent.inc.raphael.model.Intent;
import com.vincent.inc.raphael.service.IntentService;
import com.vincent.inc.viesspringutils.controller.ViesController;

@RestController
@RequestMapping("/intents")
class IntentController extends ViesController<Intent, Integer, IntentService>
{
    public IntentController(IntentService service) {
        super(service);
    }
}