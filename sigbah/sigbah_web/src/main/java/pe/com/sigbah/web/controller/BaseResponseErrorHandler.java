package pe.com.sigbah.web.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

/**
 * @className: MyResponseErrorHandle.java
 * @description: 
 * @date: 17 de may. de 2017
 * @author: SUMERIO.
 */
public class BaseResponseErrorHandler implements ResponseErrorHandler {
	
    private static final Logger log = LoggerFactory.getLogger(BaseResponseErrorHandler.class);

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        log.error("Response error: {} {}", response.getStatusCode(), response.getStatusText());
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return RestUtil.isError(response.getStatusCode());
    }
}
