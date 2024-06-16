package com.example.pixelpeppers.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="users")
data class User(
    @PrimaryKey val id: String = "",
    @ColumnInfo(name="display_name") val displayName: String = "",
    @ColumnInfo(name="email") val email: String = "",
    @ColumnInfo(name="onboarding_complete") val onboardingComplete: Boolean = false,
    @ColumnInfo(name="profile_image_url") val profileImageUrl: String = "",
) {
    companion object {
        fun fromFirestoreMap(map: Map<String, Any?>): User {
            val id = map["id"] as? String ?: ""
            val displayName = map["display_name"] as? String ?: ""
            val email = map["email"] as? String ?: ""
            val onboardingComplete = map["onboarding_complete"] as? Boolean ?: false
            val profileImageUrl = map["profile_image_url"] as? String ?: ""
            return User(
                id=id,
                displayName=displayName,
                email=email,
                onboardingComplete=onboardingComplete,
                profileImageUrl=profileImageUrl,
            )
        }
    }
}
