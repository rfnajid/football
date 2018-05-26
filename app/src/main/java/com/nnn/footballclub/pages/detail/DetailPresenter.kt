package com.nnn.footballclub.pages.detail

import android.app.Activity
import android.content.Context
import com.nnn.footballclub.R
import com.nnn.footballclub.model.Event
import com.nnn.footballclub.model.db.FavoriteDB


/**
 * Created by ridhaaaaazis on 18/05/18.
 */

class DetailPresenter (private val event : Event, private val view : DetailContract.View) : DetailContract.Presenter{

    private lateinit var context : Context
    private lateinit var favoriteDB : FavoriteDB
    private var isFavorite : Boolean= false

    override fun start(context : Context) {
        this.context=context
        startTest(context,FavoriteDB(context))
    }

    fun startTest(context : Context,favoriteDB: FavoriteDB){
        this.favoriteDB=favoriteDB
        this.context=context
        isFavorite = favoriteDB.isExist(event)
        view.updateFavoriteLayout(isFavorite)
        view.loadData(event)
    }

    override fun onOptionsItemSelected(id: Int?) : Boolean {
        return when (id){
            android.R.id.home -> {
                (view as Activity).onBackPressed()
                true
            }
            R.id.menu_favorite -> {
                favorite()
                true
            }
            else -> false
        }
    }

    override fun favorite() {
        if(isFavorite){
            favoriteDB.remove(event)
        }else{
            favoriteDB.add(event)
        }
        isFavorite=!isFavorite
        view.updateFavoriteLayout(isFavorite)

    }

}