package com.nnn.footballclub.pages.detail

import android.content.Context
import android.test.mock.MockContext
import com.google.gson.Gson
import com.nnn.footballclub.TestContextProvider
import com.nnn.footballclub.model.Event
import com.nnn.footballclub.model.db.FavoriteEventDB
import com.nnn.footballclub.model.responses.EventResponse
import com.nnn.footballclub.model.responses.TeamResponse
import com.nnn.footballclub.pages.detail.match.MatchDetailPresenter
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

class MatchDetailPresenterTest{
    @Mock
    private lateinit var presenterMatch : MatchDetailPresenter

    @Mock
    private val context : Context = MockContext()

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
    fun testFavorite(){
        //load Favorite

        `when`(favoriteEventDB.isExist(event)).thenReturn(true)

        `when`(event.isACopyOfFavEvent()).thenReturn(true)

        `when`(gson.fromJson(SportsDBApiAnko
                .doRequest(SportsDBApiAnko.getEvent(eventId)),
                EventResponse::class.java
        )).thenReturn(eventResponse)

        `when`(gson.fromJson(SportsDBApiAnko
                .doRequest(SportsDBApiAnko.getTeam(teamId)),
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