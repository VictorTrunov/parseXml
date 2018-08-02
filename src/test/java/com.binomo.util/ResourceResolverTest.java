package com.binomo.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceResolver.class)
public class ResourceResolverTest {

    @Autowired
    private ResourceResolver resourceResolver;

    @Test
    public void test() {
        Resource resource = resourceResolver.getResource("classpath:config.yml");
        assertNotNull(resource);
    }

}