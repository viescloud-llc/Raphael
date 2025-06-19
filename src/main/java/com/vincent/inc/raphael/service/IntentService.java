package com.vincent.inc.raphael.service;

import org.springframework.stereotype.Service;

import com.viescloud.eco.viesspringutils.repository.DatabaseCall;
import com.viescloud.eco.viesspringutils.service.ViesService;
import com.vincent.inc.raphael.dao.IntentDao;
import com.vincent.inc.raphael.model.Intent;

@Service
public class IntentService extends ViesService<Long, Intent, IntentDao>
{
    public IntentService(DatabaseCall<Long, Intent> databaseCall, IntentDao repositoryDao) {
        super(databaseCall, repositoryDao);
    }

    @Override
    protected Intent newEmptyObject() {
        return new Intent();
    }

    @Override
    public Long getIdFieldValue(Intent object) {
        return object.getId();
    }

    @Override
    public void setIdFieldValue(Intent object, Long id) {
        object.setId(id);
    }
}