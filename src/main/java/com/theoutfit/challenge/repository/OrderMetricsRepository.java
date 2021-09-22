package com.theoutfit.challenge.repository;

import com.theoutfit.challenge.entity.Metric;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderMetricsRepository extends MongoRepository<Metric, String> {
}
