package com.nnn.footballclub.utils

import android.content.Context
import android.content.res.Resources
import android.util.Log
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nnn.footballclub.BuildConfig
import com.nnn.footballclub.R
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

    fun nullOrEmpty(list : List<*>?) : Boolean{
        return (list == null || list.isEmpty())
    }

    fun dateToString(date : Date,format : SimpleDateFormat = normalDateFormat()) : String{
        return format.format(date)
    }

    fun stringToDate(string : String,format : SimpleDateFormat = normalDateFormat()) : Date{
        return format.parse(string)
    }

    fun normalDateFormat() : SimpleDateFormat{
        return SimpleDateFormat("E, dd MMMM yyyy")
    }

    fun dateOnlyFormat() : SimpleDateFormat{
        return SimpleDateFormat("dd MMMM yyyy")
    }


    // APP RELATED
    fun getLeagueId(resources : Resources,position : Int) : Int{
        return resources.getIntArray(R.array.league_id)
                .get(position)
    }

    fun glideRequestOptions() : RequestOptions{
        return RequestOptions()
                .placeholder(R.color.grey)
                .fitCenter()
    }
}