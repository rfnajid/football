package com.nnn.footballclub.pages.detail

import com.nnn.footballclub.model.Event
import com.nnn.footballclub.utils.base.BasePresenter
import com.nnn.footballclub.utils.base.BaseView


/**
 * Created by ridhaaaaazis on 18/05/18.
 */

interface DetailContract{

    interface View : BaseView<Presenter>{
        fun loadData(event: Event)
        fun updateFavoriteLayout(boolean : Boolean)
    }

    interface Presenter : BasePresenter{
        fun onOptionsItemSelected(id : Int?) : Boolean
        fun favorite()
    }

}
