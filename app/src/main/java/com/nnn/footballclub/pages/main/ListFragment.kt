package com.nnn.footballclub.pages.main
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.nnn.footballclub.R
import com.nnn.footballclub.pages.main.ListPresenter.TYPE
import com.nnn.footballclub.utils.Global
import kotlinx.android.synthetic.main.list_view.*
import kotlinx.android.synthetic.main.list_view.view.*
import org.jetbrains.anko.support.v4.dip


/**
 * Created by ridhaaaaazis on 30/04/18.
 */

class ListFragment : Fragment (),MainContract.ListView{

    lateinit var listPresenter : MainContract.ListPresenter

    override var type: TYPE = TYPE.PAST

    companion object {
        fun create(type : TYPE) : ListFragment{
            val fragment = ListFragment()
            fragment.type=type
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //listPresenter = ListPresenterRetro(this)
        listPresenter = ListPresenterAnko(this)
        listPresenter.start()

        val view = inflater.inflate(R.layout.list_view,container,false)
        view.recycler.adapter = (listPresenter as ListPresenter).adapter
        view.recycler.layoutManager= LinearLayoutManager(context)

        val params : FrameLayout.LayoutParams = view.layoutParams as FrameLayout.LayoutParams
        params.bottomMargin = dip(48)
        view.layoutParams=params

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        listPresenter.loadData()
    }

    override fun onResume() {
        super.onResume()

        listPresenter.onResume()
    }

    override fun empty(bool : Boolean){
        Global.log("fun empty on VIEW : ${bool}")

        try{
        if(bool){
            textEmpty.visibility=View.VISIBLE
        }else{
            textEmpty.visibility=View.GONE
        }}catch (e : Exception){
            Global.log("Empty exception : ${e.message}")
        }
    }

    override fun setPresenter(presenter: MainContract.ListPresenter) {
        listPresenter = presenter
    }
}