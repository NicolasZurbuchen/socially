package ch.nicolaszurbuchen.socially.common.utils

sealed class Resource<out T> {
    data class Success<out T>(val data: T): Resource<T>()
    data class Failure(val error: DomainError): Resource<Nothing>()
}
