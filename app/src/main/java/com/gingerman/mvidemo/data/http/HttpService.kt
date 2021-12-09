package com.gingerman.mvidemo.data.http

import com.gingerman.mvidemo.data.bean.*
import retrofit2.Call
import retrofit2.http.*

interface HttpService {

    companion object {
        const val url = "https://www.wanandroid.com"
    }

    //登录
    @FormUrlEncoded
    @POST("/user/login")
    fun login(
        @Field("username") userName: String,
        @Field("password") password: String,
    ): Call<BasicBean<UserInfo>>

}
