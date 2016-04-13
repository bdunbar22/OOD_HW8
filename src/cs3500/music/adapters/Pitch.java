package cs3500.music.adapters;

/**
 * Enum to represent letter pitches.
 */
public enum Pitch {
  C(0, "C"),
  CSHARP(1, "C#"),
  D(2, "D"),
  DSHARP(3, "D#"),
  E(4, "E"),
  F(5, "F"),
  FSHARP(6, "F#"),
  G(7, "G"),
  GSHARP(8, "G#"),
  A(9, "A"),
  ASHARP(10, "A#"),
  B(11, "B");

  //TODO: Make sure our pitches will work, change if needed on our side.
  //TODO: Remove this file.
  private int numValue; // numerical value of this Pitch, 0 indexed
  private String name; // String name of this pitch

  /**
   * Pitch constructor, sets numeric value and name
   *
   * @param numericValue the numeric value of this Pitch
   * @param name         the name of this pitch
   */
  Pitch(int numericValue, String name) {
    this.numValue = numericValue;
    this.name = name;
  }

  /**
   * Return the corresponding pitch to the specified numeric value.
   *
   * @param numericValue the specified numeric value
   * @return the corresponding Pitch
   * @throws IllegalArgumentException if invalid numeric value is entered
   */
  public static Pitch getPitch(int numericValue) {
    for (Pitch p : Pitch.values()) {
      if (p.numValue == numericValue) {
        return p;
      }
    }
    throw new IllegalArgumentException("Pitch of value " + numericValue + " does not exist");
  }

  /**
   * Get the numeric value of this Pitch
   *
   * @return the numeric value of this pitch
   */
  public int getNumValue() {
    return numValue;
  }

  /**
   * Get the name of this Pitch
   *
   * @return the name of this Pitch
   */
  @Override
  public String toString() {
    return name;
  }
}
