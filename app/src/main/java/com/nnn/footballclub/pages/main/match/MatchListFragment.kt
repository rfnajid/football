package com.nnn.footballclub.pages.main.match
import com.nnn.footballclub.pages.main.MainContract
import com.nnn.footballclub.pages.main.match.MatchListPresenter.TYPE

/**
 * Created by ridhaaaaazis on 30/04/18.
 */

class MatchListFragment : MainContract._MatchListView() {

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