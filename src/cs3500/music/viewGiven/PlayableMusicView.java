package cs3500.music.viewGiven;

/**
 * Interface to represent functionality of models that play music
 */
public interface PlayableMusicView extends MusicView {

  /**
   * Set the beat to begin playing from.
   *
   * @param beat a beat in the piece
   */
  void setBeat(double beat);

  /**
   * Begin playing music from the current beat.
   */
  void play();

  /**
   * Pause playback of music.
   */
  void pause();

  /**
   * Determine whether or not the current piece of music is currently playing
   *
   * @return whether or not the current piece of music is currently playing
   */
  boolean isPlaying();

  /**
   * Determine the current position of playback in beats
   *
   * @return current position of playback in beats
   */
  double currentBeat();

}
