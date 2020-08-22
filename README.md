![Release](https://jitpack.io/v/prashant9934/YouTubeVideoPlayer.svg) (https://jitpack.io/#prashant9934/YouTubeVideoPlayer/1.0)

# YouTubeVideoPlayer
this player is using Iframe YouTube api Internally to play YouTube video

To add library in your project add 

#1. add this file inside gradle

    allprojects {
        repositories {
		    maven { url 'https://jitpack.io' }
        }
    }

#2. add this inside gradle dependencies

    dependencies {
	    implementation 'com.github.prashant9934:YouTubeVideoPlayer:1.0'
    }
	
	
implementation:-

activity_main.xml

    <androidx.constraintlayout.widget.ConstraintLayout 
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".MainActivity">

        <in.prashant.youtubevideoplayer.YouTubeVideoPlayer
            android:id="@+id/video_player"
            android:layout_width="match_parent"
            android:layout_height="0dp"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintBottom_toBottomOf="parent"/>
    </androidx.constraintlayout.widget.ConstraintLayout>

MainActivity.kt

    class MainActivity : AppCompatActivity() {
        private lateinit var binding:ActivityMainBinding

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        }

        override fun onResume() {
            super.onResume()
            val videoParams = PlayerParams()
            binding.videoPlayer.setAutoPlayerHeight()
            videoParams.setControls(1)
            videoParams.setAutoplay(0)
            videoParams.setMute(0)
            videoParams.setVolume(90)
            videoParams.setRel(1)
            videoParams.setDisablekb(0)
            videoParams.setEnablejsapi(1)
            videoParams.setFS(0)
            binding.videoPlayer.initializeWithUrl(
                "https://www.youtube.com/watch?v=CQK7SHNcHqA",
                videoParams,
                getCallBack()
            )
    
            binding.videoPlayer.isMuted {
                Log.d("ismuted", it.toString())
            }
        }

        private fun getCallBack(): PlayerCallback {
            return object : PlayerCallback {
                override fun onReady() {
                    Handler(Looper.getMainLooper()).post {
                        binding.videoPlayer.play()
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

For more details please refer (https://developers.google.com/youtube/iframe_api_reference)

For parameter please refer (https://developers.google.com/youtube/player_parameters)