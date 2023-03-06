package com.nabilmh.seekmax.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

/**
 * All preferences are kept as variables with custom setters and getters.
 * We make all default values null so we can check existence with null checks
 *
 * @param application is used as it is safe instead of a regular context
 */
class SharedPreferenceManager @Inject constructor(application: Application) {
    //region Preference Keys
    companion object {
        const val KEY_TOKEN = "KEY_TOKEN"
    }
    //endregion

    private val sharedPreferenceKey = application.packageName // works with different build variants
    private val preferences = application.getSharedPreferences(sharedPreferenceKey, Context.MODE_PRIVATE)

    var token: String?
        get() = preferences.getString(KEY_TOKEN, null)
        set(value) = preferences.edit().putString(KEY_TOKEN, value).apply()

    //endregion

    //region Util Functions
    fun remove(key: String) {
        preferences.edit().remove(key).apply()
    }

    fun clear() {
        preferences.edit().clear().apply()
    }
    //endregion

    inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    fun editAndCommit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = preferences.edit()
        operation(editor)
        editor.commit()
    }

}