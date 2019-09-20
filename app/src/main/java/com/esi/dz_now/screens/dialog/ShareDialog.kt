package com.esi.dz_now.screens.dialog

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.esi.dz_now.R


class ShareDialog(
    private val email: String?,
    private val phone: String?,
    val title: String,
    val content: String?
) :
    DialogFragment() {

    private val TAG = "TAG-ShareDialog"


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val apps: Array<String> = if (email != "") {
            arrayOf("SMS", "Email")
        } else {
            arrayOf("SMS")
        }
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(getString(R.string.senditvia))
                .setItems(
                    apps
                ) { _, which ->
                    when (which) {
                        // SMS
                        0 -> {
                            val smsManager = SmsManager.getDefault()
                            smsManager.sendTextMessage(phone, null, "$title", null, null)
                        }

                        //EMAIL
                        1 -> {
                            val i = Intent(Intent.ACTION_SEND)
                            i.type = "message/rfc822"
                            i.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                            i.putExtra(Intent.EXTRA_SUBJECT, title)
                            i.putExtra(Intent.EXTRA_TEXT, content)
                            try {
                                startActivity(Intent.createChooser(i, "Send mail..."))
                            } catch (ex: android.content.ActivityNotFoundException) {
                                Toast.makeText(
                                    context,
                                    "There are no email clients installed.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }
                    }
                    Log.i(TAG, "$which $phone $email")
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}