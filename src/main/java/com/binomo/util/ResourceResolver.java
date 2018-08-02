package com.binomo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class ResourceResolver {

    @Autowired
    private ResourceLoader resourceLoader;

    public Resource getResource(File file) {
        return resourceLoader.getResource(file.getAbsolutePath());
    }

    public Resource getResource(String location) {
        return resourceLoader.getResource(location);
    }
}
