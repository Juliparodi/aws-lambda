package com.aws.lambda.functions;

import com.aws.lambda.model.Product;
import com.google.gson.Gson;
import org.apache.coyote.Response;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class InventoryS3Client {

    protected Product[] getAllProduct() {
        Region region = Region.US_EAST_1;
        S3Client s3Client = S3Client.builder().region(region).build();
        ResponseInputStream<?> objectData = s3Client.getObject(GetObjectRequest.builder()
                        .bucket("handy-inventory-data")
                        .key("handy-tool-catalog.json")
                .build());

        InputStreamReader isr = new InputStreamReader(objectData);
        BufferedReader br = new BufferedReader(isr);

        Product [] products = null;

        Gson gson = new Gson();
        products = gson.fromJson(br, Product[].class);
        return products;
    }

    protected ArrayList<Product> getAllProductList() {
        return new ArrayList<Product>(Arrays.asList(getAllProduct()));
    }

    protected boolean updateAllProduct(Product[] products) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(products);

        Region region = Region.US_EAST_1;
        S3Client s3Client = S3Client.builder()
                .region(region)
                .build();

        PutObjectResponse putResponse = s3Client.putObject(PutObjectRequest.builder()
                        .bucket("handy-inventory-data")
                        .key("handy-tool-catalog.json")
                .build(), RequestBody.fromString(jsonString));

        return putResponse.sdkHttpResponse().isSuccessful();
    }
}
