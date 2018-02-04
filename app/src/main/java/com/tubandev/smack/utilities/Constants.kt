package com.tubandev.smack.utilities

/**
 * Created by sulistiyanto on 31/01/18.
 */

//const val BASE_URL = "http://localhost:3005/v1/"
const val BASE_URL = "https://tubandevchat2.herokuapp.com/v1/"
const val URL_REGISTER = "${BASE_URL}account/register"
const val URL_LOGIN = "${BASE_URL}account/login"
const val URL_CREATE_USER = "${BASE_URL}user/add"
const val URL_GET_USER = "${BASE_URL}user/byEmail/"

// Broadcast Constants
const val BROADCAST_USER_DATA_CHANGE = "BROADCAST_USER_DATA_CHANGE"