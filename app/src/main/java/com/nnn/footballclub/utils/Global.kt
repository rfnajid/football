package com.nnn.footballclub.utils

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nnn.footballclub.BuildConfig
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by ridhaaaaazis on 01/05/18.
 */

object Global {

    const val idLeague : Long = 4328

    val gson : Gson
        get () = GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create()

    fun log(msg : String,type : Int=Log.DEBUG){
        when(type) {
            Log.DEBUG -> Log.d(BuildConfig.TAG, msg)
            Log.ERROR -> Log.e(BuildConfig.TAG, msg)
        }
    }

    fun log(msg : String) : Int{
        System.out.println("Debug : ${BuildConfig.TAG} : $msg")
        return 0
    }

    fun error(context : Context, msg : String){
        context.toast("Error : "+msg)
        log(msg)
    }

    fun dateToString(date : Date) : String{
        val format = SimpleDateFormat("E, dd MMMM yyyy")
        return format.format(date)
    }
}