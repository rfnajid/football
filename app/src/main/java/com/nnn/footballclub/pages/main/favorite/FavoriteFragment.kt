package com.nnn.footballclub.pages.main.favorite

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nnn.footballclub.R
import com.nnn.footballclub.pages.main.match.MatchListFragment
import com.nnn.footballclub.pages.main.team.TeamListFragment
import kotlinx.android.synthetic.main.fragment_favorite.view.*


/**
 * Created by ridhaaaaazis on 27/05/18.
 */

class FavoriteFragment : Fragment(),
        TabLayout.OnTabSelectedListener
{

    lateinit var tabLayout: TabLayout

    companion object {
        fun create() : FavoriteFragment{
            val fragment = FavoriteFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_favorite,container,false)

        prepareView(view)

        return view
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
        // not implemented
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        // not implemented
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        updateFragment()
    }


    private fun updateFragment() {
        lateinit var fragment : Fragment
        when(tabLayout.selectedTabPosition){
            0 -> {
                fragment=MatchListFragment.createFavorite()
            }
            1 -> {
                fragment=TeamListFragment.createFavorite()
            }
        }
        val ft = childFragmentManager.beginTransaction()
        ft.replace(R.id.frame, fragment)
        ft.commit()
    }

    private fun prepareView(view : View){

        tabLayout = view.tabLayout

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_match)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_team)))
        tabLayout.addOnTabSelectedListener(this)

        updateFragment()

    }

}