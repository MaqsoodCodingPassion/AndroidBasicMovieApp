package com.msk.movies.model.impersonate

import com.google.gson.annotations.SerializedName

data class ImpersonateListResponse(

	@field:SerializedName("TotalAvailable")
	val totalAvailable: Int? = null,

	@field:SerializedName("NextStart")
	val nextStart: Int? = null,

	@field:SerializedName("Users")
	val users: List<UsersItem?>? = null
)