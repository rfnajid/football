package com.nnn.footballclub.utils.base

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem


/**
 * Created by ridhaaaaazis on 30/05/18.
 */

open class BackButtonActivity : AppCompatActivity(){

    fun setBackActionBar(title : String){
        supportActionBar?.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun setBackActionBar(title: String, toolbar : Toolbar){
        setSupportActionBar(toolbar)
        setBackActionBar(title)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                super.onBackPressed()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }
}