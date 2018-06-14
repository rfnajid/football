package com.nnn.footballclub.utils.base


/**
 * Created by ridhaaaaazis on 30/05/18.
 */


interface BaseDetailContract{

    interface _View<M> : BaseView<_Presenter>{
        fun loadData(data : M )
        fun updateFavoriteLayout(boolean : Boolean)
    }

    interface _Presenter : BasePresenter{
        fun onOptionsItemSelected(id : Int?) : Boolean
        fun favorite()
    }

}
