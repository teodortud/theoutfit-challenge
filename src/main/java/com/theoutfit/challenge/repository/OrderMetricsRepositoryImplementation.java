package com.theoutfit.challenge.repository;

import com.theoutfit.challenge.entity.Metric;
import com.theoutfit.challenge.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

@Service
public class OrderMetricsRepositoryImplementation {
    private final OrderService orderService;
    private final MongoTemplate mongoTemplate;
    private final static String COLLECTION_NAME = "metrics";

    @Autowired
    public OrderMetricsRepositoryImplementation(MongoTemplate mongoTemplate,
                                                OrderService orderService){
        this.mongoTemplate = mongoTemplate;
        this.orderService = orderService;
    }

    @Scheduled(initialDelay = 1000L, fixedDelay = 5000L)
    public void saveMetrics() throws ClassNotFoundException, IOException, URISyntaxException {
        Metric newMetric = orderService.calculateMetrics();
        Metric existingMetric;

        Query query = Query.query(Criteria.where("_id").is("uniqueId"));

        if(Objects.nonNull(mongoTemplate.findOne(query, Metric.class))){
            existingMetric = mongoTemplate.findOne(query, Metric.class);
            existingMetric.setId(newMetric.getId());
            existingMetric.setTop3Brands(newMetric.getTop3Brands());
            existingMetric.setTop10Products(newMetric.getTop10Products());
            existingMetric.setDateTime(newMetric.getDateTime());

            mongoTemplate.save(existingMetric, COLLECTION_NAME);
        }
        else{
            mongoTemplate.save(newMetric, COLLECTION_NAME);
        }
    }
}
