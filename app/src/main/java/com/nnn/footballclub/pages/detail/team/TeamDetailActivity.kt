package com.nnn.footballclub.pages.detail.team

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.nnn.footballclub.R
import com.nnn.footballclub.model.Team
import com.nnn.footballclub.pages.detail.team.player.PlayerFragment
import com.nnn.footballclub.utils.Global
import com.nnn.footballclub.utils.base.BackButtonActivity
import com.nnn.footballclub.utils.base.BaseDetailContract
import kotlinx.android.synthetic.main.activity_team_detail.*


/**
 * Created by ridhaaaaazis on 30/05/18.
 */

class TeamDetailActivity : BackButtonActivity(),
        BaseDetailContract._View<Team>,
        TabLayout.OnTabSelectedListener
{

    lateinit var teamDetailPresenter: BaseDetailContract._Presenter

    internal lateinit var presenter : BaseDetailContract._Presenter

    internal lateinit var team : Team
    internal lateinit var menu : Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)

        val team : Team = intent.getSerializableExtra("extra") as Team

        setBackActionBar(team.name,toolbar)

        presenter = TeamDetailPresenter(
                intent.getSerializableExtra("extra") as Team,
                this
        )

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menu =menu

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_favorite, this.menu)

        presenter.start(this)

        return super.onCreateOptionsMenu(this.menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?) : Boolean {
        var res = presenter.onOptionsItemSelected(item?.itemId)
        if (!res){
            res = super.onOptionsItemSelected(item)
        }
        return res
    }

    override fun loadData(data: Team) {

        team = data

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_overview)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_player)))
        tabLayout.addOnTabSelectedListener(this)

    //IMAGES
        Glide.with(this)
                .load(data.stadiumImage)
                .apply(Global.glideRequestOptions())
                .into(imgHeader)

        Glide.with(this)
                .load(data.logo)
                .apply(Global.glideRequestOptions())
                .into(imgLogo)


        updateFragment(tabLayout.selectedTabPosition)

    }

    override fun updateFavoriteLayout(isFavorite : Boolean) {
        var drawableFavorite = ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_white_24dp)
        if (isFavorite)
            drawableFavorite = ContextCompat.getDrawable(this, R.drawable.ic_favorite_white_24dp)
        menu.getItem(0).icon = drawableFavorite
    }


    override fun setPresenter(presenter: BaseDetailContract._Presenter) {
        teamDetailPresenter = checkNotNull(presenter)
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
        //not implemented
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        //not implemented
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        updateFragment(tab?.position as Int)
    }

    private fun updateFragment(pos : Int){
        lateinit var fragment: Fragment
        when(pos){
            0 -> {
                fragment = OverviewFragment.create(team)
            }
            1 -> {
                fragment = PlayerFragment.create(team)
            }
        }
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frame, fragment)
        ft.commit()
    }
}