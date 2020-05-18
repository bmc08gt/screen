package dev.bmcreations.screen.core.preferences

import android.annotation.SuppressLint
import android.content.Context
import android.preference.PreferenceManager

open class Preferences(appContext: Context) {

    private val preferences = PreferenceManager.getDefaultSharedPreferences(appContext)

    protected fun deleteObject(key: String) {
        val editor = preferences.edit()
        editor.remove(key)
        editor.apply()
    }

    // Save data on same thread.
    @SuppressLint("ApplySharedPref")
    protected fun saveString(key: String, value: String) {
        val editor = preferences.edit()
        editor.putString(key, value)
        editor.commit()
    }


    // Save data in background thread.
    protected fun saveStringAsync(key: String, value: String) {
        val editor = preferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    protected fun getString(key: String): String? {
        return preferences.getString(key, null) ?: return null
    }

    protected fun saveBoolean(key: String, value: Boolean) {
        val editor = preferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    protected fun getBoolean(key: String): Boolean {
        return preferences.getBoolean(key, false)
    }

}
