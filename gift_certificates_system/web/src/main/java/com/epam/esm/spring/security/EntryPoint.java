package com.epam.esm.spring.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 * Class of entry point security
 */
@Component
public class EntryPoint implements AuthenticationEntryPoint {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MessageSource messageSource;

    /**
     * @param request       request
     * @param response      response
     * @param authException for wrong authentication
     * @throws IOException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        log.error(authException.getMessage());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding("UTF-8");
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage(
                "300004", new Object[]{}, locale);
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode error = mapper.createObjectNode();
        error.put("code", "300004");
        error.put("message", errorMessage);
        String jsonError = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
        response.getWriter().write(jsonError);
    }
}
