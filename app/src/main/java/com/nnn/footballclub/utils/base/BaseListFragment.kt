package com.nnn.footballclub.utils.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.nnn.footballclub.R
import kotlinx.android.synthetic.main.list_view.*
import kotlinx.android.synthetic.main.list_view.view.*


/**
 * Created by ridhaaaaazis on 29/05/18.
 */

open class BaseListFragment<M,A> : Fragment(),BaseView<BaseListPresenter<M,A>> {

    internal lateinit var listPresenter: BaseListPresenter<M,A>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.list_view,container,false)
        loading(view.textView,true)

        listPresenter.start(context as Context)

        view.recycler.adapter = listPresenter.adapter as RecyclerView.Adapter<*>
        view.recycler.layoutManager=LinearLayoutManager(context)

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

    override fun setPresenter(presenter: BaseListPresenter<M, A>) {
        listPresenter=presenter
    }


    fun empty(textView : TextView, show : Boolean){
        loading(textView,false)
        textView.text="There is No Data"
        textView.visibility= View.VISIBLE
        if(!show)
            textView.visibility=View.GONE
    }

    fun loading(textView: TextView, show : Boolean){
        textView.text = "Loading..."
        textView.visibility=View.VISIBLE
        if(!show)
            textView.visibility=View.GONE
    }

    fun empty(show: Boolean=true){
        empty(textView,show)
    }

    fun loading(show:Boolean=true){
        loading(textView,show)
    }

}