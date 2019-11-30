package com.silambar.fragmentcommunicationwithrxjava

import io.reactivex.subjects.PublishSubject

class RxBus {

    val personASubject = PublishSubject.create<String>()
    val personBSubject = PublishSubject.create<String>()

    companion object{
        private var rxInstance:RxBus?=null
        val instance:RxBus?
        get() {
            if (rxInstance == null)
                rxInstance = RxBus()
            return rxInstance
        }
    }
}