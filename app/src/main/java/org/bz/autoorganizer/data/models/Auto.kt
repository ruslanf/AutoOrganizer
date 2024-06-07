package org.bz.autoorganizer.data.models

import com.google.gson.annotations.SerializedName

data class Auto(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("cyrillic-name") val cyrillicName: String?,
    @SerializedName("popular") val isPopular: Boolean = false,
    @SerializedName("country") val country: String?,
    @SerializedName("models") val models: List<AutoModel>?
)
