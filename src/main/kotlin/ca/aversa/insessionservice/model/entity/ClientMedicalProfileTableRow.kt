package ca.aversa.insessionservice.model.entity

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey

@DynamoDBDocument
data class ClientMedicalProfileTableRow(

    @DynamoDBHashKey(attributeName = "id")
    var id: String = "",

    @DynamoDBAttribute(attributeName = "medications")
    var medications: List<MedicationDocument> = mutableListOf()
)
