package com.nnn.footballclub.pages

import android.content.Context
import android.test.mock.MockContext
import com.nnn.footballclub.model.Event
import com.nnn.footballclub.model.db.FavoriteDB
import com.nnn.footballclub.pages.detail.DetailContract
import com.nnn.footballclub.pages.detail.DetailPresenter
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


/**
 * Created by ridhaaaaazis on 24/05/18.
 */

class DetailPresenterTest{
    @Mock
    private lateinit var presenter : DetailPresenter
    @Mock
    private val context : Context = MockContext()
    @Mock
    private lateinit var event : Event

    @Mock
    private lateinit var favoriteDB : FavoriteDB

    @Mock
    private lateinit var view : DetailContract.View

    @Before
    fun setUp () {
        MockitoAnnotations.initMocks(this)
        presenter = DetailPresenter(event,view)
    }

    @Test
    fun testFavorite(){
        //load Favorite
        `when`(favoriteDB.isExist(event)).thenReturn(true)

        presenter.startTest(context,favoriteDB)

        verify(view).updateFavoriteLayout(true)

        //unfavorite

        presenter.favorite()

        verify(view).updateFavoriteLayout(false)


    }
}