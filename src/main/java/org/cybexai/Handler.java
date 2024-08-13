package org.cybexai;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import org.cybexai.dto.PersonRequest;
import org.cybexai.dto.PersonResponse;

/**
 * @author Dannick Tchatchoua <email: dannickte@gmail.com>
 */
public class Handler implements RequestHandler<PersonRequest, PersonResponse> {

    private AmazonDynamoDB amazonDynamoDB;

    private String DYNAMODB_TABLE_NAME = "d_users";
    private Regions REGION = Regions.EU_WEST_3;

    public PersonResponse handleRequest(PersonRequest personRequest, Context context) {
        this.initDynamoDbClient();

        persistData(personRequest);

        PersonResponse personResponse = new PersonResponse();
        personResponse.setMessage("Saved Successfully!!!");
        return personResponse;
    }

    private void persistData(PersonRequest personRequest) throws ConditionalCheckFailedException {

        Map<String, AttributeValue> attributesMap = new HashMap<>();

        attributesMap.put("uuid", new AttributeValue(String.valueOf(personRequest.getUuid())));
        attributesMap.put("firstName", new AttributeValue(personRequest.getFirstName()));
        attributesMap.put("lastName", new AttributeValue(personRequest.getLastName()));
        attributesMap.put("age", new AttributeValue(String.valueOf(personRequest.getAge())));
        attributesMap.put("address", new AttributeValue(personRequest.getAddress()));

        amazonDynamoDB.putItem(DYNAMODB_TABLE_NAME, attributesMap);
    }

    private void initDynamoDbClient() {
        this.amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
                .withRegion(REGION)
                .build();
    }
}