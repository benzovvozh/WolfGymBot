package io.project.wolfgymbot.exception;

import io.netty.handler.timeout.TimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.function.Supplier;

@Component
@Slf4j
public class ApiClientErrorHandler {

    public <T> T executeWithHandling(Supplier<T> apiCall, String operation) {
        try {
            return apiCall.get();
        } catch (TimeoutException e) {
            log.error("{} - Request timeout", operation);
            return null;
        } catch (WebClientResponseException.NotFound e) {
            log.warn("{} - Resource not found (404)", operation);
            return null;
        } catch (WebClientResponseException.BadRequest e) {
            log.error("{} - Bad request (400): {}", operation, e.getResponseBodyAsString());
            return null;
        } catch (WebClientResponseException e) {
            log.error("{} - API error ({}): {}", operation, e.getStatusCode(), e.getMessage());
            return null;
        } catch (Exception e) {
            log.error("{} - Unexpected error", operation, e);
            return null;
        }
    }
}
