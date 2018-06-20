package com.nnn.footballclub.pages.main.match

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import com.nnn.footballclub.R
import com.nnn.footballclub.utils.Global
import kotlinx.android.synthetic.main.fragment_match.view.*


/**
 * Created by ridhaaaaazis on 27/05/18.
 */

class MatchFragment : Fragment(),
        TabLayout.OnTabSelectedListener,
        AdapterView.OnItemSelectedListener
{

    lateinit var tabLayout: TabLayout
    lateinit var spinner : Spinner

    companion object {
        fun create() : MatchFragment{
            val fragment = MatchFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_match,container,false)

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

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        updateFragment()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // not implemented
    }

    private fun updateFragment() {
        val ft = childFragmentManager.beginTransaction()
        ft.replace(R.id.frame, MatchListFragment.create(getType(),getLeagueId()))
        ft.commit()

        Global.log("Update Fragment Match List : ${getType()}, league : ${getLeagueId()}")
    }

    private fun prepareView(view : View){

        tabLayout = view.tabLayout
        spinner = view.spinner

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.page_past)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.page_next)))
        tabLayout.addOnTabSelectedListener(this)

        spinner.onItemSelectedListener=this

        updateFragment()

    }

    private fun getType() : MatchListPresenter.TYPE{
        return getType(tabLayout.selectedTabPosition)
    }

    private fun getType(tabPosition: Int) : MatchListPresenter.TYPE{
        lateinit var type : MatchListPresenter.TYPE
        when(tabPosition) {
            0 -> type = MatchListPresenter.TYPE.PAST
            1 -> type = MatchListPresenter.TYPE.NEXT
        }
        return type
    }

    private fun getLeagueId() : Int{
        return Global.getLeagueId(resources,spinner.selectedItemPosition)
    }


}