package com.ahmetceylan.countries.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager



class CustomSharedPreferences {

    private val PREFERENCES_TIME = "Preferences Time"

    companion object{

        private var sharedPreferences : SharedPreferences? = null

        @Volatile private var instance: CustomSharedPreferences? = null

        private val lock = Any()

        operator fun invoke(context: Context) : CustomSharedPreferences?
            = instance?: synchronized(lock){
            instance?: makeCustomSharedPreferences(context).also {
                instance = it
            }
        }

        private fun makeCustomSharedPreferences(context: Context) : CustomSharedPreferences {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSharedPreferences()

        }
    }

    fun saveTime(time : Long){

        sharedPreferences?.edit(commit = true){
            putLong(PREFERENCES_TIME,time)
        }
    }

    fun getTime() = sharedPreferences?.getLong(PREFERENCES_TIME,0)


}