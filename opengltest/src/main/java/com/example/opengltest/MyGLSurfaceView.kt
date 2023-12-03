package com.example.opengltest

import android.content.Context
import android.graphics.BitmapFactory
import android.opengl.GLSurfaceView

class MyGLSurfaceView (context: Context) : GLSurfaceView(context){
    private val renderer : MyGLRenderer
    private val renderer1 : MyGLRenderer

    init {
        setEGLContextClientVersion(2)

        renderer = MyGLRenderer()
        renderer1 = MyGLRenderer()
//        renderMode = RENDERMODE_WHEN_DIRTY
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.img_1)
        val bitmap2 = BitmapFactory.decodeResource(context.resources,R.drawable.img)
        renderer.setImageBitmap(bitmap)
        renderer1.setImageBitmap(bitmap2)
        setRenderer(renderer)
    }
}