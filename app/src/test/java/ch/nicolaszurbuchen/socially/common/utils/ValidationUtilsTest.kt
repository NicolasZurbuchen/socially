package ch.nicolaszurbuchen.socially.common.utils

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidationUtilsTest {

    @Test
    fun `isEmailValid returns true for valid email`() {
        assertTrue(isEmailValid("test@example.com"))
        assertTrue(isEmailValid("user.name+tag@domain.co.uk"))
    }

    @Test
    fun `isEmailValid returns false for invalid email`() {
        assertFalse(isEmailValid("testexample.com"))  // missing @
        assertFalse(isEmailValid("test@.com"))        // invalid domain
        assertFalse(isEmailValid("test@com"))         // no dot
        assertFalse(isEmailValid("@example.com"))     // missing local part
    }

    @Test
    fun `isPasswordValid returns true for passwords with 8 or more characters`() {
        assertTrue(isPasswordValid("password"))
        assertTrue(isPasswordValid("12345678"))
    }

    @Test
    fun `isPasswordValid returns false for passwords shorter than 8 characters`() {
        assertFalse(isPasswordValid("1234567"))
        assertFalse(isPasswordValid("abc"))
    }

    @Test
    fun `isUsernameValid returns true for usernames with 3 or more characters`() {
        assertTrue(isUsernameValid("abc"))
        assertTrue(isUsernameValid("username"))
    }

    @Test
    fun `isUsernameValid returns false for usernames shorter than 3 characters`() {
        assertFalse(isUsernameValid("ab"))
        assertFalse(isUsernameValid(""))
    }

    @Test
    fun `validate email and password returns correct errors`() {
        val result = validate("invalid", "short")
        assertEquals(2, result.size)
        assertTrue(result.any { it.field == Field.EMAIL && it.error == DomainError.InvalidEmail })
        assertTrue(result.any { it.field == Field.PASSWORD && it.error == DomainError.InvalidPassword })
    }

    @Test
    fun `validate email and password returns empty list for valid inputs`() {
        val result = validate("user@example.com", "strongpass")
        assertTrue(result.isEmpty())
    }

    @Test
    fun `validate username, email and password returns all errors`() {
        val result = validate("ab", "invalid", "short")
        assertEquals(3, result.size)
        assertTrue(result.any { it.field == Field.EMAIL })
        assertTrue(result.any { it.field == Field.PASSWORD })
        assertTrue(result.any { it.field == Field.USERNAME })
    }

    @Test
    fun `validate username, email and password returns empty list for valid inputs`() {
        val result = validate("user", "user@example.com", "password123")
        assertTrue(result.isEmpty())
    }
}
