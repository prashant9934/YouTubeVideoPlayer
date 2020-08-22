<!DOCTYPE html>
<html>
<style type="text/css">
  html, body {
     height:100%;
     width:100%;
     margin: 0;
     padding: 0;
     background:[BG_COLOR];
     overflow:hidden;
     position:relative;
  }
</style>
<script type = "text/javascript" src = "http://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script type = "text/javascript" src = "https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.3/jquery-ui.min.js"></script>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<meta charset="utf-8" />
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width" />
<script src="https://www.youtube.com/iframe_api"></script>
</head>
<body>
  <div id="Player"></div>
</body>
<script type="text/javascript">
  var player;
  function onYouTubeIframeAPIReady() {
      player = new YT.Player('Player', {
      height: '100%',
      width: '100%',
      videoId: '[VIDEO_ID]',
      events: {
        'onReady': onPlayerReady,
        'onStateChange': onPlayerStateChange,
        'onPlaybackQualityChange': onPlayerPlaybackQualityChange,
        'onPlaybackRateChange': onPlayerPlaybackRateChange,
        'onError': onPlayerError,
        'onApiChange': onPlayerApiChange
      },
      playerVars: {
        'autoplay': [AUTO_PLAY],
        'autohide': [AUTO_HIDE],
        'rel': [REL],
        'showinfo': [SHOW_INFO],
        'enablejsapi': [ENABLE_JS_API],
        'disablekb': [DISABLE_KB],
        'cc_lang_pref': '[CC_LANG_PREF]',
        'controls': [CONTROLS],
        'fs': [FS],
        'mute': [MUTE],
        'cc_load_policy': [CC_LOAD_POLICY],
		'origin': 'https://www.youtube.com'
      }
    });
  }

  function onPlayerReady(event) {
    console.log('player is ready');
    onReady('player is ready');
    sendDuration();
    player.setOption("captions", "track", {"languageCode": "es"});
    player.unloadModule("captions");
    player.setVolume([AUDIO_VOLUME]);
  }

  var timerId = 0;
  function onPlayerStateChange(event) {
    clearTimeout(timerId);
    switch (event.data) {
      case YT.PlayerState.UNSTARTED:
        onStateChange("UNSTARTED");
        break;
      case YT.PlayerState.ENDED:
        onStateChange("ENDED");
        break;
      case YT.PlayerState.PLAYING:
        player.unloadModule("captions");
        onStateChange("PLAYING");
        timerId = setInterval(function() {
          setCurrentSeconds();
        }, 100);
        break;
      case YT.PlayerState.PAUSED:
        onStateChange("PAUSED");
        break;
      case YT.PlayerState.BUFFERING:
        onStateChange("BUFFERING");
        break;
      case YT.PlayerState.CUED:
        onStateChange("CUED");
        break;
    }
  }

  function onPlayerPlaybackQualityChange(playbackQuality) {
   console.log('playback quality changed to ' + playbackQuality.data);
   onPlaybackQualityChange('playback quality changed to ' + playbackQuality.data);
  }

  function onPlayerPlaybackRateChange(playbackRate) {
   console.log('playback rate changed to ' + playbackRate.data);
   onPlaybackRateChange('playback rate changed to ' + playbackRate.data);
  }

  function onPlayerError(e) {
   console.log('An error occurred: ' + e.data);
   onError('An error occurred: ' + e.data);
  }

  function onPlayerApiChange() {
   console.log('The player API changed');
   onApiChange('The player API changed');
  }

  function onReady(e){
    window.JSInterface.onReady(e);
  }

  function onStateChange(e){
    window.JSInterface.onStateChange(e);
  }

  function onPlaybackQualityChange(e){
    window.JSInterface.onPlaybackQualityChange(e);
  }

  function onPlaybackRateChange(e){
    window.JSInterface.onPlaybackRateChange(e);
  }

  function onError(e){
    window.JSInterface.onError(e);
  }

  function onApiChange(e){
    window.JSInterface.onApiChange(e);
  }

  function setCurrentSeconds(){
    window.JSInterface.currentSeconds(player.getCurrentTime());
  }

  function sendDuration(){
    window.JSInterface.duration(player.getDuration());
  }

  function setLog(msg){
    window.JSInterface.logs(msg);
  }

  function onSeekTo(startSeconds){
    player.seekTo(startSeconds, true)
  }

  function onVideoPause(){
    player.pauseVideo();
  }

  function onVideoPlay(){
    player.playVideo();
    player.setVolume([AUDIO_VOLUME]);
  }

  function onHideControls(){
    setLog("onHideControls()");
  }

  function loadVideo(videoId, startSeconds){
    setLog(videoId + "_" + startSeconds);
    player.loadVideoById(videoId, startSeconds);
  }

  function cueVideo(videoId){
    setLog(videoId);
    player.cueVideoById(videoId, 0, "[PLAYBACK_QUALITY]");
    player.setVolume([AUDIO_VOLUME]);
  }

  function playNextVideo() {
      player.nextVideo()
  }

  function playPreviousVideo(){
      player.previousVideo()
  }

  function stopVideo(){
      player.stopVideo()
  }

  function playVideoAt(index){
    player.playVideoAt(index)
  }

  function mute(){
    player.mute()
  }

  function unMute(){
    player.unMute()
  }

  function setVolume(volume){
    player.setVolume(volume)
  }

  function setPlayerSize(width, height){
    player.setSize(width, height)
  }

  function setPlayBackRate(suggestedRate) {
    player.setPlaybackRate(suggestedRate)
  }
</script>
</html>