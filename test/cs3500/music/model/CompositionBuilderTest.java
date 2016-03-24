package cs3500.music.model;

import cs3500.music.util.CompositionBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Sam Letcher on 3/23/2016.
 */
public class CompositionBuilderTest {
  @Test
  public void compositionBuilderAddNote () {
    CompositionBuilder testBuilder = new CompositionBuilder();
    testBuilder.addNote(1, 2, 1, 64, 1);
    IPiece testPiece = testBuilder.build();
    assertEquals("[  E4 ]", testPiece.getNotes().toString());
    testBuilder.addNote(1, 2, 1, 60, 1);
    testPiece = testBuilder.build();
    assertEquals("[  E4 ,   C4 ]", testPiece.getNotes().toString());
  }
}
