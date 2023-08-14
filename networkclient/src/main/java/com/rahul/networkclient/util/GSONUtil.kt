package com.rahul.networkclient.util

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

/**
 * Created by abrol at 09/08/23.
 */
object GSONUtil {
    fun objectToJsonString(`object`: Any?): String {
        return try {
            val gson = Gson()
            gson.toJson(`object`)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    fun <T : Any> fromJson(reader: String?, classOfT: Class<T>?): T {
        /* JsonReader reader = new JsonReader(new StringReader(json));
        reader.setLenient(true);*/
        return Gson().fromJson(reader, classOfT)
    }

    @Suppress("unused")
    fun jsonStringtoObject(jsonString: String?, c: Class<*>?): Any? {
        return try {
            val gson = Gson()
            gson.fromJson(jsonString, c)
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            null
        }
    }
}