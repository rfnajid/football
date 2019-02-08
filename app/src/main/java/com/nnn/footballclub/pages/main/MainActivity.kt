package com.nnn.footballclub.pages.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.nnn.footballclub.R
import com.nnn.footballclub.R.layout.activity_main
import com.nnn.footballclub.pages.search.SearchActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity


class MainActivity : AppCompatActivity(), MainContract._MainView,
        BottomNavigationView.OnNavigationItemSelectedListener
{

    private lateinit var searchButton: MenuItem

    private lateinit var mainPresenter : MainContract._MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prepareView()

        mainPresenter = MainPresenter(this)

    }

    override fun setPresenter(presenter: MainContract._MainPresenter) {
        mainPresenter= checkNotNull(presenter)
    }

    override fun changeTitle(newTitle: String) {
        supportActionBar?.title=newTitle
    }

    override fun selectFragment(fragment : Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frame, fragment)
        ft.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search_button, menu)

        searchButton = menu?.findItem(R.id.menu_search) as MenuItem

        searchButton.setOnMenuItemClickListener {
            startActivity<SearchActivity>("type" to mainPresenter.type)
            true
        }

        mainPresenter.start(this)

        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        mainPresenter.loadFragment(item.itemId)
        return true
    }

    override fun hideSearchButton(hide: Boolean) {
        searchButton.isVisible = !hide
    }


    private fun prepareView(){

        setContentView(activity_main)

        navigation.setOnNavigationItemSelectedListener(this)

        setSupportActionBar(toolbar)

    }




}


