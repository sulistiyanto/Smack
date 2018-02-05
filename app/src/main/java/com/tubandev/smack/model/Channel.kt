package com.tubandev.smack.model

/**
 * Created by sulistiyanto on 05/02/18.
 */
class Channel(val name : String, val desc : String, val id : String) {

    override fun toString(): String {
        return "#$name"
    }
}