package ca.aversa.insessionservice.model

import ca.aversa.insessionservice.model.entity.ClientMedicalProfileTableRow
import ca.aversa.insessionservice.model.request.ClientMedicalProfileUpdateRequest

data class MedicalProfile(

    val medications: List<Medication>
) {
    fun toEntity(id: String): ClientMedicalProfileTableRow {
        val medicationRows = medications.map(Medication::toEntity)

        return ClientMedicalProfileTableRow(id, medicationRows)
    }

    object Mapper {
        fun from(request: ClientMedicalProfileUpdateRequest): MedicalProfile {
            val medications = request.medications.map(Medication.Mapper::from)

            return MedicalProfile(medications)
        }

        fun from(row: ClientMedicalProfileTableRow): MedicalProfile {
            val medications = row.medications.map(Medication.Mapper::from)

            return MedicalProfile(medications)
        }
    }
}
