package com.tubandev.smack.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tubandev.smack.R
import com.tubandev.smack.model.Message
import com.tubandev.smack.services.UserDataService
import kotlinx.android.synthetic.main.message_list_view.view.*

/**
 * Created by sulistiyanto on 06/02/18.
 */
class MessageAdapter(val context: Context, val messages : ArrayList<Message>) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.message_list_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return messages.count()
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindMessage(context, messages[position])
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        //val userImage =
        fun bindMessage(context: Context, message: Message) {
            val resourceId = context.resources.getIdentifier(message.userAvatar,
                    "drawable", context.packageName)
            itemView.messageUserImage?.setImageResource(resourceId)
            itemView.messageUserImage?.setBackgroundColor(UserDataService.returnAvatarColor(message.userAvatarColor))
            itemView.messageUserName.text = message.userName
            itemView.timeStamp.text = message.tampStamp
            itemView.messageBody.text = message.message
        }
    }
}