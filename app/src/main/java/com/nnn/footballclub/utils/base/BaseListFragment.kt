package com.nnn.footballclub.utils.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nnn.footballclub.R
import kotlinx.android.synthetic.main.list_view.*
import kotlinx.android.synthetic.main.list_view.view.*


/**
 * Created by ridhaaaaazis on 29/05/18.
 */

open abstract class BaseListFragment<M,A> : Fragment(),BaseView<BaseListPresenter> {

    internal lateinit var listPresenter: BaseListPresenter

    val data : MutableList<M> = mutableListOf()
    abstract var adapter : A

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.list_view,container,false)

        listPresenter.start(context as Context)

        view.recycler.adapter = this.adapter as RecyclerView.Adapter<*>
        view.recycler.layoutManager= LinearLayoutManager(context)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loading()
        listPresenter.loadData()

    }

    override fun onResume() {
        super.onResume()
        listPresenter.onResume()
    }

    override fun setPresenter(presenter: BaseListPresenter) {
        listPresenter=presenter
    }


    fun empty(show : Boolean=true){
        loading(false)
        textView.text="There is No Data"
        textView.visibility= View.VISIBLE
        if(!show)
            textView.visibility=View.GONE
    }

    fun loading(show : Boolean=true){
        textView?.text = "Loading..."
        textView?.visibility=View.VISIBLE
        if(!show)
            textView?.visibility=View.GONE
    }


}