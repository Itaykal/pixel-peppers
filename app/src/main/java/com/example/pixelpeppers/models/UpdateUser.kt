package com.example.pixelpeppers.models

data class UpdateUser(
    val displayName: String? = null,
    val onboardingComplete: Boolean? = null,
) {
    fun toMap(): Map<String, Any?> {
        val map = mutableMapOf<String, Any?>()
        if (displayName != null) {
            map["display_name"] = displayName
        }
        if (onboardingComplete != null) {
            map["onboarding_complete"] = onboardingComplete
        }
        return map
    }
    fun updateUser(user: User): User {
        return user.copy(
            displayName = displayName ?: user.displayName,
            onboardingComplete = onboardingComplete ?: user.onboardingComplete
        )
    }
}
