package org.bz.autoorganizer.data.models

import com.google.gson.annotations.SerializedName

data class AutoModel(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("cyrillic-name") val cyrillicName: String?,
    @SerializedName("class") val autoClass: String?,
    @SerializedName("year-from") val yearFrom: String?,
    @SerializedName("year-to") val yearTo: String?,
    @SerializedName("path") val path: Path
)

data class Path(
    @SerializedName("mark-id") val markId: String?
)
