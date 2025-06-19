package com.vincent.inc.raphael.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viescloud.eco.viesspringutils.controller.ViesController;
import com.vincent.inc.raphael.model.Intent;
import com.vincent.inc.raphael.service.IntentService;

@RestController
@RequestMapping("api/v1/intent")
class IntentController extends ViesController<Long, Intent, IntentService>
{
    public IntentController(IntentService service) {
        super(service);
    }
}