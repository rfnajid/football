package com.nnn.footballclub.pages.detail.player

import android.os.Bundle
import com.bumptech.glide.Glide
import com.nnn.footballclub.R
import com.nnn.footballclub.model.Player
import com.nnn.footballclub.utils.Global
import com.nnn.footballclub.utils.base.BackButtonActivity
import kotlinx.android.synthetic.main.activity_player_detail.*


/**
 * Created by ridhaaaaazis on 03/06/18.
 */

class PlayerDetailActivity : BackButtonActivity (){

    lateinit var player : Player

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)

        player = intent.getSerializableExtra("extra") as Player

        setBackActionBar(player.name)

        textPosition.text=player.position
        textNationality.text=player.nationality
        textBorn.text=player.born()
        textHeight.text = player.height
        textWeight.text = player.weight
        textTeam.text = player.team
        textDesc.text = player.desc


        Glide.with(this)
                .load(player.pp)
                .apply(Global.glideRequestOptions())
                .into(img)
    }
}