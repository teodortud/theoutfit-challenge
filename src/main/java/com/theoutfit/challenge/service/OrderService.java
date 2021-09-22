package com.theoutfit.challenge.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theoutfit.challenge.entity.Order;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OrderService {
    private final HttpService httpService;
    private final String uriPath = "https://op-app.azurewebsites.net/api/dev-test/get-products";

    @Autowired
    public OrderService(HttpService httpService){
        this.httpService = httpService;
    }

    public void calculateMetrics() throws IOException, URISyntaxException, ClassNotFoundException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        CloseableHttpResponse response = httpService.getHttpResponse(uriPath);

        InputStream inputStream = response.getEntity().getContent();

        JsonNode rootNode = null;
        try{
            rootNode = objectMapper.readTree(inputStream);
        }
        catch (IOException exception){
            exception.printStackTrace();
        }

        Iterator<JsonNode> results = rootNode.elements();

        //All orders
        List<Order> orders = getMapping(objectMapper, results);

        //Avg produse pastrate per comanda
        double average = averagePerOrders(orders);

        //top 3 branduri
        String[] topBrands = top3Brands(orders);

        //top 10 produse

    }

    //Mapping each json object to Order model
    private List<Order> getMapping(ObjectMapper objectMapper, Iterator<JsonNode> results){
        List<Order> result = StreamSupport
                .stream(Spliterators.spliteratorUnknownSize(results, Spliterator.ORDERED),
                        false)
                .filter(f -> f.elements().hasNext())
                .map(item -> {
                    Order order = null;
                    try{
                        order = objectMapper.readerFor(Order.class).readValue(item);
                    }
                    catch (IOException exception){
                        exception.printStackTrace();
                    }

                    return order;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return result;
    }

    //aflu numarul de produse pastrate pentru fiecare comanda(o comanda = 5 produse)
    // si impart la numarul total de comenzi
    private double averagePerOrders(List<Order> orders){
        double numberOfKeptProducts = 0;
        double numberOfOrders = 0;
        int counter = 1;

        for(Order order : orders){
            if(order.getStatus().equals("VANDUT")){
                numberOfKeptProducts++;
            }

            counter++;

            if(counter > 5){
                counter = 1;
                numberOfOrders++;
            }
        }

        return numberOfKeptProducts/numberOfOrders;
    }

    private String[] top3Brands(List<Order> orders){
        List<Order> soldOrders = orders
                .stream()
                .filter(item -> item.getStatus().equals("VANDUT"))
                .collect(Collectors.toList());

        Map<String, Long> group  = soldOrders.stream()
                .map(Order::getBrand)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        String top3Brands = group
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.joining(" "));

        return top3Brands.split(" ");
    }

    private void top10Products(){

    }
}
