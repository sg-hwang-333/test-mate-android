package kr.hs.emirim.evie.testmateloginpage.comm

import android.content.Context
import android.content.SharedPreferences

object SessionManager {
    private const val PREF_NAME = "TestMatePref"
    private const val SESSION_ID = "sessionId"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveSessionId(context: Context, sessionId: String) {
        val editor = getPreferences(context).edit()
        editor.putString(SESSION_ID, sessionId)
        editor.apply()
    }

    fun getSessionId(context: Context): String? {
        return getPreferences(context).getString(SESSION_ID, null)
    }
}