package com.nnn.footballclub.pages.detail

import android.content.Context
import android.test.mock.MockContext
import com.google.gson.Gson
import com.nnn.footballclub.TestContextProvider
import com.nnn.footballclub.model.Team
import com.nnn.footballclub.model.db.FavoriteTeamDB
import com.nnn.footballclub.model.responses.TeamResponse
import com.nnn.footballclub.pages.detail.team.TeamDetailPresenter
import com.nnn.footballclub.utils.base.BaseDetailContract
import com.nnn.footballclub.utils.network.SportsDBApiAnko
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


/**
 * Created by ridhaaaaazis on 24/05/18.
 */

class TeamDetailPresenterTest{
    @Mock
    private lateinit var presenterTeam : TeamDetailPresenter

    @Mock
    private val context : Context = MockContext()

    @Mock
    private lateinit var team : Team

    @Mock
    private lateinit var favoriteTeamDB : FavoriteTeamDB

    @Mock
    private lateinit var view : BaseDetailContract._View<Team>

    @Mock
    private lateinit var teamResponse: TeamResponse

    @Mock
    private lateinit var gson : Gson

    private var teamId: Long = 133619

    @Before
    fun setUp () {
        MockitoAnnotations.initMocks(this)
        presenterTeam = TeamDetailPresenter(team, view,TestContextProvider())
    }

    @Test
    fun testFavorite(){
        //load Favorite

        `when`(favoriteTeamDB.isExist(team)).thenReturn(true)

        `when`(team.isACopyOfFavTeam()).thenReturn(true)

        `when`(gson.fromJson(SportsDBApiAnko
                .doRequest(SportsDBApiAnko.getTeam(teamId)),
                TeamResponse::class.java
        )).thenReturn(teamResponse)

        `when`(gson.fromJson(SportsDBApiAnko
                .doRequest(SportsDBApiAnko.getTeam(teamId)),
                TeamResponse::class.java
        )).thenReturn(teamResponse)

        presenterTeam.start(context,favoriteTeamDB)

        verify(view).updateFavoriteLayout(true)

        //unfavorite

        presenterTeam.favorite()

        verify(view).updateFavoriteLayout(false)

    }

    @Test
    fun testNormal(){
        `when`(favoriteTeamDB.isExist(team)).thenReturn(false)

        `when`(team.isACopyOfFavTeam()).thenReturn(false)

        presenterTeam.start(context,favoriteTeamDB)

        verify(view).loadData(team)
    }
}