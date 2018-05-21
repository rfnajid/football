package com.nnn.footballclub.pages.detail

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nnn.footballclub.R
import com.nnn.footballclub.model.Event
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity(), DetailContract.View{

    private lateinit var menu : Menu

    private lateinit var presenter : DetailContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        presenter = DetailPresenter(
                intent.getSerializableExtra("extra") as Event,
                this
        )

        supportActionBar?.title = getString(R.string.page_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menu=menu

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_detail, this.menu)

        presenter.start()

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

    override fun setPresenter(presenter: DetailContract.Presenter) {
        this.presenter = checkNotNull(presenter)
    }

    override fun loadData(event : Event){

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
        var req : RequestOptions = RequestOptions().placeholder(R.color.grey).fitCenter()
        Glide.with(this)
                .load(event.homeTeam.logo)
                .apply(req)
                .into(imgHome)

        Glide.with(this)
                .load(event.awayTeam.logo)
                .apply(req)
                .into(imgAway)
    }
}
