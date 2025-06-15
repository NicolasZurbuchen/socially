package ch.nicolaszurbuchen.socially.utils

sealed class DomainError {
    data object InvalidUsername : DomainError()
    data object InvalidEmail : DomainError()
    data object InvalidPassword : DomainError()
    data class Firebase(val type: FirebaseErrorType) : DomainError()
}

sealed class FirebaseErrorType {
    data object NetworkError : FirebaseErrorType()
    data object Unknown : FirebaseErrorType()
}

data class ValidationErrors(
    val errors: List<FieldValidationError>
) : DomainError()

data class FieldValidationError(
    val field: Field,
    val error: DomainError
)

enum class Field {
    USERNAME,
    EMAIL,
    PASSWORD,
}
