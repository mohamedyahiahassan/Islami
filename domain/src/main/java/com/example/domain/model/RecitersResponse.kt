package com.example.domain.model

import com.google.gson.annotations.SerializedName

data class RecitersResponse(

	@field:SerializedName("recitations")
	val recitations: List<RecitationsItem?>? = null
)

data class RecitationsItem(

	@field:SerializedName("reciter_name")
	val reciterName: String? = null,

	@field:SerializedName("translated_name")
	val translatedName: RecitersTranslatedName? = null,

	@field:SerializedName("style")
	val style: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class RecitersTranslatedName(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("language_name")
	val languageName: String? = null
)
