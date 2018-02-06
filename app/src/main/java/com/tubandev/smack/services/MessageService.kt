package com.tubandev.smack.services

import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.tubandev.smack.controller.App
import com.tubandev.smack.model.Channel
import com.tubandev.smack.model.Message
import com.tubandev.smack.utilities.URL_GET_CHANNEL
import com.tubandev.smack.utilities.URL_GET_MESSAGE
import org.json.JSONException


/**
 * Created by sulistiyanto on 05/02/18.
 */
object MessageService {

    val channels = ArrayList<Channel>()
    val messages = ArrayList<Message>()

    fun getChannel(complete: (Boolean) -> Unit) {

        val channelRequest = object : JsonArrayRequest(Method.GET, URL_GET_CHANNEL, null, Response.Listener { response ->

            try {
                for (x in 0 until response.length()) {
                    val channel = response.getJSONObject(x)
                    val name = channel.getString("name")
                    val desc = channel.getString("description")
                    val id = channel.getString("_id")

                    val newChannel = Channel(name, desc, id)
                    this.channels.add(newChannel)
                }
                complete(true)
            } catch ( e: JSONException) {
                Log.d("JSON", "EXC ${e.localizedMessage}")
                complete(false)
            }

        }, Response.ErrorListener { error ->
            Log.d("ERROR", "Could not retrieve channels: $error")
            complete(false)
        }) {

            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Authorization", "Bearer ${App.prefs.authToken}")
                return headers
            }
        }

        channelRequest.timeoutMs.times(200000)
        App.prefs.requestQueue.add(channelRequest)
    }

    fun getMessage(channelId : String, complete: (Boolean) -> Unit) {

        val url = "$URL_GET_MESSAGE$channelId"

        val messagesRequest = object : JsonArrayRequest(Method.GET, url, null, Response.Listener { response ->
            clearMessage()
            try {
                for (x in 0 until response.length()) {
                    val message = response.getJSONObject(x)
                    val messageBody = message.getString("messageBody")
                    val channelId = message.getString("channelId")
                    val id = message.getString("_id")
                    val userName = message.getString("userName")
                    val userAvatar = message.getString("userAvatar")
                    val userAvatarColor = message.getString("userAvatarColor")
                    val timeStamp = message.getString("timeStamp")

                    val newMessage = Message(messageBody, userName, channelId, userAvatar, userAvatarColor, id, timeStamp)
                    this.messages.add(newMessage)
                }
                complete(true)
            } catch (e : JSONException) {
                Log.d("JSON", "EXC ${e.localizedMessage}")
                complete(false)
            }

        }, Response.ErrorListener { error ->
            Log.d("ERROR", "Could not retrieve channels: $error")
            complete(false)
        }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Authorization", "Bearer ${App.prefs.authToken}")
                return headers
            }
        }

        messagesRequest.timeoutMs.times(200000)
        App.prefs.requestQueue.add(messagesRequest)
    }

    fun clearMessage() {
        messages.clear()
    }

    fun clearChannel() {
        channels.clear()
    }
}