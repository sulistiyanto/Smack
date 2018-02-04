package com.tubandev.smack.controller

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.view.View
import android.widget.Toast
import com.tubandev.smack.R
import com.tubandev.smack.services.AuthService
import com.tubandev.smack.services.UserDataService
import com.tubandev.smack.utilities.BROADCAST_USER_DATA_CHANGE
import kotlinx.android.synthetic.main.activity_create_user.*
import java.util.*

class CreateUserActivity : AppCompatActivity() {

    var userAvatar = "profiledefault"
    var avatarColor = "[0.5, 0.5, 0.5, 1]"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
        progressBar.visibility = View.INVISIBLE
    }

    fun createAvatarImageClicked(view: View) {
        val random = Random()
        val color = random.nextInt(2)
        val avatar = random.nextInt(28)

        userAvatar = if (color == 0) { "light$avatar" } else "dark$avatar"

        val resourceId = resources.getIdentifier(userAvatar, "drawable", packageName)
        createAvatarImage.setImageResource(resourceId)
    }

    fun generateBackgroundColorClicked(view: View) {
        val random = Random()
        val r = random.nextInt(255)
        val g = random.nextInt(255)
        val b = random.nextInt(255)

        createAvatarImage.setBackgroundColor(Color.rgb(r, g, b))

        val savedR = r.toDouble() / 255
        val savedG = g.toDouble() / 255
        val savedB = b.toDouble() / 255
        avatarColor = "[$savedR, $savedG, $savedR, 1]"
    }

    fun createUserClicked(view: View) {
        enableProgressBar(true)
        val userName = createUserNameText.text.toString()
        val email = createEmailText.text.toString()
        val password = createPasswordText.text.toString()

        if (userName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
            AuthService.registerUser(this, email, password) { registerSuccess ->
                if (registerSuccess) {
                    enableProgressBar(false)
                    AuthService.loginUser(this, email, password) {loginSuccess ->
                        if (loginSuccess) {
                            AuthService.createUser(this, userName, email, userAvatar, avatarColor) { createSuccess ->
                                if (createSuccess) {
                                    val userDataChange = Intent(BROADCAST_USER_DATA_CHANGE)
                                    LocalBroadcastManager.getInstance(this).sendBroadcast(userDataChange)
                                    println(UserDataService.name)
                                    finish()
                                } else {
                                    errorToast()
                                }
                            }
                        } else {
                            errorToast()
                        }
                    }
                } else {
                    errorToast()
                }

            }
        } else {
            Toast.makeText(this, "Make sure username, email and password are filled in", Toast.LENGTH_SHORT).show()
            enableProgressBar(false)
        }
    }

    fun enableProgressBar(enable : Boolean) {
        if (enable) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.INVISIBLE
        }

        createUserBtn.isEnabled = !enable
        createAvatarImage.isEnabled = !enable
        backgroundColorBtn.isEnabled = !enable
    }

    fun errorToast() {
        Toast.makeText(this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show()
        enableProgressBar(false)
    }
}
