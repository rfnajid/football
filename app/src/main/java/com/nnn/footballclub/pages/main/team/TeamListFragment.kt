package com.nnn.footballclub.pages.main.team

import com.nnn.footballclub.pages.main.MainContract


/**
 * Created by ridhaaaaazis on 28/05/18.
 */

class TeamListFragment : MainContract._TeamListView()
{

    companion object {
        fun create(idLeague : Int) : TeamListFragment{
            val fragment = TeamListFragment()
            val presenter = TeamListPresenter(fragment)
            presenter.type = TeamListPresenter.TYPE.NORMAL
            presenter.idLeague=idLeague
            fragment.setPresenter(presenter)

            return fragment
        }

        fun createFavorite() : TeamListFragment{
            val fragment = TeamListFragment()
            val presenter = TeamListPresenter(fragment)
            presenter.type=TeamListPresenter.TYPE.FAVORITE
            fragment.setPresenter(presenter)

            return fragment
        }

        fun createSearch(query : String) : TeamListFragment {
            val fragment = TeamListFragment()
            val presenter = TeamListPresenter(fragment)
            presenter.type = TeamListPresenter.TYPE.SEARCH
            presenter.query = query
            fragment.setPresenter(presenter)

            return fragment
        }
    }

}