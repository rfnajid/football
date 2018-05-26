package com.nnn.footballclub.utils.provider

import kotlinx.coroutines.experimental.android.UI
import kotlin.coroutines.experimental.CoroutineContext


/**
 * Created by ridhaaaaazis on 24/05/18.
 */

open class CoroutineContextProvider {
    open val main: CoroutineContext by lazy { UI }
}