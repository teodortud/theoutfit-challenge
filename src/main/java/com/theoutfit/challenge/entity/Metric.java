package com.theoutfit.challenge.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Metric {
    @Id
    private String id;

    private String averagePerOrder;
    private List<String> top3Brands;
    private List<Long> top10Products;
}
