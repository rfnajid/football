package com.nnn.footballclub.pages.main.match
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nnn.footballclub.pages.main.MainContract
import com.nnn.footballclub.pages.main.match.MatchListPresenter.TYPE

/**
 * Created by ridhaaaaazis on 30/04/18.
 */

class MatchListFragment : MainContract._MatchListView() {

    override lateinit var adapter: MatchItemAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.adapter = MatchItemAdapter(context!!,data)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    companion object {
        fun create(type : TYPE, idLeague : Int) : MatchListFragment {
            val fragment = MatchListFragment()
            val presenter = MatchListPresenter(fragment)
            presenter.type=type
            presenter.idLeague=idLeague
            fragment.setPresenter(presenter)
            return fragment
        }

        fun createFavorite() : MatchListFragment{
            val fragment = MatchListFragment()
            val presenter = MatchListPresenter(fragment)
            presenter.type=TYPE.FAVORITE
            fragment.setPresenter(presenter)
            return fragment
        }

        fun createSearch(query : String) : MatchListFragment{
            val fragment = MatchListFragment()
            val presenter = MatchListPresenter(fragment)
            presenter.type=TYPE.SEARCH
            presenter.query = query
            fragment.setPresenter(presenter)
            return fragment
        }
    }


}