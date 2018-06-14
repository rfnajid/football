package com.nnn.footballclub.utils


/**
 * Created by ridhaaaaazis on 13/06/18.
 */

class TestUtil {
    companion object {

        fun sleep(seconds : Int){
            Thread.sleep(seconds.toLong()*1000)
        }

        fun sleepFlash(){
            sleep(1)
        }

        fun sleepFast(){
            sleep(3)
        }

        fun sleepQuiteFast(){
            sleep(5)
        }

        fun sleepMedium(){
            sleep(10)
        }

        fun sleepNotLongEnough(){
            sleep(15)
        }

        fun sleepQuiteLong(){
            sleep(20)
        }

        fun sleepLong(){
            sleep(30)
        }
    }
}