package com.nnn.footballclub.pages.detail

import android.content.Context
import com.google.gson.Gson
import com.nnn.footballclub.TestContextProvider
import com.nnn.footballclub.model.Event
import com.nnn.footballclub.model.db.FavoriteEventDB
import com.nnn.footballclub.model.responses.EventResponse
import com.nnn.footballclub.model.responses.TeamResponse
import com.nnn.footballclub.pages.detail.match.MatchDetailPresenter
import com.nnn.footballclub.utils.base.BaseDetailContract
import com.nnn.footballclub.utils.network.SportsDBApiAnko
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


/**
 * Created by ridhaaaaazis on 24/05/18.
 */

class MatchDetailPresenterTest{
    @Mock
    private lateinit var presenterMatch : MatchDetailPresenter

    @Mock
    private val context : Context = mock(Context::class.java)

    @Mock
    private lateinit var event : Event

    @Mock
    private lateinit var favoriteEventDB : FavoriteEventDB

    @Mock
    private lateinit var view : BaseDetailContract._View<Event>

    @Mock
    private lateinit var eventResponse: EventResponse

    @Mock
    private lateinit var teamResponse: TeamResponse

    @Mock
    private lateinit var gson : Gson

    private var eventId: Long = 526905

    private var teamId: Long = 133619

    @Before
    fun setUp () {
        MockitoAnnotations.initMocks(this)
        presenterMatch = MatchDetailPresenter(event, view,TestContextProvider())
    }

    @Test
    fun testFavorite() = runBlocking<Unit>{
        //load Favorite

        `when`(favoriteEventDB.isExist(event)).thenReturn(true)

        `when`(event.isACopyOfFavEvent()).thenReturn(true)

        `when`(gson.fromJson(SportsDBApiAnko
                .doRequest(SportsDBApiAnko.getEvent(eventId)).await(),
                EventResponse::class.java
        )).thenReturn(eventResponse)

        `when`(gson.fromJson(SportsDBApiAnko
                .doRequest(SportsDBApiAnko.getTeam(teamId)).await(),
                TeamResponse::class.java
        )).thenReturn(teamResponse)

        presenterMatch.start(context,favoriteEventDB)

        verify(view).updateFavoriteLayout(true)

        //unfavorite

        presenterMatch.favorite()

        verify(view).updateFavoriteLayout(false)

    }

    @Test
    fun testNormal(){
        `when`(favoriteEventDB.isExist(event)).thenReturn(false)

        `when`(event.isACopyOfFavEvent()).thenReturn(false)

        presenterMatch.start(context,favoriteEventDB)

        verify(view).loadData(event)
    }
}