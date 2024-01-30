package com.aws.lambda.functions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.aws.lambda.mapper.HttpProductResponse;
import com.aws.lambda.model.Product;
import com.google.gson.Gson;

import java.net.http.HttpRequest;
import java.util.List;

public class InventoryInsertFunction extends InventoryS3Client implements RequestHandler<HttpRequest, HttpProductResponse> {

    @Override
    public HttpProductResponse handleRequest(HttpRequest request, Context context) {
        context.getLogger().log("Input: " + request);

        //String body = request.getBody;

        Gson gson = new Gson();
        Product productToAdd = gson.fromJson(body, Product.class);

        List<Product> productList = getAllProductList();
        productList.add(productToAdd);

        if(updateAllProduct((Product[]) productList.toArray())) {
            return new HttpProductResponse();
        }

        HttpProductResponse httpProductResponse = new HttpProductResponse();
        httpProductResponse.setStatusCode("500");
        return httpProductResponse;
    }
}
