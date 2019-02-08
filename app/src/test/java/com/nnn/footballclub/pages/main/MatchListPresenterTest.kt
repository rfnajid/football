package com.nnn.footballclub.pages.main

import android.content.Context
import com.google.gson.Gson
import com.nnn.footballclub.TestContextProvider
import com.nnn.footballclub.model.Event
import com.nnn.footballclub.model.db.FavoriteEventDB
import com.nnn.footballclub.model.favorite.FavEvent
import com.nnn.footballclub.model.responses.EventResponse
import com.nnn.footballclub.model.responses.TeamResponse
import com.nnn.footballclub.pages.main.match.MatchItemAdapter
import com.nnn.footballclub.pages.main.match.MatchListPresenter
import com.nnn.footballclub.utils.network.SportsDBApiAnko
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


/**
 * Created by ridhaaaaazis on 24/05/18.
 */

class MatchListPresenterTest{

    private lateinit var presenter : MatchListPresenter

    @Mock
    private lateinit var view : MainContract._MatchListView

    @Mock
    private lateinit var favoriteDB: FavoriteEventDB

    @Mock
    private val context: Context = mock(Context::class.java)

    @Mock
    private lateinit var gson : Gson

    @Mock
    private lateinit var eventResponse : EventResponse

    @Mock
    private lateinit var teamResponse : TeamResponse

    @Mock
    private lateinit var list : List<Event>

    @Mock
    private lateinit var listFav : List<FavEvent>

    @Mock
    private lateinit var adapter : MatchItemAdapter

    private var leagueId : Long = 4328

    private var listSize : Int = 15

    private var searchQuery = "Arsenal"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchListPresenter(view, TestContextProvider())

        presenter.start(context)
        presenter.favoriteEventDB=favoriteDB
    }


    @Test
    fun testLoadData() = runBlocking<Unit>{

        presenter.type = MatchListPresenter.TYPE.PAST

        `when`(
                gson.fromJson(SportsDBApiAnko
                .doRequest(SportsDBApiAnko.getPast(leagueId)).await(),
                EventResponse::class.java)
        ).thenReturn(eventResponse)

        `when`(view.adapter).thenReturn(adapter)

        presenter.loadData()

        `when`(eventResponse.events).thenReturn(list)

    }

    @Test
    fun testLoadSearch() = runBlocking<Unit>{

        presenter.type=MatchListPresenter.TYPE.SEARCH
        presenter.query= searchQuery

        `when`(gson.fromJson(SportsDBApiAnko
                .doRequest(SportsDBApiAnko.searchMatch(searchQuery)).await(),
                EventResponse::class.java
        )).thenReturn(eventResponse)

        presenter.loadData()

        `when`(eventResponse.events).thenReturn(list)

    }

    @Test
    fun testLoadFavoriteSuccess(){

        var iterator = mock(Iterator::class.java)

        `when`(favoriteDB.getAll()).thenReturn(listFav)

        `when`(listFav.size).thenReturn(listSize)

        `when` (listFav.isEmpty()).thenReturn(false)

        `when` (listFav.iterator()).thenReturn(iterator as Iterator<FavEvent>)

        `when` (view.adapter).thenReturn(adapter)

        presenter.loadFavorite()

        verify(view).loading(false)
    }

    @Test
    fun testLoadFavoriteEmpty(){

        `when`(favoriteDB.getAll()).thenReturn(listFav)

        `when`(listFav.size).thenReturn(listSize)

        `when` (listFav.isEmpty()).thenReturn(true)

        presenter.loadFavorite()

        verify(view).empty()

    }

    @Test
    fun testLoadMatchSuccess() = runBlocking {

        var mockEvent : Event = mock(Event::class.java)

        `when` (eventResponse.events).thenReturn(list, mutableListOf())
        `when` (list.isEmpty()).thenReturn(false)
        `when` (list.size).thenReturn(listSize)
        `when` (list.get(ArgumentMatchers.anyInt())).thenReturn(mockEvent)
        `when` (mockEvent.name).thenReturn(ArgumentMatchers.anyString())

        `when`(gson.fromJson(SportsDBApiAnko
                .doRequest(SportsDBApiAnko.getTeam(ArgumentMatchers.anyLong())).await(),
                TeamResponse::class.java
        )).thenReturn(teamResponse)

        `when` (view.adapter).thenReturn(adapter)

        presenter.loadEvent(eventResponse)

    }

    @Test
    fun testLoadMatchEmpty(){

        `when` (eventResponse.events).thenReturn(null)

        `when` (view.adapter).thenReturn(adapter)

        presenter.loadEvent(eventResponse)

        verify(view).empty()
    }


}
