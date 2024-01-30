package com.aws.lambda.mapper;

import com.aws.lambda.model.Product;
import com.google.gson.Gson;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class HttpProductResponse {

    private String body;
    private String statusCode = "200";
    Map<String, String> headers = new HashMap<String, String>();

    public HttpProductResponse() {
        super();
        this.headers.put("Content-Type", "application/json");
        this.body = body;
    }

    public HttpProductResponse(Product product) {
        this();
        Gson gson = new Gson();
        this.body = gson.toJson(product);
    }

    public HttpProductResponse(Product [] products) {
        this();
        Gson gson = new Gson();
        this.body = gson.toJson(products);
    }
}


