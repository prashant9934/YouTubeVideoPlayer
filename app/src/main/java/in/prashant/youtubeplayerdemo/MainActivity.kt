package `in`.prashant.youtubeplayerdemo

import `in`.prashant.youtubevideoplayer.YouTubeVideoPlayer
import `in`.prashant.youtubevideoplayer.callBack.PlayerCallback
import `in`.prashant.youtubevideoplayer.enums.PLAYER_STATE
import `in`.prashant.youtubevideoplayer.model.PlayerParams
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var player: YouTubeVideoPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        player = findViewById(R.id.video_player)
    }

    override fun onResume() {
        super.onResume()
        val videoParams = PlayerParams()
        player.setAutoPlayerHeight()
        videoParams.setControls(1)
        videoParams.setAutoplay(0)
        videoParams.setMute(0)
        videoParams.setVolume(90)
        videoParams.setRel(1)
        videoParams.setDisablekb(0)
        videoParams.setEnablejsapi(1)
        videoParams.setFS(0)
        player.initializeWithUrl(
            "https://www.youtube.com/watch?v=CQK7SHNcHqA",
            videoParams,
            getCallBack()
        )

        player.isMuted {
            Log.d("ismuted", it.toString())
        }
    }

    private fun getCallBack(): PlayerCallback {
        return object : PlayerCallback {
            override fun onReady() {
                Handler(Looper.getMainLooper()).post {
                    player.play()
                    findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
                }
            }

            override fun onStateChange(state: PLAYER_STATE?) {
                Toast.makeText(applicationContext, "State changed", Toast.LENGTH_SHORT).show()
            }

            override fun onPlaybackQualityChange(arg: String?) {
                Toast.makeText(applicationContext, arg.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onPlaybackRateChange(arg: String?) {
                Toast.makeText(applicationContext, arg.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onError(arg: String?) {
                Log.d("onError", arg.toString())
            }

            override fun onApiChange(arg: String?) {
                Toast.makeText(applicationContext, "api changed", Toast.LENGTH_SHORT).show()
            }

            override fun onCurrentSecond(second: Double) {
            }

            override fun onDuration(duration: Double) {
            }

            override fun logs(log: String?) {
                Toast.makeText(applicationContext, log, Toast.LENGTH_SHORT).show()
            }

        }
    }
}