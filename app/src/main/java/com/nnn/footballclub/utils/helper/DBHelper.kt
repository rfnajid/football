package com.nnn.footballclub.utils.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.nnn.footballclub.model.Event
import org.jetbrains.anko.db.*


/**
 * Created by ridhaaaaazis on 14/05/18.
 */

class DBHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoriteTeam.db", null, 1) {
    companion object {
        private var instance: DBHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DBHelper {
            if (instance == null) {
                instance = DBHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable(Event.TABLE_FAVORITE, true,
                Event.ID to INTEGER + PRIMARY_KEY
                )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable(Event.TABLE_FAVORITE, true)
    }
}

// Access property for Context
val Context.database: DBHelper
    get() = DBHelper.getInstance(applicationContext)