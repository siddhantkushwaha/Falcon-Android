package com.siddhantkushwaha.falcon

import android.app.Activity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.Tasks


object GoogleAuth {

    private fun getGoogleSignInOptions(
        email: String? = null,
        scope: Scope? = null
    ): GoogleSignInOptions {
        val signInOptionsBuilder = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        if (scope != null)
            signInOptionsBuilder.requestScopes(scope)
        if (email != null)
            signInOptionsBuilder.setAccountName(email)
        signInOptionsBuilder.requestEmail()
        return signInOptionsBuilder.build()
    }

    public fun signIn(
        email: String?,
        activity: Activity,
        scope: Scope,
        requestCode: Int
    ) {
        val thread = Thread {
            val signInOptions = getGoogleSignInOptions(email = email, scope = scope)
            val googleSignInClient = GoogleSignIn.getClient(activity, signInOptions)
            if (email == null) {
                // all signOut() effectively does is clears cache, silent sign in still possible
                // to disconnect account use revokeAccess()
                // TODO - error handling
                Tasks.await(googleSignInClient.signOut())
            }
            activity.startActivityForResult(googleSignInClient.signInIntent, requestCode)
        }
        thread.start()
    }

    public fun signOut(email: String, activity: Activity) {
        val thread = Thread {
            val googleSignInOptions = getGoogleSignInOptions(email = email)
            val googleSignInClient = GoogleSignIn.getClient(activity, googleSignInOptions)
            // TODO - error handling
            Tasks.await(googleSignInClient.revokeAccess())
        }
        thread.start()
    }

    public fun getLastSignedInAccount(activity: Activity): GoogleSignInAccount? {
        return GoogleSignIn.getLastSignedInAccount(activity)
    }
}