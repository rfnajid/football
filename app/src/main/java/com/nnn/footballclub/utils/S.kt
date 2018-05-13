package com.nnn.footballclub.utils

import android.app.Activity
import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.anko.toast
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by ridhaaaaazis on 01/05/18.
 */

class S {
    companion object {

        val idLeague : Long = 4328

        val TAG : String = "uvuv"
        val endpoint: String = "https://www.thesportsdb.com/api/v1/json/1/"

        fun retrofit():Retrofit{
            val gson = GsonBuilder()
                    .setDateFormat("yyyy-MM-dd")
                    .create()

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.NONE
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            return Retrofit.Builder()
                    .addCallAdapterFactory(
                            RxJava2CallAdapterFactory.create())
                    .client(client)
                    .addConverterFactory(
                            GsonConverterFactory.create(gson))
                    .baseUrl(endpoint)
                    .build()
        }

        fun log(msg : String){
            Log.d(TAG,msg)
        }

        fun error(activity : Activity, msg : String){
            activity.toast("Error : "+msg)
            log(msg)
        }

        fun dateToString(date : Date) : String{
            val format = SimpleDateFormat("E, dd MMMM yyyy")
            return format.format(date)
        }
    }
}