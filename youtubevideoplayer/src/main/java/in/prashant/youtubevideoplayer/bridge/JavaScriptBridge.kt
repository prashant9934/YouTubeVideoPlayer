package `in`.prashant.youtubevideoplayer.bridge

import `in`.prashant.youtubevideoplayer.callBack.PlayerCallback
import `in`.prashant.youtubevideoplayer.enums.PLAYER_STATE
import android.webkit.JavascriptInterface

class JavaScriptBridge(private val callBack: PlayerCallback?) {

    @JavascriptInterface
    fun onReady(arg: String) {
        callBack?.onReady()
    }

    @JavascriptInterface
    fun onStateChange(arg: String) {
        if (callBack != null) {
            when {
                "UNSTARTED".equals(arg, ignoreCase = true) -> {
                    callBack.onStateChange(PLAYER_STATE.UNSTARTED)
                }
                "ENDED".equals(arg, ignoreCase = true) -> {
                    callBack.onStateChange(PLAYER_STATE.ENDED)
                }
                "PLAYING".equals(arg, ignoreCase = true) -> {
                    callBack.onStateChange(PLAYER_STATE.PLAYING)
                }
                "PAUSED".equals(arg, ignoreCase = true) -> {
                    callBack.onStateChange(PLAYER_STATE.PAUSED)
                }
                "BUFFERING".equals(arg, ignoreCase = true) -> {
                    callBack.onStateChange(PLAYER_STATE.BUFFERING)
                }
                "CUED".equals(arg, ignoreCase = true) -> {
                    callBack.onStateChange(PLAYER_STATE.CUED)
                }
            }
        }
    }

    @JavascriptInterface
    fun onPlaybackQualityChange(arg: String) {
        callBack?.onPlaybackQualityChange(arg)
    }

    @JavascriptInterface
    fun onPlaybackRateChange(arg: String) {
        callBack?.onPlaybackRateChange(arg)
    }

    @JavascriptInterface
    fun onError(arg: String) {
        callBack?.onError(arg)
    }

    @JavascriptInterface
    fun onApiChange(arg: String) {
        callBack?.onApiChange(arg)
    }

    @JavascriptInterface
    fun currentSeconds(seconds: String) {
        callBack?.onCurrentSecond(seconds.toDouble())
    }

    @JavascriptInterface
    fun duration(seconds: String) {
        callBack?.onDuration(seconds.toDouble())
    }

    @JavascriptInterface
    fun logs(arg: String) {
        callBack?.logs(arg)
    }
}