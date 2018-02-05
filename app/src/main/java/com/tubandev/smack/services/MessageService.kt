package com.tubandev.smack.services

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.tubandev.smack.model.Channel
import com.tubandev.smack.utilities.URL_GET_CHANNEL
import org.json.JSONException


/**
 * Created by sulistiyanto on 05/02/18.
 */
object MessageService {

    val channels = ArrayList<Channel>()

    fun getChannel(context: Context, complete: (Boolean) -> Unit) {

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
                headers.put("Authorization", "Bearer ${AuthService.authToken}")
                return headers
            }
        }

        channelRequest.timeoutMs.times(200000)
        Volley.newRequestQueue(context).add(channelRequest)
    }

}