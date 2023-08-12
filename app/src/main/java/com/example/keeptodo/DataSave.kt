package com.example.keeptodo

import android.content.Context
import android.preference.PreferenceManager
import com.google.gson.Gson


var arrString = Array(4){Array(1000){""} }
var n = 0
var daily = 0
var editNum = -1

// 保存数组到SharedPreferences
fun saveArrayToSharedPreferences(context: Context, key: String) {
    val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    val editor = prefs.edit()
    val gson = Gson()
    val json = gson.toJson(arrString)
    editor.putString(key, json)
    editor.apply()
}

// 从SharedPreferences中读取数组
fun loadArrayFromSharedPreferences(context: Context, key: String) {
    val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    val json = prefs.getString(key, null)
    if (json != null) {
        val gson = Gson()
        arrString = gson.fromJson(json, Array<Array<String>>::class.java)
    }
}
