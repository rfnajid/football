package com.nnn.footballclub.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.nnn.footballclub.R
import com.nnn.footballclub.R.array.*
import com.nnn.footballclub.adapter.RecyclerViewAdapter
import com.nnn.footballclub.layout.MainActivityUI
import com.nnn.footballclub.model.Item
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {

    lateinit var adapter : RecyclerViewAdapter
    var items : MutableList<Item> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initData();

        adapter = RecyclerViewAdapter(this, items)
        MainActivityUI(adapter).setContentView(this)
    }

    private fun initData(){
        val name = resources.getStringArray(club_name)
        val image = resources.obtainTypedArray(club_image)
        val desc = resources.getStringArray(club_desc)
        items.clear()
        for (i in name.indices) {
            items.add(Item(name[i],
                    image.getResourceId(i, 0),
                    desc[i]))
        }

        //Recycle the typed array
        image.recycle()
    }
}


