package ca.aversa.insessionservice.model

import ca.aversa.insessionservice.model.entity.MedicationDocument

data class Medication(

    val name: String,
    val isVisible: Boolean
) {
    fun toEntity(): MedicationDocument {
        return MedicationDocument(name, isVisible)
    }

    object Mapper {
        fun from(request: ca.aversa.insessionservice.model.request.Medication): Medication {
            return Medication(request.name, request.isVisible)
        }

        fun from(row: MedicationDocument): Medication {
            return Medication(row.name, row.isVisible)
        }
    }
}
