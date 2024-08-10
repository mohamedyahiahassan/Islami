package com.example.domain.model

import com.google.gson.annotations.SerializedName

data class AudioFileResponse(

	@field:SerializedName("audio_file")
	val audioFile: AudioFile? = null
)

data class AudioFile(

	@field:SerializedName("format")
	val format: String? = null,

	@field:SerializedName("audio_url")
	val audioUrl: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("chapter_id")
	val chapterId: Int? = null,

	@field:SerializedName("file_size")
	val fileSize: Int? = null
)
