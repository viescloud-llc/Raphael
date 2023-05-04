package com.vincent.inc.raphael.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.ErrorResponseException;

import jakarta.ws.rs.QueryParam;
import io.swagger.v3.oas.annotations.Operation;

import com.vincent.inc.raphael.model.Intent;
import com.vincent.inc.raphael.service.IntentService;
import com.vincent.inc.raphael.util.splunk.Splunk;

@RestController
@RequestMapping("/intents")
class IntentController
{
    @Autowired
    IntentService intentService;

    @Operation(summary = "Get a list of all Intent")
    @GetMapping
    public ResponseEntity<List<Intent>> getAll()
    {
        try
        {
            List<Intent> intents = intentService.getAll();

            if (intents.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(intents, HttpStatus.OK);
        }
        catch(ErrorResponseException ex)
        {
            throw ex;
        }
        catch (Exception ex)
        {
            Splunk.logError(ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get Intent base on id in path variable")
    @GetMapping("{id}")
    public ResponseEntity<Intent> getById(@PathVariable("id") int id)
    {
        try
        {
            Intent intent = intentService.getById(id);

            return new ResponseEntity<>(intent, HttpStatus.OK);
        }
        catch(ErrorResponseException ex)
        {
            throw ex;
        }
        catch(Exception ex)
        {
            Splunk.logError(ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get a list of all Intent that match all information base on query parameter")
    @GetMapping("match_all")
    public ResponseEntity<List<Intent>> matchAll(@QueryParam("intent") Intent intent)
    {
        try
        {
            List<Intent> intents = this.intentService.getAllByMatchAll(intent);

            if (intents.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(intents, HttpStatus.OK);
        }
        catch(ErrorResponseException ex)
        {
            throw ex;
        }
        catch (Exception ex)
        {
            Splunk.logError(ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get a list of all Intent that match any information base on query parameter")
    @GetMapping("match_any")
    public ResponseEntity<List<Intent>> matchAny(@QueryParam("intent") Intent intent)
    {
        try
        {
            List<Intent> intents = this.intentService.getAllByMatchAny(intent);

            if (intents.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(intents, HttpStatus.OK);
        }
        catch(ErrorResponseException ex)
        {
            throw ex;
        }
        catch (Exception ex)
        {
            Splunk.logError(ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Create a new Intent")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Intent> create(@RequestBody Intent intent)
    {
        try
        {
            Intent savedIntent = intentService.createIntent(intent);
            return new ResponseEntity<>(savedIntent, HttpStatus.CREATED);
        }
        catch(ErrorResponseException ex)
        {
            throw ex;
        }
        catch (Exception ex)
        {
            Splunk.logError(ex);
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Operation(summary = "Modify an Intent base on id in path variable")
    @PutMapping("{id}")
    public ResponseEntity<Intent> update(@PathVariable("id") int id, @RequestBody Intent intent)
    {
        try
        {
            intent = this.intentService.modifyIntent(id, intent);

            return new ResponseEntity<>(intent, HttpStatus.OK);
        }
        catch(ErrorResponseException ex)
        {
            throw ex;
        }
        catch(Exception ex)
        {
            Splunk.logError(ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Patch an Intent base on id in path variable")
    @PatchMapping("{id}")
    public ResponseEntity<Intent> patch(@PathVariable("id") int id, @RequestBody Intent intent)
    {
        try
        {
            intent = this.intentService.patchIntent(id, intent);

            return new ResponseEntity<>(intent, HttpStatus.OK);
        }
        catch(ErrorResponseException ex)
        {
            throw ex;
        }
        catch(Exception ex)
        {
            Splunk.logError(ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete an Intent base on id in path variable")
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id)
    {
        try
        {
            intentService.deleteIntent(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch(ErrorResponseException ex)
        {
            throw ex;
        }
        catch (Exception ex)
        {
            Splunk.logError(ex);
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}