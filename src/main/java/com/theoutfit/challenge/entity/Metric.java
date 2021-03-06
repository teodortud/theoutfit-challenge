package com.theoutfit.challenge.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document("metrics")
public class Metric {
    @Id
    private String id;
    private Double averagePerOrder;
    private List<String> top3Brands;
    private List<Long> top10Products;

    //l-am adaugat sa vad update-ul in baza de date
    private LocalDateTime dateTime;
}
