package com.nnn.footballclub.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nnn.footballclub.R
import com.nnn.footballclub.layout.DetailActivityUI
import com.nnn.footballclub.layout.MainActivityUI
import com.nnn.footballclub.model.Item
import org.jetbrains.anko.setContentView

class DetailActivity : AppCompatActivity() {

    lateinit var item : Item
    val ui : DetailActivityUI = DetailActivityUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui.setContentView(this)

        item = intent.getSerializableExtra("extra") as Item

        supportActionBar?.title = item.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        ui.desc.text=item.desc

        var req : RequestOptions = RequestOptions().placeholder(R.color.grey).fitCenter()
        Glide.with(this)
                .load(item.image)
                .apply(req)
                .into(ui.img)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId == android.R.id.home){
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
