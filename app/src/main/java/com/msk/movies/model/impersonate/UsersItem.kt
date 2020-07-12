package com.msk.movies.model.impersonate

import com.google.gson.annotations.SerializedName

data class UsersItem(

	@field:SerializedName("UserName")
	val userName: String? = null,

	@field:SerializedName("DisplayName")
	val displayName: String? = null,

	@field:SerializedName("Id")
	val id: String? = null
)