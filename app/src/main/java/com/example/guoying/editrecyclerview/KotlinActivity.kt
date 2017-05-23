package com.example.guoying.editrecyclerview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_kotlin.*

class KotlinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        textView.text="代码中设置文本"
        imageView.setImageResource(R.drawable.ic_camera)
        button.text="代码中设置button"






    }
}
