package com.nnn.footballclub.pages.detail.match

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.nnn.footballclub.R
import com.nnn.footballclub.model.Event
import com.nnn.footballclub.utils.Global
import com.nnn.footballclub.utils.base.BackButtonActivity
import com.nnn.footballclub.utils.base.BaseDetailContract
import kotlinx.android.synthetic.main.activity_event_detail.*


class MatchDetailActivity : BackButtonActivity(), BaseDetailContract._View<Event> {

    private lateinit var menu : Menu

    private lateinit var presenter : BaseDetailContract._Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        presenter = MatchDetailPresenter(
                intent.getSerializableExtra("extra") as Event,
                this
        )

        super.setBackActionBar(getString(R.string.page_detail))

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menu=menu

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

    override fun updateFavoriteLayout(isFavorite : Boolean) {
        var drawableFavorite = ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_white_24dp)
        if (isFavorite)
            drawableFavorite = ContextCompat.getDrawable(this, R.drawable.ic_favorite_white_24dp)
        menu.getItem(0).icon = drawableFavorite
    }

    override fun setPresenter(presenter: BaseDetailContract._Presenter) {
        this.presenter = checkNotNull(presenter)
    }

    override fun loadData(event : Event){

        loading.visibility= View.GONE

        textDate.text=event.date()

        //HOME
        textTeamHome.text       = event.homeTeam.name
        textScoreHome.text      = event.homeScore.toString()
        textGoalHome.text       = event.homeGoals()
        textRedHome.text        = event.homeRed()
        textYellowHome.text     = event.homeYellow()
        textShotHome.text       = event.homeShots.toString()
        textFormationHome.text  = event.homeFormation()

        textGKHome.text         = event.homeGK()
        textDFHome.text         = event.homeDF()
        textMFHome.text         = event.homeMF()
        textFWHome.text         = event.homeFW()
        textSubHome.text        = event.homeSubs()

        //AWAY
        textTeamAway.text       = event.awayTeam.name
        textScoreAway.text      = event.awayScore.toString()
        textGoalAway.text       = event.awayGoals()
        textRedAway.text        = event.awayRed()
        textYellowAway.text     = event.awayYellow()
        textShotAway.text       = event.awayShots.toString()
        textFormationAway.text  = event.awayFormation()

        textGKAway.text         = event.awayGK()
        textDFAway.text         = event.awayDF()
        textMFAway.text         = event.awayMF()
        textFWAway.text         = event.awayFW()
        textSubAway.text        = event.awaySubs()

        //IMAGES
        Glide.with(this)
                .load(event.homeTeam.logo)
                .apply(Global.glideRequestOptions())
                .into(imgHome)

        Glide.with(this)
                .load(event.awayTeam.logo)
                .apply(Global.glideRequestOptions())
                .into(imgAway)
    }
}
