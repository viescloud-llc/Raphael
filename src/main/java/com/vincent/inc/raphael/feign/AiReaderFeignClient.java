package com.vincent.inc.raphael.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.vincent.inc.raphael.model.AiReaderRequest;

@FeignClient("${ai.reader.url}")
public interface AiReaderFeignClient {

    @PostMapping("/generate/wav")
    public byte[] generateWav(@RequestBody AiReaderRequest aiReaderRequest);
}
