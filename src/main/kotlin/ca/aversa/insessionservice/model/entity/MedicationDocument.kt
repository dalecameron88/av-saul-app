package ca.aversa.insessionservice.model.entity

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument

@DynamoDBDocument
data class MedicationDocument(

    @DynamoDBAttribute(attributeName = "name")
    var name: String = "",

    @DynamoDBAttribute(attributeName = "visible")
    var isVisible: Boolean = true
)
