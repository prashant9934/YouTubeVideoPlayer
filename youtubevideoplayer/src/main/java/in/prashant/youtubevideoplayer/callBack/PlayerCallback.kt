package `in`.prashant.youtubevideoplayer.callBack

import `in`.prashant.youtubevideoplayer.enums.PLAYER_STATE

interface PlayerCallback {
    fun onReady()

    fun onStateChange(state: PLAYER_STATE?)

    fun onPlaybackQualityChange(arg: String?)

    fun onPlaybackRateChange(arg: String?)

    fun onError(arg: String?)

    fun onApiChange(arg: String?)

    fun onCurrentSecond(second: Double)

    fun onDuration(duration: Double)

    fun logs(log: String?)
}