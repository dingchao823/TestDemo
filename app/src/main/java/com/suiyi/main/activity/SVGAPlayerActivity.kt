package com.suiyi.main.activity

import android.app.Activity
import android.os.Bundle
import android.os.Trace
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.opensource.svgaplayer.SVGADrawable
import com.opensource.svgaplayer.SVGAParser
import com.opensource.svgaplayer.SVGAVideoEntity
import com.suiyi.main.R
import com.suiyi.main.constants.Path
import kotlinx.android.synthetic.main.activity_svga_player.*

@Route(path = Path.SVGA_PLAYER_ACTIVITY)
class SVGAPlayerActivity : Activity(){

    private var kingSet : SVGADrawable? = null

    private var psdSet : SVGADrawable? = null

    private var isKingSet = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_svga_player)

        val parser = SVGAParser(this)

        Trace.beginSection("加载 kingSet.svga")
        parser.decodeFromAssets("kingset.svga", object : SVGAParser.ParseCompletion{
            override fun onComplete(videoItem: SVGAVideoEntity) {
                kingSet = SVGADrawable(videoItem)
            }

            override fun onError() {
            }
        })
        Trace.endSection()

        Trace.beginSection("加载 psd.svga")
        parser.decodeFromAssets("psd.svga", object : SVGAParser.ParseCompletion{
            override fun onComplete(videoItem: SVGAVideoEntity) {
                psdSet = SVGADrawable(videoItem)
            }

            override fun onError() {
            }

        })
        Trace.endSection()

        svga_player.setImageDrawable(kingSet)
        isKingSet = true

        svga_player.setOnClickListener{
            svga_player.pauseAnimation()
            if (isKingSet) {
                svga_player.setImageDrawable(psdSet)
                isKingSet = false
            }else{
                svga_player.setImageDrawable(kingSet)
                isKingSet = true
            }
            svga_player.startAnimation()
        }
    }

    fun pause(v : View){
        svga_player.pauseAnimation()
    }

    fun play(v : View){
        svga_player.drawable?.let {
            if (it is SVGADrawable){
                it.currentFrame?.let { currentFrame ->
                    svga_player.stepToFrame(currentFrame, true)
                }
            }
        }
    }
}