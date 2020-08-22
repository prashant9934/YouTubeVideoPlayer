package `in`.prashant.youtubevideoplayer.model

import `in`.prashant.youtubevideoplayer.enums.VIDEO_QUALITY

data class PlayerParams(
    private var autoplay: Int = 0,
    private var autohide: Int = 1,
    private var rel: Int = 0,
    private var showinfo: Int = 0,
    private var enablejsapi: Int = 1,
    private var disablekb: Int = 1,
    private var cc_lang_pref: String = "en",
    private var controls: Int = 1,
    private var volume: Int = 100,
    private var fs: Int = 1,
    private var mute: Int = 0,
    private var cc_load_policy: Int = 1,
    private var playbackQuality: VIDEO_QUALITY? = null,
    private var colorCode:String = "#01131B"
) {

    fun setVolume(volume: Int): PlayerParams {
        this.volume = volume
        return this
    }

    fun setPlaybackQuality(playbackQuality: VIDEO_QUALITY?): PlayerParams {
        this.playbackQuality = playbackQuality
        return this
    }

    fun getAutoplay(): Int {
        return autoplay
    }

    fun setAutoplay(autoplay: Int) {
        this.autoplay = autoplay
    }

    fun getAutohide(): Int {
        return autohide
    }

    fun setAutohide(autohide: Int) {
        this.autohide = autohide
    }

    fun getRel(): Int {
        return rel
    }

    fun setRel(rel: Int) {
        this.rel = rel
    }

    fun getShowinfo(): Int {
        return showinfo
    }

    fun setShowinfo(showinfo: Int) {
        this.showinfo = showinfo
    }

    fun getEnablejsapi(): Int {
        return enablejsapi
    }

    fun setEnablejsapi(enablejsapi: Int) {
        this.enablejsapi = enablejsapi
    }

    fun getDisablekb(): Int {
        return disablekb
    }

    fun setDisablekb(disablekb: Int) {
        this.disablekb = disablekb
    }

    fun getCc_lang_pref(): String? {
        return cc_lang_pref
    }

    fun setCc_lang_pref(cc_lang_pref: String?) {
        this.cc_lang_pref = cc_lang_pref!!
    }

    fun getControls(): Int {
        return controls
    }

    fun setControls(controls: Int) {
        this.controls = controls
    }

    fun getVolume(): Int {
        return volume
    }

    fun getPlaybackQuality(): VIDEO_QUALITY? {
        return playbackQuality
    }

    fun getFS(): String {
        return fs.toString()
    }

    fun setFS(fs: Int) {
        this.fs = fs
    }

    fun setMute(mute: Int) {
        this.mute = mute
    }

    fun getMute(): String {
        return this.mute.toString()
    }

    fun getCcLoadPolicy(): String {
        return this.cc_load_policy.toString()
    }

    fun setCcLoadPolicy(cc_load_policy: Int) {
        this.cc_load_policy = cc_load_policy
    }

    fun getBackgroundColor(): String {
        return colorCode
    }

    /**
     * set color code example "#01131B"
     */
    fun setBackgroundColor(colorCode:String) {
        this.colorCode = colorCode
    }

    override fun toString(): String {
        return "?autoplay=" + autoplay +
                "&autohide=" + autohide +
                "&rel=" + rel +
                "&showinfo=" + showinfo +
                "&enablejsapi=" + enablejsapi +
                "&disablekb=" + disablekb +
                "&cc_lang_pref=" + cc_lang_pref +
                "&controls=" + controls +
                "&volume=" + volume +
                "&playbackQuality=" + playbackQuality!!.name
    }
}