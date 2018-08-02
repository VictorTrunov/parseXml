package com.binomo.parsers;

import com.binomo.util.ResourceResolver;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {YamlParser.class, ResourceResolver.class})
public class YamlParserTest {

    @Autowired
    private ResourceResolver resourceResolver;

    @Autowired
    private YamlParser yamlParser;

    @Test
    public void test() throws Exception {
        Map<String, String> config =
                yamlParser.extract(resourceResolver.getResource("classpath:config.yml"));
        Assert.assertEquals("42", config.get("configuration.number"));
    }

}