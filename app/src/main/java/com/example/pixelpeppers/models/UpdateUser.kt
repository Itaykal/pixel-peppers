package com.example.pixelpeppers.models

data class UpdateUser(
    val displayName: String? = null,
    val onboardingComplete: Boolean? = null,
    val profileImageUrl: String? = null,
) {
    fun toMap(): Map<String, Any?> {
        val map = mutableMapOf<String, Any?>()
        if (displayName != null) {
            map["display_name"] = displayName
        }
        if (onboardingComplete != null) {
            map["onboarding_complete"] = onboardingComplete
        }
        if (onboardingComplete != null) {
            map["profile_image_url"] = profileImageUrl
        }
        return map
    }
}
