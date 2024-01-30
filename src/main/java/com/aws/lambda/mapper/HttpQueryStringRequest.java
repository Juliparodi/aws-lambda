package com.aws.lambda.mapper;

import lombok.Data;

import java.util.Map;

@Data
public class HttpQueryStringRequest {

    Map<String, String> queryStringParameters;
}
