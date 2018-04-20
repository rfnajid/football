package com.nnn.footballclub.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.request.RequestOptions
import com.nnn.footballclub.R
import com.nnn.footballclub.activity.DetailActivity
import com.nnn.footballclub.layout.ItemUI
import com.nnn.footballclub.model.Item
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.startActivity


/**
 * Created by ridhaaaaazis on 19/04/18.
 */

class RecyclerViewAdapter(private val context: Context, private val items: List<Item>)
    : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(
                    ItemUI().createView(AnkoContext.Companion.create(context,parent,false)
                    )
            )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        val name = view.findViewById<TextView>(R.id.text)
        val image = view.findViewById<ImageView>(R.id.img)
        val card = view.findViewById<CardView>(R.id.cardView)

        fun bindItem(item: Item) {
            name.text = item.name

            var req : RequestOptions = RequestOptions().placeholder(R.color.grey).fitCenter()

            Glide.with(itemView.context)
                    .load(item.image)
                    .apply(req)
                    .into(image)

            card.setOnClickListener{
                itemView.context.startActivity<DetailActivity>("extra" to item)
            }
        }


    }
}