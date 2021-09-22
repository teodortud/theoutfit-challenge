package com.theoutfit.challenge.repository;

import com.theoutfit.challenge.entity.Metric;
import com.theoutfit.challenge.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;

@Service
public class OrderMetricsRepositoryImplementation {
    private final MongoTemplate mongoTemplate;
    private final OrderService orderService;

    @Autowired
    public OrderMetricsRepositoryImplementation(MongoTemplate mongoTemplate,
                                                OrderService orderService){
        this.mongoTemplate = mongoTemplate;
        this.orderService = orderService;
    }

    public void saveMetrics() throws ClassNotFoundException, IOException, URISyntaxException {
        Metric metric = orderService.calculateMetrics();

        mongoTemplate.save(metric, "metrics");
    }

}
