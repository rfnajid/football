package com.nnn.footballclub.layout

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.nnn.footballclub.R
import com.nnn.footballclub.activity.DetailActivity
import com.nnn.footballclub.activity.MainActivity
import com.nnn.footballclub.adapter.RecyclerViewAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * Created by ridhaaaaazis on 19/04/18.
 */

class DetailActivityUI : AnkoComponent<DetailActivity> {

    lateinit var img : ImageView
    lateinit var desc : TextView

    override fun createView(ui: AnkoContext<DetailActivity>) = with(ui) {
        verticalLayout{
            lparams(width=matchParent,height=matchParent)
            padding=dip(16)

            img = imageView(R.drawable.img_acm)
                    .lparams(width=matchParent, height= dip(100)){
                        gravity = Gravity.CENTER
                    }

            desc = textView("tes uji coba").lparams(width=matchParent,height=wrapContent){
                topMargin=dip(16)
            }

        }
    }
}