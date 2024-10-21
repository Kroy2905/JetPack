package com.kroy.sseditor.models

sealed class ApiResponse {

    data class UserLoginResponse(
        val data: UserData? = null, // Assuming you have a UserData data class
        val message: String = "",
        val statusCode: Int = 0
    ) : ApiResponse()

    data class AddClientResponse(
        val data: addUserData? = null, // Replace with actual data type if different
        val message: String = "",
        val statusCode: Int = 0
    ) : ApiResponse()

    data class AllClientResponse(
        val data: List<clientItem>? = emptyList(),
        val message: String = "",
        val statusCode: Int = 0
    ) : ApiResponse()
    data class AllContactResponse(
        val data: List<ContactItem>? = emptyList(),
        val message: String = "",
        val statusCode: Int = 0
    ) : ApiResponse()

    data class AddContacttResponse(
        val data: addContactResponse? = null, // Replace with actual data type if different
        val message: String = "",
        val statusCode: Int = 0
    ) : ApiResponse()

   data class EditContacttResponse(
    val data: addContactResponse? = null, // Replace with actual data type if different
    val message: String = "",
    val statusCode: Int = 0
    ) : ApiResponse()

    data class ContactDetailsResponse(
        val data: ContactDetails? = null, // Replace with actual data type if different
        val message: String = "",
        val statusCode: Int = 0
    ) : ApiResponse()

    data class RandomContactsResponse(
        val data: List<RandomContact>? = null, // Replace with actual data type if different
        val message: String = "",
        val statusCode: Int = 0
    ) : ApiResponse()
}

// Example user data class
data class UserData(
    val token: String = "",
    val userId: Int = 0
)
data class addUserData(
    val clientId: Int = 0
)

// Example client item data class

