package com.nnn.footballclub.pages

import android.test.mock.MockContext
import com.google.gson.Gson
import com.nnn.footballclub.TestContextProvider
import com.nnn.footballclub.adapter.RecyclerViewAdapter
import com.nnn.footballclub.model.Event
import com.nnn.footballclub.model.responses.EventResponse
import com.nnn.footballclub.pages.main.ListPresenter
import com.nnn.footballclub.pages.main.MainContract
import com.nnn.footballclub.utils.Global
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

class ListPresenterTest{

    private lateinit var presenter : ListPresenter

    @Mock
    private lateinit var view : MainContract.ListView

    @Mock
    private val context = MockContext()

    @Mock
    private lateinit var gson : Gson

    @Mock
    private lateinit var eventResponse : EventResponse

    @Mock
    private lateinit var listEvent : List<Event>

    @Mock
    private lateinit var adapter : RecyclerViewAdapter


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = ListPresenter(view, TestContextProvider())
    }


    @Test
    fun testGetPast(){

        presenter.start(context)

        `when`(view.type).thenReturn(ListPresenter.TYPE.PAST)

        `when`(gson.fromJson(SportsDBApiAnko
                .doRequest(SportsDBApiAnko.getPast(Global.idLeague)),
                EventResponse::class.java
        )).thenReturn(eventResponse)


        presenter.loadData()


        `when`(eventResponse.events).thenReturn(listEvent)

        verify(eventResponse.events)
    }

    @Test
    fun testLoadDone(){
        `when`(listEvent.size).thenReturn(15)
        presenter.adapter=adapter

        presenter.checkLoadEvent(15)

        verify(view).loading(false)
    }


}
