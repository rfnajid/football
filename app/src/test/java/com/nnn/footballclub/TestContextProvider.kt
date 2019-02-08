package com.nnn.footballclub

import com.nnn.footballclub.utils.provider.CoroutineContextProvider
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext


/**
 * Created by ridhaaaaazis on 24/05/18.
 */
open class TestContextProvider : CoroutineContextProvider() {
    override val main: CoroutineContext = Dispatchers.Unconfined

}