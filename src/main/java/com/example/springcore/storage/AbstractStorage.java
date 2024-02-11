package com.example.springcore.storage;

import com.example.springcore.service.ProfileService;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractStorage<T>{
    private ResourceLoader resourceLoader;
    protected ProfileService profileService;

    @Getter
    private final Map<Integer, T> storageMap = new HashMap<>();

    @Autowired
    public void setInjection(ProfileService profileService, ResourceLoader resourceLoader) {
        this.profileService = profileService;
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    public void init() {
        Resource resource = resourceLoader.getResource(getDataFilePath());
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                T entity = parseLine(line);
                storageMap.put(getId(entity), entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract String getDataFilePath();

    protected abstract T parseLine(String line);

    protected abstract Integer getId(T entity);
}
