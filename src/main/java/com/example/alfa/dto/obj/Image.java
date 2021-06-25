package com.example.alfa.dto.obj;

import java.util.Map;


public class Image {
    private Map<String, String> original;
    private Map<String, String> downsized;

    public Map<String, String> getOriginal() {
        return original;
    }

    public void setOriginal(Map<String, String> original) {
        this.original = original;
    }

    public Map<String, String> getDownsized() {
        return downsized;
    }

    public void setDownsized(Map<String, String> downsized) {
        this.downsized = downsized;
    }
}