package com.mtanmay.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mtanmay.domain.models.interfaces.User
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity("user")
data class DBUser(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    override val username: String,

    @ColumnInfo("email_id")
    override val emailId: String,

    @ColumnInfo("password")
    val password: String,

    @ColumnInfo("phone_number")
    override val phoneNumber: String,

    override val country: String,

    @ColumnInfo("token")
    override val token: Int =
        (username + emailId + password + phoneNumber + country).hashCode() + (100..1000).random()

) : Parcelable, User