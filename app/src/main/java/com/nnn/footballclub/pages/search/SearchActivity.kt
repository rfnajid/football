package com.nnn.footballclub.pages.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import com.nnn.footballclub.R
import com.nnn.footballclub.pages.main.MainPresenter
import com.nnn.footballclub.pages.main.match.MatchListFragment
import com.nnn.footballclub.pages.main.team.TeamListFragment
import com.nnn.footballclub.utils.base.BackButtonActivity


class SearchActivity : BackButtonActivity(),
        SearchView.OnQueryTextListener,
        MenuItem.OnActionExpandListener
{

    lateinit var searchView : SearchView

    lateinit var type : MainPresenter.TYPE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        type = intent.getSerializableExtra("type") as MainPresenter.TYPE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)

        val searchItem = menu?.findItem(R.id.menu_search)

        searchView = searchItem?.actionView as SearchView
        searchView.maxWidth=Integer.MAX_VALUE

        searchView.setOnQueryTextListener(this)

        searchItem.expandActionView()
        searchView.requestFocus()

        searchItem.setOnActionExpandListener(this)

        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        search(query.toString())
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        //searchPresenter.search(newText.toString())
        return true
    }

    override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
        return true
    }

    override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
        onBackPressed()
        return true
    }

    private fun selectFragment(fragment : Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frame, fragment)
        ft.commit()
    }

    private fun search(query : String){
        var fragment : Fragment?= null

        when(type){
            MainPresenter.TYPE.TEAM -> {
                fragment = TeamListFragment.createSearch(query)
            }
            MainPresenter.TYPE.MATCH -> {
                fragment = MatchListFragment.createSearch(query)
            }
        }

        selectFragment(fragment)
    }

}
