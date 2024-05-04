package com.example.springcore.util;

import com.example.springcore.exceptions.Error;
import com.example.springcore.exceptions.ServiceException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import feign.Response;
import feign.codec.ErrorDecoder;
import io.jsonwebtoken.JwtException;
import jakarta.ws.rs.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Slf4j
public class RetreiveMessageErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder errorDecoder;
    private final ObjectMapper customObjectMapper;

    public RetreiveMessageErrorDecoder() {
        this.errorDecoder = new Default();
        this.customObjectMapper = new ObjectMapper();
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        customObjectMapper.registerModule(new JavaTimeModule());

        if (response.status() >= HttpStatus.BAD_REQUEST.value()
                && response.status() <= HttpStatus.SERVICE_UNAVAILABLE.value()) {

            String responseBody = "";

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.body().asInputStream(), StandardCharsets.UTF_8))) {
                responseBody = reader.lines().collect(Collectors.joining("\n"));
                Error error = customObjectMapper.readValue(responseBody, Error.class);

                if (response.status() == HttpStatus.BAD_REQUEST.value()) {
                    throw new BadRequestException(error.message());
                }

                if (response.status() == HttpStatus.UNAUTHORIZED.value() || response.status() == HttpStatus.FORBIDDEN.value()) {
                    throw new JwtException(error.message());
                }

                if (response.status() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                    throw new ServiceException(error.message());
                }
            } catch (IOException e) {
                log.error("Error reading response body", e);
            }
        }
        return errorDecoder.decode(methodKey, response);
    }
}
