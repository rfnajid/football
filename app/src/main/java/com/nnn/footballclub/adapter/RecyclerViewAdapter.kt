package com.nnn.footballclub.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nnn.footballclub.R
import com.nnn.footballclub.model.Event
import com.nnn.footballclub.pages.detail.DetailActivity
import kotlinx.android.synthetic.main.item_view.view.*
import org.jetbrains.anko.startActivity


/**
 * Created by ridhaaaaazis on 19/04/18.
 */

class RecyclerViewAdapter(private val context: Context, private val data: List<Event>)
    : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(

                    LayoutInflater.from(context).inflate(R.layout.item_view,parent,false)
            )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position])
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        val view = view

        fun bindItem(data: Event) {

            view.textDate.text = data.date()

            view.titleHome.text=data.homeTeam.name
            view.titleAway.text=data.awayTeam.name

            view.scoreHome.text=""
            view.scoreAway.text=""
            if(data.homeScore !=null ){
                view.scoreHome.text=data.homeScore.toString()
                view.scoreAway.text=data.awayScore.toString()
            }

            var req : RequestOptions = RequestOptions().placeholder(R.color.grey).fitCenter()
            Glide.with(itemView.context)
                    .load(data.homeTeam.logo)
                    .apply(req)
                    .into(view.imgHome)
            Glide.with(itemView.context)
                    .load(data.awayTeam.logo)
                    .apply(req)
                    .into(view.imgAway)

            view.card.setOnClickListener{
                itemView.context.startActivity<DetailActivity>("extra" to data)
            }
        }
    }
}