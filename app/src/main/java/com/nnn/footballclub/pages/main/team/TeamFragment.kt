package com.nnn.footballclub.pages.main.team

import android.os.Bundle
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
 * Created by ridhaaaaazis on 28/05/18.
 */

class TeamFragment : Fragment(),
        AdapterView.OnItemSelectedListener
{
    companion object {
        fun create() : TeamFragment {
            val fragment = TeamFragment()
            return fragment
        }
    }

    lateinit var spinner : Spinner

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_team,container,false)

        prepareView(view)

        return view
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // not implemented
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        updateFragment()
    }

    fun updateFragment() {
        val ft = childFragmentManager.beginTransaction()
        ft.replace(R.id.frame, TeamListFragment.create(getLeagueId()))
        ft.commit()
    }

    private fun prepareView(view : View){
        spinner = view.spinner
        spinner.onItemSelectedListener=this

        updateFragment()
    }

    private fun getLeagueId() : Int{
        return Global.getLeagueId(resources,spinner.selectedItemPosition)
    }
}