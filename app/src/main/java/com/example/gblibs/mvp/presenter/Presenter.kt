package com.example.gblibs.mvp.presenter

import com.example.gblibs.mvp.model.Model
import com.example.gblibs.mvp.view.MainView

class Presenter(val model: Model, val view: MainView) {
    fun counterClick1() {
        val nextValue = model.next(0)
        view.setButton1Text(nextValue.toString())
    }
    fun counterClick2() {
        val nextValue = model.next(1)
        view.setButton2Text(nextValue.toString())
    }
    fun counterClick3() {
        val nextValue = model.next(2)
        view.setButton3Text(nextValue.toString())
    }
}