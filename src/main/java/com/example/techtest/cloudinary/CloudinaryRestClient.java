package com.example.techtest.cloudinary;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// See https://cloudinary.com/documentation/upload_images#uploading_with_a_direct_call_to_the_rest_api
@FeignClient(name = "com.example.techtest.cloudinary.CloudinaryRestClient", url = "${cloudinary.url}")
public interface CloudinaryRestClient {

    @PostMapping("/v1_1/{cloudName}/{resourceType}/upload")
    public void upload(
            @PathVariable("cloudName") String cloudName,
            @PathVariable("resourceType") String resourceType,
            @RequestBody CloudinaryPayload payload);
}
