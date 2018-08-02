package com.binomo.parsers;

import org.springframework.core.io.Resource;

import java.util.Map;

public interface IParser {

    Map<String, String> extract(Resource resource) throws Exception;

}
