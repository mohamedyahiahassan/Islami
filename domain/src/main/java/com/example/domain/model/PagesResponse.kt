package com.example.domain.model

import com.google.gson.annotations.SerializedName

data class PagesResponse(

	@field:SerializedName("meta")
	val meta: Meta? = null,

	@field:SerializedName("verses")
	val verses: List<VersesItem?>? = null
)

data class Meta(

	@field:SerializedName("filters")
	val filters: Filters? = null
)

data class Filters(

	@field:SerializedName("page_number")
	val pageNumber: String? = null
)

data class VersesItem(

	@field:SerializedName("verse_key")
	val verseKey: String? = null,

	@field:SerializedName("text_uthmani")
	val textUthmani: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class Page(

	val versesList:List<VersesItem>? = null
)
