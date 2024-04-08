package com.mtanmay.stupa.utils

import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object EncryptionUtils {
    fun getEncryptedPassword(password: String): String {
        val cipher = Cipher.getInstance("AES/PKCS5Padding")
        val secretKeySpec = SecretKeySpec(SECRET_KEY.toByteArray(), "AES")
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
        val encryptedBytes = cipher.doFinal(password.toByteArray())
        return Base64.getEncoder().encodeToString(encryptedBytes)
    }

    private val SECRET_KEY = "STUPASPORTSECRET"
}