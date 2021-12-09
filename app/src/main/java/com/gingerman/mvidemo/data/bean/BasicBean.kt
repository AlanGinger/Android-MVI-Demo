package com.gingerman.mvidemo.data.bean

data class BasicBean<T>(
    var data: T?,
    var errorCode: Int,
    var errorMsg: String
)