package com.mtanmay.stupa.utils

import com.google.common.truth.Truth.assertThat
import com.google.common.truth.Truth.assertWithMessage
import com.mtanmay.data.models.DBUser
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ValidatorTest {

    // EMAIL VALIDATION TESTS

    @Test
    fun `fails if email is blank or empty`() {
        val isValid = Validator.isEmailValid(EMPTY_STRING)
        assertThat(isValid).isFalse()
    }

    @Test
    fun `fails if email does not match regex pattern`() {
        val isValid = Validator.isEmailValid(WRONG_EMAIL_PATTERN)
        assertThat(isValid).isFalse()
    }

    @Test
    fun `passes if email matches regex pattern`() {
        val isValid = Validator.isEmailValid(CORRECT_EMAIL_PATTERN)

        assertThat(isValid).isTrue()
    }

    // PASSWORD VALIDATION TESTS

    @Test
    fun `fails if password or confirmPassword is blank or empty`() {
        val passwordValidation = Validator.isPasswordValid(EMPTY_STRING, TEST_PASSWORD_1)
        assertWithMessage("PASSWORD VALIDATION FAILED")
            .that(passwordValidation)
            .isFalse()

        val confirmPasswordValidation = Validator.isPasswordValid(TEST_PASSWORD_1, EMPTY_STRING)
        assertWithMessage("CONFIRM PASSWORD VALIDATION FAILED")
            .that(confirmPasswordValidation)
            .isFalse()
    }

    @Test
    fun `fails if password and confirmPassword do not match`() {
        val isValid = Validator.isPasswordValid(TEST_PASSWORD_1, TEST_PASSWORD_2)
        assertThat(isValid).isFalse()
    }

    @Test
    fun `passes if password and confirmPassword match`() {
        val isValid = Validator.isPasswordValid(TEST_PASSWORD_1, TEST_PASSWORD_1)
        assertThat(isValid).isTrue()
    }

    // USER VALIDATION TESTS

    @Test
    fun `fails is user detail is blank or empty`() {
        val withEmptyUsername = createUser(username = EMPTY_STRING)
        assertThat(Validator.isUserValid(withEmptyUsername)).isFalse()

        val withEmptyEmail = createUser(email = EMPTY_STRING)
        assertThat(Validator.isUserValid(withEmptyEmail)).isFalse()

        val withEmptyPassword = createUser(email = EMPTY_STRING)
        assertThat(Validator.isUserValid(withEmptyPassword)).isFalse()

        val withEmptyCountry = createUser(country = EMPTY_STRING)
        assertThat(Validator.isUserValid(withEmptyCountry)).isFalse()

        val withEmptyPhone = createUser(phoneNumber = EMPTY_STRING)
        assertThat(Validator.isUserValid(withEmptyPhone)).isFalse()
    }

    private companion object {
        const val EMPTY_STRING = "" // also works as a blank string
        const val WRONG_EMAIL_PATTERN = "someone_mail.com"
        const val CORRECT_EMAIL_PATTERN = "someone@mail.com"
        const val TEST_PASSWORD_1 = "1234"
        const val TEST_PASSWORD_2 = "12345789"

        const val TEST_EMAIL = "someone@mail.com"
        const val TEST_USERNAME = "john_doe"
        const val TEST_PASSWORD = "john_123"
        const val TEST_COUNTRY = "test_country"
        const val TEST_PHONE_NUMBER = "123456789"

        fun createUser(
            username: String = TEST_USERNAME,
            email: String = TEST_EMAIL,
            password: String = TEST_PASSWORD,
            country: String = TEST_COUNTRY,
            phoneNumber: String = TEST_PHONE_NUMBER
        ) = DBUser(
            username = username,
            emailId = email,
            password = password,
            phoneNumber = phoneNumber,
            country = country
        )

    }

}