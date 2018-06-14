package com.nnn.footballclub.utils.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.nnn.footballclub.model.favorite.FavEvent
import com.nnn.footballclub.model.favorite.FavTeam
import org.jetbrains.anko.db.*


/**
 * Created by ridhaaaaazis on 14/05/18.
 */

class DBHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "Favorite.db", null, 1) {
    companion object {
        private var instance: DBHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DBHelper {
            if (instance == null) {
                instance = DBHelper(ctx.applicationContext)
            }
            return instance as DBHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable(FavEvent.TABLE, true,
                FavEvent.ID to INTEGER + PRIMARY_KEY,
                FavEvent.DATE to TEXT,
                FavEvent.HOMENAME to TEXT,
                FavEvent.HOMELOGO to TEXT,
                FavEvent.HOMESCORE to INTEGER,
                FavEvent.AWAYNAME to TEXT,
                FavEvent.AWAYLOGO to TEXT,
                FavEvent.AWAYSCORE to INTEGER
                )

        db.createTable(FavTeam.TABLE,true,
                FavTeam.ID to INTEGER + PRIMARY_KEY,
                FavTeam.NAME to TEXT,
                FavTeam.LOGO to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable(FavEvent.TABLE, true)
        db.dropTable(FavTeam.TABLE, true)
    }
}

// Access property for Context
val Context.database: DBHelper
    get() = DBHelper.getInstance(applicationContext)