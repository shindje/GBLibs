package com.example.gblibs.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.gblibs.R
import com.example.gblibs.mvp.model.Model
import com.example.gblibs.mvp.presenter.Presenter
import com.example.gblibs.mvp.view.MainView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView {
    val presenter = Presenter(Model(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_counter1.setOnClickListener {presenter.counterClick1()}
        btn_counter2.setOnClickListener {presenter.counterClick2()}
        btn_counter3.setOnClickListener {presenter.counterClick3()}
    }

    private fun setButtonText(i: Int, text: String) {
        when(i){
            0 -> btn_counter1.text = text
            1 -> btn_counter2.text = text
            2 -> btn_counter3.text = text
        }
    }

    override fun setButton1Text(text: String) {
        setButtonText(0, text)
    }

    override fun setButton2Text(text: String) {
        setButtonText(1, text)
    }

    override fun setButton3Text(text: String) {
        setButtonText(2, text)
    }
}