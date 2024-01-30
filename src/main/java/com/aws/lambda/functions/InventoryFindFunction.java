package com.aws.lambda.functions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.aws.lambda.mapper.HttpProductResponse;
import com.aws.lambda.mapper.HttpQueryStringRequest;
import com.aws.lambda.model.Product;
import com.google.gson.Gson;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class InventoryFindFunction extends InventoryS3Client implements RequestHandler<HttpQueryStringRequest, HttpProductResponse> {

    @Override
    public HttpProductResponse handleRequest(HttpQueryStringRequest request, Context context) {

        context.getLogger().log("Input: " + request);

        String idAsString = (String) request.getQueryStringParameters().get("id");

        if (idAsString.equalsIgnoreCase("all")) {
            Product[] products = getAllProduct();
            HttpProductResponse response = new HttpProductResponse(products);

            return response;
        }
        Integer productId = Integer.parseInt(idAsString);

        Product product = getProductById(productId, context);

        return new HttpProductResponse(product);
    }

    private Product getProductById(int productId, Context context) {

        Product [] products = getAllProduct();

        for (Product product : products) {
            if (product.getId()==productId) {
                return product;
            }
        }

        System.out.println(products[0].toString());

        return null;
    }
}
