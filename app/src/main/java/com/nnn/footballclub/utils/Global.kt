package com.nnn.footballclub.utils

import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import com.nnn.footballclub.BuildConfig
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

class Global {
    companion object {

        const val idLeague : Long = 4328

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
                    .baseUrl(BuildConfig.END_POINT)
                    .build()
        }

        fun log(msg : String){
            Log.d(BuildConfig.TAG,msg)
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
}