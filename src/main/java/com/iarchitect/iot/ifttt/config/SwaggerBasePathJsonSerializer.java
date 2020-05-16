package com.iarchitect.iot.ifttt.config;

import io.swagger.models.Swagger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.json.Json;
import springfox.documentation.spring.web.json.JsonSerializer;

import java.util.List;

@Component
@Primary
public class SwaggerBasePathJsonSerializer extends JsonSerializer {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    public SwaggerBasePathJsonSerializer(List<JacksonModuleRegistrar> modules) {
        super(modules);
    }

    @Override
    public Json toJson(Object toSerialize) {
        if (toSerialize instanceof Swagger) {
            Swagger swagger = (Swagger) toSerialize;
            swagger.basePath(contextPath);
        }
        return super.toJson(toSerialize);
    }

}
