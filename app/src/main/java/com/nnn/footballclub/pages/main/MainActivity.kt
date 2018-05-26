package com.nnn.footballclub.pages.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.nnn.footballclub.R
import com.nnn.footballclub.R.layout.activity_main
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener, MainContract.MainView {

    private lateinit var mainPresenter : MainContract.MainPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(activity_main)
        navigation.setOnNavigationItemSelectedListener(this)

        mainPresenter = MainPresenter(this)
        mainPresenter.start(this)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        mainPresenter.navMenuSelected(item.itemId)
        return true
    }

    override fun setPresenter(presenter: MainContract.MainPresenter) {
        mainPresenter= checkNotNull(presenter)
    }

    override fun selectFragment(fragment : Fragment){

        when((fragment as ListFragment).type){
            ListPresenter.TYPE.PAST -> title=getString(R.string.page_past)
            ListPresenter.TYPE.NEXT -> title=getString(R.string.page_next)
            ListPresenter.TYPE.FAVORITE -> title=getString(R.string.page_favorite)
        }


        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frame, fragment)
        ft.commit()
    }


}


