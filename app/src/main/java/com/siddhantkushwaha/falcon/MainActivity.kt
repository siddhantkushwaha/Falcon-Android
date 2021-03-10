package com.siddhantkushwaha.falcon

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.api.Scope
import com.google.api.services.gmail.GmailScopes


class MainActivity : AppCompatActivity() {

    private val scope = Scope(GmailScopes.MAIL_GOOGLE_COM)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        println("$resultCode")
        if (resultCode == RESULT_OK && requestCode == 77) {
            println("*************** Logged in, Permission acquired. **************")
        }
    }

    fun click(view: View) {
        println("******** ${GoogleAuth.getLastSignedInAccount(this)?.email} ********")
    }
}