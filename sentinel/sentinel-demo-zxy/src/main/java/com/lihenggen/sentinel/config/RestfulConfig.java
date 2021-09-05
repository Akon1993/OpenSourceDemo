package com.lihenggen.sentinel.config;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.zxy.common.base.exception.UnprocessableException;
import com.zxy.common.restful.exception.DefaultHandlerExceptionResolver;
import com.zxy.common.restful.exception.ExceptionMappingEntry;
import com.zxy.common.restful.security.AuthenticationException;
import com.zxy.common.restful.security.AuthorizationException;
import com.zxy.common.restful.validation.ValidationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RestfulConfig {

    @Bean
    public DefaultHandlerExceptionResolver getDefaultHandlerExceptionResolver() {
        DefaultHandlerExceptionResolver resolver = new DefaultHandlerExceptionResolver();
        List<ExceptionMappingEntry> entries = new ArrayList<>();

        ExceptionMappingEntry entry = new ExceptionMappingEntry();
        entry.setExceptionClass(UnprocessableException.class);
        entry.setExposeErrorMessage(true);
        entry.setStatusCode(422);
        entry.setMessage("Invalid input.");
        entries.add(entry);

        entry = new ExceptionMappingEntry();
        entry.setExceptionClass(BlockException.class);
        entry.setExposeErrorMessage(true);
        entry.setStatusCode(422);
        entry.setMessage("Invalid input.");
        entries.add(entry);

        entry = new ExceptionMappingEntry();
        entry.setExceptionClass(ValidationException.class);
        entry.setExposeErrorMessage(true);
        entry.setStatusCode(422);
        entry.setMessage("Invalid input.");
        entries.add(entry);

        entry = new ExceptionMappingEntry();
        entry.setExceptionClass(AuthenticationException.class);
        entry.setStatusCode(401);
        entry.setMessage("Access is Denied");
        entries.add(entry);

        entry = new ExceptionMappingEntry();
        entry.setExceptionClass(AuthorizationException.class);
        entry.setStatusCode(403);
        entry.setMessage("Request is not permitted");
        entries.add(entry);


        entry = new ExceptionMappingEntry();
        entry.setExceptionClass(Exception.class);
        entry.setStatusCode(500);
        entry.setMessage("Internal Error");
        entries.add(entry);

        resolver.setEntries(entries);
        return resolver;
    }

}
