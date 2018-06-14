package com.nnn.footballclub.pages.detail.team.player

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.nnn.footballclub.R
import com.nnn.footballclub.model.Player
import com.nnn.footballclub.pages.detail.player.PlayerDetailActivity
import com.nnn.footballclub.utils.Global
import kotlinx.android.synthetic.main.item_player.view.*
import org.jetbrains.anko.startActivity


/**
 * Created by ridhaaaaazis on 02/06/18.
 */
class PlayerItemAdapter(private val context: Context, private val data: List<Player>)
    : RecyclerView.Adapter<PlayerItemAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(
                    LayoutInflater.from(context).inflate(R.layout.item_player, parent, false)
            )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position])
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        fun bindItem(data:  Player) {
            itemView.textName.text=data.name
            itemView.textPosition.text=data.position

            Glide.with(itemView.context)
                    .load(data.pp)
                    .apply(Global.glideRequestOptions())
                    .into(itemView.img)

            itemView.setOnClickListener {
                itemView.context.startActivity<PlayerDetailActivity>("extra" to data)
            }
        }
    }
}