package com.vincent.inc.raphael.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Example;
import org.springframework.data.redis.core.RedisTemplate;

import com.google.gson.Gson;

import com.vincent.inc.raphael.dao.IntentDao;
import com.vincent.inc.raphael.model.Intent;
import com.vincent.inc.raphael.util.ReflectionUtils;
import com.vincent.inc.raphael.util.splunk.Splunk;

@Service
public class IntentService
{
    public static final String HASH_KEY = "com.vincent.inc.raphael.intents";

    // @Value("${spring.cache.redis.intentTTL}")
    private int intentTTL = 600;

    @Autowired
    private Gson gson;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private IntentDao intentDao;

    public List<Intent> getAll()
    {
        return this.intentDao.findAll();
    }

    public Intent getById(int id)
    {
        //get from redis
        String key = String.format("%s.%s", HASH_KEY, id);
        try
        {
            String jsonIntent = this.redisTemplate.opsForValue().get(key);
            if(jsonIntent != null)
                return this.gson.fromJson(jsonIntent, Intent.class);
        }
        catch(Exception ex)
        {
            Splunk.logError(ex);
        }

        //get from database
        Optional<Intent> oIntent = this.intentDao.findById(id);

        if(oIntent.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Intent ID not found");

        Intent intent = oIntent.get();

        //save to redis
        try
        {
            this.redisTemplate.opsForValue().set(key, gson.toJson(intent));
            this.redisTemplate.expire(key, intentTTL, TimeUnit.SECONDS);
        }
        catch(Exception ex)
        {
            Splunk.logError(ex);
        }

        return intent;
    }

    public List<Intent> getAllByMatchAll(Intent intent)
    {
        Example<Intent> example = (Example<Intent>) ReflectionUtils.getMatchAllMatcher(intent);
        return this.intentDao.findAll(example);
    }

    public List<Intent> getAllByMatchAny(Intent intent)
    {
        Example<Intent> example = (Example<Intent>) ReflectionUtils.getMatchAnyMatcher(intent);
        return this.intentDao.findAll(example);
    }

    public Intent createIntent(Intent intent)
    {
        intent = this.intentDao.save(intent);
        return intent;
    }

    public Intent modifyIntent(int id, Intent intent)
    {
        Intent oldIntent = this.getById(id);

        ReflectionUtils.replaceValue(oldIntent, intent);

        oldIntent = this.intentDao.save(oldIntent);

        //remove from redis
        try
        {
            String key = String.format("%s.%s", HASH_KEY, id);
            this.redisTemplate.delete(key);
        }
        catch(Exception ex)
        {
            Splunk.logError(ex);
        }

        return oldIntent;
    }

    public Intent patchIntent(int id, Intent intent)
    {
        Intent oldIntent = this.getById(id);

		ReflectionUtils.patchValue(oldIntent, intent);

        oldIntent = this.intentDao.save(oldIntent);

        //remove from redis
        try
        {
            String key = String.format("%s.%s", HASH_KEY, id);
            this.redisTemplate.delete(key);
        }
        catch(Exception ex)
        {
            Splunk.logError(ex);
        }

        return oldIntent;
    }

    public void deleteIntent(int id)
    {
        this.intentDao.deleteById(id);

        //remove from redis
        try
        {
            String key = String.format("%s.%s", HASH_KEY, id);
            this.redisTemplate.delete(key);
        }
        catch(Exception ex)
        {
            Splunk.logError(ex);
        }
    }
}