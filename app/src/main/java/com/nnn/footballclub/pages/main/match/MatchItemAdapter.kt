package com.nnn.footballclub.pages.main.match

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.nnn.footballclub.R
import com.nnn.footballclub.model.Event
import com.nnn.footballclub.pages.detail.match.MatchDetailActivity
import com.nnn.footballclub.utils.Global
import kotlinx.android.synthetic.main.item_match.view.*
import org.jetbrains.anko.startActivity


/**
 * Created by ridhaaaaazis on 19/04/18.
 */

class MatchItemAdapter(private val context: Context, private val data: List<Event>)
    : RecyclerView.Adapter<MatchItemAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(
                    LayoutInflater.from(context).inflate(R.layout.item_match, parent, false)
            )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position])
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        fun bindItem(data: Event?) {

            itemView.textDate.text = data?.date()

            itemView.titleHome.text= data?.homeTeam?.name
            itemView.titleAway.text= data?.awayTeam?.name

            itemView.scoreHome.text=""
            itemView.scoreAway.text=""
            if(data?.homeScore !=null ){
                itemView.scoreHome.text=data.homeScore.toString()
                itemView.scoreAway.text=data.awayScore.toString()
            }

            Glide.with(itemView.context)
                    .load(data?.homeTeam?.logo)
                    .apply(Global.glideRequestOptions())
                    .into(itemView.imgHome)
            Glide.with(itemView.context)
                    .load(data?.awayTeam?.logo)
                    .apply(Global.glideRequestOptions())
                    .into(itemView.imgAway)

            itemView.card.setOnClickListener{
                if(data?.homeTeam!=null && data.awayTeam!=null) {
                    itemView.context.startActivity<MatchDetailActivity>("extra" to data)
                }
            }
        }
    }
}