package com.suiyi.main.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.opensource.svgaplayer.SVGADrawable
import com.opensource.svgaplayer.SVGAParser
import com.opensource.svgaplayer.SVGAVideoEntity
import com.suiyi.main.R
import com.example.base.constants.Path
import kotlinx.android.synthetic.main.activity_svga_player.*

@Route(path = Path.SVGA_PLAYER_ACTIVITY)
class SVGAPlayerActivity : Activity(){

    private var psdSet : SVGADrawable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_svga_player)

        val parser = SVGAParser(this)

        parser.decodeFromAssets("svga/max.svga", object : SVGAParser.ParseCompletion{
            override fun onComplete(videoItem: SVGAVideoEntity) {
                psdSet = SVGADrawable(videoItem)
            }

            override fun onError() {

            }

        })

        svga_player.setImageDrawable(psdSet)

        svga_player.setOnClickListener{
            svga_player.pauseAnimation()
            svga_player.startAnimation()
        }
    }

    fun pause(v : View){
        svga_player.pauseAnimation()
    }

    fun play(v : View){
        svga_player.drawable?.let {
            if (it is SVGADrawable){
                svga_player.stepToFrame(it.currentFrame, true)
            }
        }
    }
}