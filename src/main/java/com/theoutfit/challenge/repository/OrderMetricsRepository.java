package com.theoutfit.challenge.repository;

import com.theoutfit.challenge.entity.Metric;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public interface OrderMetricsRepository extends MongoRepository<Metric, Double> {
}
