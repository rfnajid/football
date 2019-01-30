package com.nnn.footballclub.pages.main

import android.content.Context
import android.test.mock.MockContext
import com.google.gson.Gson
import com.nnn.footballclub.TestContextProvider
import com.nnn.footballclub.model.Player
import com.nnn.footballclub.model.Team
import com.nnn.footballclub.model.responses.PlayerResponse
import com.nnn.footballclub.pages.detail.team.TeamDetailContract
import com.nnn.footballclub.pages.detail.team.player.PlayerItemAdapter
import com.nnn.footballclub.pages.detail.team.player.PlayerListPresenter
import com.nnn.footballclub.utils.network.SportsDBApiAnko
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


/**
 * Created by ridhaaaaazis on 24/05/18.
 */

class PlayerListPresenterTest{

    private lateinit var presenter : PlayerListPresenter

    @Mock
    private lateinit var view : TeamDetailContract._PlayerView

    @Mock
    private lateinit var gson : Gson

    @Mock
    private lateinit var response : PlayerResponse

    @Mock
    private lateinit var list : List<Player>

    @Mock
    private lateinit var adapter : PlayerItemAdapter

    @Mock
    private lateinit var team : Team

    @Mock
    private val context : Context = mock(Context::class.java)

    private var mockLong : Long = 15

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = PlayerListPresenter(view, TestContextProvider())

        presenter.start(context)
        view.adapter = adapter
        presenter.team = team
    }


    @Test
    fun testLoadNormal(){

        `when`(
                GlobalScope.launch(coroutineContext.main) {
            val data = Global.gson.fromJson(SportsDBApiAnko
                    .doRequest(req).await(),
                    PlayerResponse::class.java
            )

            Global.log("ASYNC PLAYER LIST")

            loadToView(data)
        }
                gson.fromJson(SportsDBApiAnko
                .doRequest(SportsDBApiAnko.getPlayers(mockLong)),
                PlayerResponse::class.java
        )).thenReturn(response)

        presenter.loadData()

        `when`(response.players).thenReturn(list)

    }

    @Test
    fun testLoadPlayerSuccess(){

        `when` (response.players).thenReturn(list, mutableListOf())
        `when` (list.isEmpty()).thenReturn(false)

        presenter.loadToView(response)

        verify(view).loading(false)
    }

    @Test
    fun testLoadPlayerEmpty(){


        `when` (response.players).thenReturn(null)

        presenter.loadToView(response)

        verify(view).empty()
    }


}

// TODO test still not working for new coroutine system