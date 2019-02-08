package com.nnn.footballclub.pages.main

import com.google.gson.Gson
import com.nnn.footballclub.TestContextProvider
import com.nnn.footballclub.model.Team
import com.nnn.footballclub.model.db.FavoriteTeamDB
import com.nnn.footballclub.model.favorite.FavTeam
import com.nnn.footballclub.model.responses.TeamResponse
import com.nnn.footballclub.pages.main.team.TeamItemAdapter
import com.nnn.footballclub.pages.main.team.TeamListPresenter
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

class TeamListPresenterTest{

    private lateinit var presenter : TeamListPresenter

    @Mock
    private lateinit var view : MainContract._TeamListView

    @Mock
    private lateinit var favoriteTeamDB: FavoriteTeamDB

    @Mock
    private lateinit var gson : Gson

    @Mock
    private lateinit var response : TeamResponse

    @Mock
    private lateinit var list : MutableList<Team>

    @Mock
    private lateinit var listFav : List<FavTeam>

    @Mock
    private lateinit var adapter : TeamItemAdapter

    private var leagueId : Long = 4328

    private var listSize : Int = 15

    private var searchQuery = "Arsenal"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = TeamListPresenter(view, TestContextProvider())
    }


    @Test
    fun testLoadNormal() = runBlocking<Unit>{

        presenter.start(favoriteTeamDB)

        presenter.type = TeamListPresenter.TYPE.NORMAL

        `when`(gson.fromJson(SportsDBApiAnko
                .doRequest(SportsDBApiAnko.getTeam(leagueId)).await(),
                TeamResponse::class.java
        )).thenReturn(response)

        `when`(view.adapter).thenReturn(adapter)

        presenter.loadData()

        `when`(response.teams).thenReturn(list)

    }

    @Test
    fun testLoadSearch() = runBlocking<Unit>{

        presenter.start(favoriteTeamDB)

        presenter.type=TeamListPresenter.TYPE.SEARCH
        presenter.query= searchQuery

        `when`(gson.fromJson(SportsDBApiAnko
                .doRequest(SportsDBApiAnko.searchTeam(searchQuery)).await(),
                TeamResponse::class.java
        )).thenReturn(response)

        `when`(view.adapter).thenReturn(adapter)

        presenter.loadData()

        `when`(response.teams).thenReturn(list)

    }

    @Test
    fun testLoadFavoriteSuccess() = runBlocking<Unit>{

        var iterator = mock(Iterator::class.java)

        presenter.start(favoriteTeamDB)

        `when`(favoriteTeamDB.getAll()).thenReturn(listFav)

        `when`(listFav.size).thenReturn(listSize)

        `when` (listFav.isEmpty()).thenReturn(false)

        `when` (listFav.iterator()).thenReturn(iterator as Iterator<FavTeam>)

        `when` (view.adapter).thenReturn(adapter)

        presenter.loadFavorite()

        verify(view).loading(false)
    }

    @Test
    fun testLoadFavoriteEmpty(){

        presenter.start(favoriteTeamDB)

        `when`(favoriteTeamDB.getAll()).thenReturn(listFav)

        `when`(listFav.size).thenReturn(listSize)

        `when` (listFav.isEmpty()).thenReturn(true)

        `when`(view.adapter).thenReturn(adapter)

        presenter.loadFavorite()

        verify(view).empty()

    }

    @Test
    fun testLoadTeamSuccess(){

        presenter.start(favoriteTeamDB)

        `when` (response.teams).thenReturn(list, mutableListOf())
        `when` (list.isEmpty()).thenReturn(false)

        `when` (view.adapter).thenReturn(adapter)

        presenter.loadTeam(response)

        verify(view).loading(false)
    }

    @Test
    fun testLoadTeamEmpty(){

        presenter.start(favoriteTeamDB)

        `when` (response.teams).thenReturn(null)

        `when` (view.data).thenReturn(list)

        `when` (view.adapter).thenReturn(adapter)

        presenter.loadTeam(response)

        verify(view).empty()
    }


}
