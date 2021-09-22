package com.theoutfit.challenge.service;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class HttpService {
    public CloseableHttpResponse getHttpResponse(String uriPath) throws IOException, URISyntaxException {
        CloseableHttpClient client = HttpClients.createDefault();

        URI uri = new URIBuilder(uriPath).build();

        HttpGet request = new HttpGet(uri);
        request.addHeader("API_KEY", "&aBWaR&r[12x)I9Q2mhi9y4W");
        request.addHeader("Content-Type", "application/json");

        return client.execute(request);
    }
}
