package com.nnn.footballclub.pages.main.team

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.nnn.footballclub.R
import com.nnn.footballclub.model.Team
import com.nnn.footballclub.pages.detail.team.TeamDetailActivity
import com.nnn.footballclub.utils.Global
import kotlinx.android.synthetic.main.item_team.view.*
import org.jetbrains.anko.startActivity


/**
 * Created by ridhaaaaazis on 29/05/18.
 */

class TeamItemAdapter (private val context : Context, private val data : List<Team>)
    : RecyclerView.Adapter<TeamItemAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_team,parent,false)
        )

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position])
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        fun bindItem(data : Team){

            itemView.textName.text=data.name

            Glide.with(itemView.context)
                    .load(data.logo)
                    .apply(Global.glideRequestOptions())
                    .into(itemView.imgLogo)

            itemView.setOnClickListener{
                itemView.context.startActivity<TeamDetailActivity>("extra" to data)
            }

        }
    }
}