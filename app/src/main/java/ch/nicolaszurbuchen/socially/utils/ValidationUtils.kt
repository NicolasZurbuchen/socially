package ch.nicolaszurbuchen.socially.utils

fun isEmailValid(email: String): Boolean {
    return (email.matches(Regex("^[\\w-_.+]*[\\w-_.]@[\\w]+([.][\\w]+)+$")))
}

fun isPasswordValid(password: String): Boolean {
    return password.length >= 8
}

fun isUsernameValid(username: String): Boolean {
    return username.length >= 3
}

fun validate(email: String, password: String): List<FieldValidationError> {
    val errors = mutableListOf<FieldValidationError>()

    if (!isEmailValid(email)) {
        errors.add(FieldValidationError(Field.EMAIL, DomainError.InvalidEmail))
    }

    if (!isPasswordValid(password)) {
        errors.add(FieldValidationError(Field.PASSWORD, DomainError.InvalidPassword))
    }

    return errors
}

fun validate(username: String, email: String, password: String): List<FieldValidationError> {
    val errors = mutableListOf<FieldValidationError>()

    errors += validate(email, password)

    if (!isUsernameValid(username)) {
        errors.add(FieldValidationError(Field.USERNAME, DomainError.InvalidUsername))
    }

    return errors
}