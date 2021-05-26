package com.example.techtest.cloudinary;

public class CloudinaryPayload {

    private String base64;
    private String apiKey;
    private long timestamp;
    private String signature;

    public CloudinaryPayload() {
    }

    public CloudinaryPayload(String base64, String apiKey, long timestamp, String signature) {
        this.base64 = base64;
        this.apiKey = apiKey;
        this.timestamp = timestamp;
        this.signature = signature;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
