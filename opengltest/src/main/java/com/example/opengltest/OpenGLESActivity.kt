package com.example.opengltest

import android.app.Activity
import android.opengl.GLSurfaceView
import android.os.Bundle

class OpenGLESActivity : Activity() {
    private lateinit var gLView : GLSurfaceView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gLView = MyGLSurfaceView(this)
        setContentView(gLView)


    }
}