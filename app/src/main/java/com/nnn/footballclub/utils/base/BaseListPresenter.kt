package com.nnn.footballclub.utils.base

import com.nnn.footballclub.utils.provider.CoroutineContextProvider


/**
 * Created by ridhaaaaazis on 29/05/18.
 */
open abstract class BaseListPresenter<M,A> (
        open var coroutineContext : CoroutineContextProvider = CoroutineContextProvider()
): BasePresenter{

    val data : MutableList<M> = mutableListOf()
    abstract var adapter : A

    abstract fun onResume()

    abstract fun loadData()
}