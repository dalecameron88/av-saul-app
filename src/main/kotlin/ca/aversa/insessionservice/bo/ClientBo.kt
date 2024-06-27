package ca.aversa.insessionservice.bo

import ca.aversa.insessionservice.dao.BasicDao
import ca.aversa.insessionservice.exception.ClientBoException
import ca.aversa.insessionservice.exception.DuplicateResourceException
import ca.aversa.insessionservice.exception.RegisterUserException
import ca.aversa.insessionservice.exception.ResourceNotFoundException
import ca.aversa.insessionservice.model.Client
import ca.aversa.insessionservice.model.MedicalProfile
import ca.aversa.insessionservice.model.entity.ClientMedicalProfileTableRow
import ca.aversa.insessionservice.model.entity.ClientTableRow
import ca.aversa.insessionservice.service.EmailService
import ca.aversa.insessionservice.util.EmailUtils

open class ClientBo(

    private val userBo: UserBo<Client, ClientTableRow>,
    private val emailService: EmailService,
    private val clientDao: BasicDao<ClientTableRow>,
    private val medicalProfileDao: BasicDao<ClientMedicalProfileTableRow>
) {

    fun doesUserExist(email: String): Boolean {
        return userBo.doesUserExist(email)
    }

    fun register(user: Client, auth0UserId: String, clinicianId: String) {
        try {
            user.clinicianId = clinicianId
            userBo.register(user, auth0UserId)

            emailService.sendEmail(
                user.email,
                EmailUtils.CLIENT_INVITATION_ACCEPT_SUBJECT,
                EmailUtils.invitationApprovalEmail("${user.firstName} ${user.lastName}")
            )
        }
        catch (e: DuplicateResourceException) {
            throw e
        }
        catch (e: Exception) {
            throw RegisterUserException(e)
        }
    }

    /**
     * Update client attributes in DynamoDB
     */
    fun updateProfile(user: Client, auth0UserId: String): Client {
        return userBo.updateProfile(user, auth0UserId)
    }

    fun updateFcmToken(clinicianUserId: String, fcmToken: String) {
        userBo.updateFcmToken(clinicianUserId, fcmToken)
    }

    fun getProfiles(userIds: List<String>): List<Client> {
        val clientTableRows = clientDao.batchLoad(userIds)

        return clientTableRows.map(Client.Mapper::from)
    }

    fun getMedicalProfile(auth0UserId: String): MedicalProfile {
        try {
            val row = medicalProfileDao.get(auth0UserId)
                ?: throw ResourceNotFoundException("Medical profile not found for user")

            return MedicalProfile.Mapper.from(row)
        }
        catch (e: Exception) {
            when(e) {
                is ResourceNotFoundException -> throw e
                else -> throw ClientBoException(cause = e)
            }
        }
    }

    fun updateMedicalProfile(auth0UserId: String, medicalProfile: MedicalProfile) {
        val row = medicalProfile.toEntity(auth0UserId)

        medicalProfileDao.update(row)
    }
}
