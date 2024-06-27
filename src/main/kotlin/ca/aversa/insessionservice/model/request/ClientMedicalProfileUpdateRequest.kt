package ca.aversa.insessionservice.model.request

import ca.aversa.insessionservice.util.ListPattern
import com.fasterxml.jackson.annotation.JsonProperty

data class ClientMedicalProfileUpdateRequest(

    @JsonProperty("medications")
    val medications: List<Medication>,
)

data class Medication(

    @JsonProperty("name")
    @field:ListPattern(regex = "^.{100}$", message = "Each medication must be less than 100 characters.")
    val name: String,

    @JsonProperty("visible")
    val isVisible: Boolean
)
