package com.example.pixelpeppers.models

import com.google.gson.annotations.SerializedName

data class AccessTokenResponse(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("expiresIn") val expiresIn: Int,
    @SerializedName("tokenType") val tokenType: String

) {
    companion object {
        fun fromMap(map: Map<* , *>): AccessTokenResponse {
            return AccessTokenResponse(
                accessToken = map["accessToken"] as String,
                expiresIn = map["expiresIn"] as Int,
                tokenType = map["tokenType"] as String,
            )
        }
    }
}