package `in`.prashant.youtubevideoplayer

import `in`.prashant.youtubevideoplayer.bridge.JavaScriptBridge
import `in`.prashant.youtubevideoplayer.callBack.PlayerCallback
import `in`.prashant.youtubevideoplayer.enums.VIDEO_QUALITY
import `in`.prashant.youtubevideoplayer.model.PlayerParams
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Created by Prashant kumar
 */
class YouTubeVideoPlayer : WebView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    private var params: PlayerParams = PlayerParams()
    private var youTubeListener: PlayerCallback? = null
    private var bridge: JavaScriptBridge? = null
    private var webClient: WebViewClient? = null

    private fun getMpWebViewClient(): WebViewClient {
        if (webClient != null) {
            return webClient!!
        } else {
            return object : WebViewClient() {
                override fun shouldOverrideKeyEvent(view: WebView?, event: KeyEvent?): Boolean {
                    Log.d("events", event.toString())
                    return super.shouldOverrideKeyEvent(view, event)
                }

                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    context?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                    return true
                }
            }
        }
    }

    /**
     * set web view client to get call back when any link is getting clicked
     */
    fun setPlayerWebViewClient(webViewClient: WebViewClient) {
        this.webClient = webViewClient
    }

    override fun destroy() {
        super.destroy()
        super.onDetachedFromWindow()
        clearCache(true)
        clearHistory()
    }

    @SuppressLint("JavascriptInterface")
    private fun initialize(videoId: String, youTubeListener: PlayerCallback?) {
        val setting = this.settings
        setting.javaScriptEnabled = true
        setting.useWideViewPort = true
        setting.loadWithOverviewMode = true
        setting.layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
        setting.cacheMode = WebSettings.LOAD_NO_CACHE
        setting.pluginState = WebSettings.PluginState.ON
        setting.pluginState = WebSettings.PluginState.ON_DEMAND
        setting.allowContentAccess = true
        setting.allowFileAccess = true

        this.youTubeListener = youTubeListener
        bridge = JavaScriptBridge(this.youTubeListener)
        setLayerType(View.LAYER_TYPE_NONE, null)
        measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)
        addJavascriptInterface(bridge, "JSInterface")
        this.isLongClickable = true
        this.webChromeClient = WebChromeClient()
        this.webViewClient = getMpWebViewClient()
        setOnLongClickListener { true }
        setWebContentsDebuggingEnabled(true)
        loadDataWithBaseURL("https://www.youtube.com", getVideoHTML(videoId), "text/html", "utf-8", null)
    }

    fun initializeWithUrl(
        videoUrl: String,
        params: PlayerParams?,
        youTubeListener: PlayerCallback?
    ) {
        if (params != null) {
            this.params = params
        }
        val videoId = videoUrl.substring(videoUrl.indexOf('=') + 1)
        initialize(videoId, youTubeListener)
    }

    private fun setAutoPlayerHeight(context: Context) {
        val displayMetrics = DisplayMetrics()
        if (context is Activity) {
            context.windowManager.defaultDisplay.getMetrics(displayMetrics)
            this.layoutParams.height = (displayMetrics.widthPixels * 0.6).toInt()
        } else {
            this.layoutParams.height =
                (context.resources.displayMetrics.widthPixels * 0.6).toInt()
        }
    }

    /**
     * set player height in pixels
     */
    fun setPlayerHeight(height: Int) {
        this.layoutParams.height = height
    }

    fun setAutoPlayerHeight() {
        setAutoPlayerHeight(context)
    }

    fun seekToMillis(mil: Double) {
        this.loadUrl("javascript:onSeekTo($mil)")
    }

    fun pause() {
        this.loadUrl("javascript:onVideoPause()")
    }

    fun play() {
        this.loadUrl("javascript:onVideoPlay()")
    }

    fun onLoadVideo(videoId: String, mil: Float) {
        this.loadUrl("javascript:loadVideo('$videoId', $mil)")
    }

    fun onCueVideo(videoId: String) {
        this.loadUrl("javascript:cueVideo('$videoId')")
    }

    fun playNextVideo() {
        this.loadUrl("javascript:playNextVideo()")
    }

    fun playPreviousVideo() {
        this.loadUrl("javascript:playPreviousVideo()")
    }

    fun stopVideo() {
        this.loadUrl("javascript:stopVideo()")
    }

    fun playVideoAt(index: Int) {
        this.loadUrl("javascript:playVideoAt($index)")
    }

    fun mute() {
        this.loadUrl("javascript:mute()")
    }

    fun unMute() {
        this.loadUrl("javascript:unMute()")
    }

    fun setVolume(volume: Int) {
        this.loadUrl("javascript:setVolume($volume)")
    }

    fun playFullscreen() {
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        this.layoutParams.height = (displayMetrics.widthPixels * 0.6).toInt()
        this.loadUrl("javascript:playFullscreen(" + displayMetrics.widthPixels + ", " + displayMetrics.heightPixels + ")")
    }


    private fun getVideoHTML(videoId: String): String? {
        try {
            val inputStream = resources.openRawResource(R.raw.youtubevideoplayer)
            val stream = InputStreamReader(inputStream, "utf-8")
            val buffer = BufferedReader(stream)
            var read: String?
            val sb = StringBuilder("")
            while (buffer.readLine().also { read = it } != null) {
                if (read != null) {
                    sb.append(read!!.trimIndent())
                }
            }
            inputStream.close()
            var html = sb.toString().replace("[VIDEO_ID]", videoId).replace("[BG_COLOR]", params.getBackgroundColor())
            val playbackQuality: VIDEO_QUALITY? = params.getPlaybackQuality()
            html = html.replace("[AUTO_PLAY]", params.getAutoplay().toString())
                .replace("[AUTO_HIDE]", params.getAutohide().toString())
                .replace("[REL]", params.getRel().toString())
                .replace("[SHOW_INFO]", params.getShowinfo().toString())
                .replace("[ENABLE_JS_API]", params.getEnablejsapi().toString())
                .replace("[DISABLE_KB]", params.getDisablekb().toString())
                .replace("[CC_LANG_PREF]", params.getCc_lang_pref().toString())
                .replace("[CONTROLS]", params.getControls().toString())
                .replace("[AUDIO_VOLUME]", params.getVolume().toString())
                .replace("[PLAYBACK_QUALITY]", playbackQuality?.name ?: VIDEO_QUALITY.small.name)
                .replace("[FS]", params.getFS())
                .replace("[MUTE]", params.getMute())
                .replace("[CC_LOAD_POLICY]", params.getCcLoadPolicy())
            return html
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}