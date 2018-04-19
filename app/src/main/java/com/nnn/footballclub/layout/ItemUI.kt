package com.nnn.footballclub.layout

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.nnn.footballclub.R
import com.nnn.footballclub.activity.DetailActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.sdk25.coroutines.onClick


/**
 * Created by ridhaaaaazis on 19/04/18.
 */
class ItemUI : AnkoComponent<ViewGroup>{

    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui){
            relativeLayout {
                lparams(width=matchParent,height= wrapContent)
                cardView {
                    layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT).apply {
                        leftMargin = dip(10)
                        rightMargin = dip(10)
                        topMargin = dip(5)
                        bottomMargin = dip(5)
                    }
                    id=R.id.cardView
                    backgroundColor = Color.WHITE
                    radius = dip(8).toFloat()

                    relativeLayout {
                        lparams(width = matchParent, height = dip(56))
                                .padding=dip(5)

                        //img
                        imageView(R.drawable.img_city) {
                            id = R.id.img
                        }.lparams(width = dip(56), height = matchParent) {

                        }

                        //text
                        textView("test") {
                            id = R.id.text
                            singleLine = true
                            leftPadding = dip(5)
                        }.lparams(width = wrapContent, height = wrapContent) {
                            centerVertically()
                            leftMargin = dip(56)
                        }
                    }
                }
            }
        }
    }
}