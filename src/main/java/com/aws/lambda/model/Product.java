package com.aws.lambda.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Product {

    int id;
    String toolType;
    String brand;
    String name;
    int count;


    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", toolType='" + toolType + '\'' +
                ", brand='" + brand + '\'' +
                ", name='" + name + '\'' +
                ", count=" + count +
                '}';
    }
}
