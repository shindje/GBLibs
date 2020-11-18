package com.example.gblibs.mvp.presenter

import com.example.gblibs.R
import com.example.gblibs.mvp.model.Model
import com.example.gblibs.mvp.view.MainView

class Presenter(val model: Model, val view: MainView) {
    fun counterClick(id: Int) {
        when(id){
            R.id.btn_counter1 -> {
                val nextValue = model.next(0)
                view.setButtonText(0, nextValue.toString())
            }
            R.id.btn_counter2 -> {
                val nextValue = model.next(1)
                view.setButtonText(1, nextValue.toString())
            }
            R.id.btn_counter3 -> {
                val nextValue = model.next(2)
                view.setButtonText(2, nextValue.toString())
            }
        }
    }
}