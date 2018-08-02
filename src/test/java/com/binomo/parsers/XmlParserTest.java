package com.binomo.parsers;

import com.binomo.util.ResourceResolver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {XmlParser.class, YamlParser.class, ResourceResolver.class})
public class XmlParserTest {

    @Autowired
    private XmlParser xmlParser;

    @Autowired
    private YamlParser yamlParser;

    @Autowired
    private ResourceResolver resourceResolver;

    @Test
    public void test() throws Exception {
        Map<String, String> expected = yamlParser.extract(resourceResolver.getResource("classpath:config.yml"));
        Map<String, String> actual = xmlParser.extract(resourceResolver.getResource("classpath:config.xml"));
        Set<Map.Entry<String, String>> actualES = actual.entrySet();
        Set<Map.Entry<String, String>> expectedES = expected.entrySet();
        assertThat(actualES, both(everyItem(isIn(expectedES))).and(containsInAnyOrder(expectedES)));
    }

}