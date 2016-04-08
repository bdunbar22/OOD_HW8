package cs3500.music.view;

import cs3500.music.model.*;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test the ViewPiece functionality. The view piece implements the IViewPiece interface.
 *
 * Created by Ben on 4/4/16.
 */
public class ViewPieceTest {
    /**
     * Test get notes from view piece
     */
    @Test
    public void testGetNotes() {
        IViewPiece testList = testViewPieceHelper2();
        List<INote> notes = testList.getNotes();
        assertEquals(4, notes.size());
        assertTrue(notes.contains(new Note(Pitch.DSHARP, new Octave(3), 0, 1)));
        assertTrue(notes.contains(new Note(Pitch.CSHARP, new Octave(4), 1, 2)));
        assertTrue(notes.contains(new Note(Pitch.FSHARP, new Octave(6), 2, 1)));
        assertTrue(notes.contains(new Note(Pitch.ASHARP, new Octave(3), 3, 2)));
    }

    /**
     * Test get notes is not by reference for view piece
     */
    @Test
    public void testGetNotesNotByRef() {
        IViewPiece testList = testViewPieceHelper2();
        List<INote> changedNotes = testList.getNotes();
        changedNotes.remove(2);
        changedNotes.remove(1);
        INote note = changedNotes.get(0);
        note.setDuration(9999);
        //Edited the notes that were returned.
        //Verify the testList notes were not changed. By getting them again and testing.
        List<INote> notes = testList.getNotes();
        assertEquals(4, notes.size());
        assertTrue(notes.contains(new Note(Pitch.DSHARP, new Octave(3), 0, 1)));
        assertTrue(notes.contains(new Note(Pitch.CSHARP, new Octave(4), 1, 2)));
        assertTrue(notes.contains(new Note(Pitch.FSHARP, new Octave(6), 2, 1)));
        assertTrue(notes.contains(new Note(Pitch.ASHARP, new Octave(3), 3, 2)));
    }

    /**
     * Test get notes when the view piece was created from an empty model.
     */
    @Test public void testGetNotesEmpty() {
        IPiece test = new Piece();
        IViewPiece testPiece = new ViewPiece(test);
        List<INote> notes = testPiece.getNotes();
        assertEquals(0, notes.size());
    }

    /**
     * Test last beat from view piece
     */
    @Test public void testLastBeat() {
        IViewPiece testList = testViewPieceHelper2();
        assertEquals(4, testList.getLastBeat());
    }

    /**
     * Test last beat one note song. The duration is 1 so 0 was that last beat a note was heard.
     * Should be 0.
     */
    @Test public void testLastBeatShort() {
        IPiece test = new Piece();
        test.addNote(new Note(Pitch.C, new Octave(9), 0, 1));
        IViewPiece testList = new ViewPiece(test);
        assertEquals(0, testList.getLastBeat());
    }

    /**
     * Test last beat empty note list
     */
    @Test public void testEmptyLastBeat() {
        IPiece test = new Piece();
        IViewPiece testPiece = new ViewPiece(test);
        assertEquals(0, testPiece.getLastBeat());
    }

    /**
     * Test music display of an empty song
     */
    @Test public void testEmptyDisplay() {
        IPiece test = new Piece();
        IViewPiece testPiece = new ViewPiece(test);
        assertEquals("There are no musical notes to display.", testPiece.musicOutput());
    }

    /**
     * Test music display of an 1 note, 1 duration song.
     * Song starts and ends at beat 0
     */
    @Test public void testOneNoteDisplay() {
        IPiece test = new Piece();
        test.addNotes(new Note(Pitch.C, new Octave(5), 0, 1));
        IViewPiece testPiece = new ViewPiece(test);
        assertEquals("   C5 \n" + "0  X  \n", testPiece.musicOutput());
    }

    /**
     * Test music display of an 1 octave song.
     * Song starts and ends at beat 0
     */
    @Test public void testOneOctaveDisplay() {
        IPiece test = new Piece();
        test.addNotes(new Note(Pitch.C, new Octave(5), 0, 1), new Note(Pitch.F, new Octave(5), 0,
            1));
        IViewPiece testPiece = new ViewPiece(test);
        assertEquals("   C5  C#5   D5  D#5   E5   F5 \n" + "0  X                        X  \n",
            testPiece.musicOutput());
    }

    /**
     * Test music display of an 1 octave song.
     * Song is more than 1 beat long.
     */
    @Test public void testOneOctaveDisplayLongDuration() {
        IPiece test = new Piece();
        test.addNotes(new Note(Pitch.C, new Octave(5), 0, 1), new Note(Pitch.F, new Octave(5), 0,
            7));
        IViewPiece testPiece = new ViewPiece(test);
        assertEquals("   C5  C#5   D5  D#5   E5   F5 \n" + "0  X                        X  \n"
                + "1                           |  \n" + "2                           |  \n"
                + "3                           |  \n" + "4                           |  \n"
                + "5                           |  \n" + "6                           |  \n",
            testPiece.musicOutput());
    }

    /**
     * Run a test on a normal note list.
     * Lowest tone is C#3
     * Highest tone is B6
     * Song has music in beats 0 - 9
     */
    @Test public void testNormalNoteList() {
        IViewPiece test = testViewPieceHelper();
        assertEquals("  C#3   D3  D#3   E3   F3  F#3   G3  G#3   A3  A#3   B3   C4  C#4   D4  "
            + "D#4  "
                + " E4   F4  F#4   G4  G#4   A4  A#4   B4   C5  C#5   D5  D#5   E5   F5  F#5   "
            + "G5  G#5 "
                + "  A5  A#5   B5   C6  C#6   D6  D#6   E6   F6  F#6   G6  G#6   A6  A#6  "
            + " B6 \n"
                + "0                                          X                            "
            + "            "
                + "                                                                          "
            + "          "
                + "                                                                    \n"
                + "1                                                                         "
            + "          "
                + "                   X                                                       "
            + "         "
                + "                                                                    \n"
                + "2                                                                           "
            + "        "
                + "                   |                                                        "
            + "        "
                + "                                                                 X  \n"
                + "3                                          X                                 "
            + "       "
                + "                                                                             "
            + "       "
                + "                                                                    \n"
                + "4  X                        X              |                               "
            + "         "
                + "                                                                     X      "
            + "        "
                + "                                                                    \n"
                + "5  |                        |                                                "
            + "       "
                + "                   X                                                         "
            + "       "
                + "                                                                    \n"
                + "6                           |                                                "
            + "       "
                + "                             X                                               "
            + "       "
                + "                                                                    \n"
                + "7                 X                        X                                "
            + "        "
                + "                             |                                              "
            + "        "
                + "                                                                    \n"
                + "8                 |                                                          "
            + "       "
                + "                             |                                               "
            + "       "
                + "                                                                    \n"
                + "9                                                                          "
            + "         "
                + "                             |                                            "
            + "          "
                + "                                                                    \n",
            test.musicOutput());
    }

    /**
     * Test that the music display will display the beat # correctly with larger than 1 digit
     */
    @Test public void testBeatNumberDisplay() {
        IPiece test = new Piece();
        test.addNote(new Note(Pitch.C, new Octave(4), 2, 1000));
        for (int i = 0; i < 250; i++) {
            test.addNote(new Note(Pitch.FSHARP, new Octave(3), i * 4, 2));
        }
        IViewPiece testPiece = new ViewPiece(test);
        String output = testPiece.musicOutput();
        assertTrue(output.contains("     F#3   G3  G#3   A3  A#3   B3   C4 "));
        assertTrue(output.contains("   2                                X  "));
        assertTrue(output.contains("  12  X                             |  "));
        assertTrue(output.contains(" 100  X                             |  "));
        assertTrue(output.contains(" 999                                |  "));
        assertTrue(output.contains("1000                                |  "));
    }

    /**
     * Test the music display with unusual octaves
     */
    @Test public void testDisplayLowOctaves() {
        IPiece test = new Piece();
        test.addNotes(new Note(Pitch.C, new Octave(-9), 0, 5),
            new Note(Pitch.CSHARP, new Octave(0), 4, 2), new Note(Pitch.F, new Octave(-4), 2,
                3));
        IViewPiece testPiece = new ViewPiece(test);
        assertEquals("  C-9  C#-9 D-9  D#-9 E-9  F-9  F#-9 G-9  G#-9 A-9  A#-9 B-9  C-8  "
            + "C#-8 D-8  "
            + "D#-8 E-8  F-8  F#-8 G-8  G#-8 A-8  A#-8 B-8  C-7  C#-7 D-7  D#-7 E-7  F-7  "
            + "F#-7 G-7 "
            + " G#-7 A-7  A#-7 B-7  C-6  C#-6 D-6  D#-6 E-6  F-6  F#-6 G-6  G#-6 A-6  A#-6 "
            + "B-6  C-5"
            + "  C#-5 D-5  D#-5 E-5  F-5  F#-5 G-5  G#-5 A-5  A#-5 B-5  C-4  C#-4 D-4  D#-4"
            + " E-4  "
            + "F-4  F#-4 G-4  G#-4 A-4  A#-4 B-4  C-3  C#-3 D-3  D#-3 E-3  F-3  F#-3 G-3  "
            + "G#-3 A-3"
            + "  A#-3 B-3  C-2  C#-2 D-2  D#-2 E-2  F-2  F#-2 G-2  G#-2 A-2  A#-2 B-2  C-1"
            + "  C#-1 "
            + "D-1  D#-1 E-1  F-1  F#-1 G-1  G#-1 A-1  A#-1 B-1   C0  C#0 \n"
            + "0  X                                                                        "
            + "        "
            + "                                                                            "
            + "        "
            + "                                                                            "
            + "        "
            + "                                                                            "
            + "        "
            + "                                                                            "
            + "        "
            + "                                                                            "
            + "        "
            + "                                               \n"
            + "1  |                                                                        "
            + "        "
            + "                                                                            "
            + "        "
            + "                                                                             "
            + "       "
            + "                                                                             "
            + "       "
            + "                                                                             "
            + "       "
            + "                                                                             "
            + "       "
            + "                                               \n"
            + "2  |                                                                         "
            + "       "
            + "                                                                             "
            + "       "
            + "                                                                              "
            + "      "
            + "                                                                            X "
            + "      "
            + "                                                                               "
            + "     "
            + "                                                                               "
            + "     "
            + "                                               \n"
            + "3  |                                                                          "
            + "      "
            + "                                                                              "
            + "      "
            + "                                                                              "
            + "      "
            + "                                                                            |  "
            + "     "
            + "                                                                               "
            + "     "
            + "                                                                               "
            + "     "
            + "                                               \n"
            + "4  |                                                                          "
            + "      "
            + "                                                                              "
            + "      "
            + "                                                                              "
            + "      "
            + "                                                                            |  "
            + "     "
            + "                                                                               "
            + "     "
            + "                                                                                "
            + "    "
            + "                                            X  \n"
            + "5                                                                             "
            + "      "
            + "                                                                              "
            + "      "
            + "                                                                               "
            + "     "
            + "                                                                               "
            + "     "
            + "                                                                                "
            + "    "
            + "                                                                               "
            + "     "
            + "                                            |  \n", testPiece.musicOutput());
    }

    /**
     * Test getting all the notes at a specific beat.
     */
    @Test
    public void testBeatFetch() {
        IViewPiece testPiece = testViewPieceHelper3();
        List<INote> notesInBeat = testPiece.getNotesInBeat(3);
        assertTrue(notesInBeat.contains(new Note(Pitch.FSHARP, new Octave(6), 2, 4)));
        assertTrue(notesInBeat.contains(new Note(Pitch.ASHARP, new Octave(3), 3, 2)));
        assertTrue(notesInBeat.contains(new Note(Pitch.DSHARP, new Octave(3), 1, 4)));
        assertTrue(notesInBeat.contains(new Note(Pitch.FSHARP, new Octave(6), 1, 3)));
        assertTrue(notesInBeat.contains(new Note(Pitch.ASHARP, new Octave(3), 2, 2)));
    }

    /**
     * Test overlapping notes
     */
    @Test
    public void testOverlapAllowed() {
        IViewPiece testList = testViewPieceHelper3();
        List<INote> notes = testList.getNotes();
        assertTrue(notes.contains(new Note(Pitch.FSHARP, new Octave(6), 1, 3)));
        assertTrue(notes.contains(new Note(Pitch.FSHARP, new Octave(6), 2, 4)));
    }

    /**
     * Test the consolidation map
     */
    @Test
    public void testConsolitationMap() {
        IViewPiece testList = testViewPieceHelper5();
        Map<Integer, List<INote>> data = testList.getConsolidationMap();
        assertEquals(7, data.size());
        //Playing at beat 0
        assertTrue(data.get(0).contains(new Note(Pitch.DSHARP, new Octave(2), 0, 3)));

        //Playing at beat 1
        assertTrue(data.get(1).contains(new Note(Pitch.CSHARP, new Octave(3), 1, 2)));
        assertTrue(data.get(1).contains(new Note(Pitch.DSHARP, new Octave(2), 1, 4)));
        assertTrue(data.get(1).contains(new Note(Pitch.FSHARP, new Octave(2), 1, 3)));
        assertTrue(data.get(1).contains(new Note(Pitch.B, new Octave(2), 1, 4)));
        assertTrue(data.get(1).contains(new Note(Pitch.C, new Octave(2), 1, 3)));
        //Continueing at beat 1
        assertTrue(data.get(1).contains(new Note(Pitch.DSHARP, new Octave(2), 0, 3)));

        //Playing at beat 2
        assertTrue(data.get(2).contains(new Note(Pitch.FSHARP, new Octave(2), 2, 4)));
        assertTrue(data.get(2).contains(new Note(Pitch.ASHARP, new Octave(3), 2, 2)));
        assertTrue(data.get(2).contains(new Note(Pitch.E, new Octave(3), 2, 2)));
        //Continueing at beat 2
        assertTrue(data.get(2).contains(new Note(Pitch.DSHARP, new Octave(2), 0, 3)));
        assertTrue(data.get(2).contains(new Note(Pitch.CSHARP, new Octave(3), 1, 2)));
        assertTrue(data.get(2).contains(new Note(Pitch.DSHARP, new Octave(2), 1, 4)));
        assertTrue(data.get(2).contains(new Note(Pitch.FSHARP, new Octave(2), 1, 3)));
        assertTrue(data.get(2).contains(new Note(Pitch.B, new Octave(2), 1, 4)));
        assertTrue(data.get(2).contains(new Note(Pitch.C, new Octave(2), 1, 3)));

        //Playing at beat 3
        assertTrue(data.get(3).contains(new Note(Pitch.ASHARP, new Octave(2), 3, 2)));
        //Continueing at beat 3
        assertTrue(data.get(3).contains(new Note(Pitch.FSHARP, new Octave(2), 1, 3)));
        assertTrue(data.get(3).contains(new Note(Pitch.C, new Octave(2), 1, 3)));
        assertTrue(data.get(3).contains(new Note(Pitch.ASHARP, new Octave(3), 2, 2)));
        assertTrue(data.get(3).contains(new Note(Pitch.E, new Octave(3), 2, 2)));
        assertTrue(data.get(3).contains(new Note(Pitch.FSHARP, new Octave(2), 2, 4)));
        assertTrue(data.get(3).contains(new Note(Pitch.DSHARP, new Octave(2), 1, 4)));
        assertTrue(data.get(3).contains(new Note(Pitch.DSHARP, new Octave(2), 1, 4)));

        //Playing at beat 4
        assertTrue(data.get(4).contains(new Note(Pitch.CSHARP, new Octave(3), 4, 3)));
        assertTrue(data.get(4).contains(new Note(Pitch.D, new Octave(3), 4, 3)));
        //Continuing at beat 4
        assertTrue(data.get(4).contains(new Note(Pitch.FSHARP, new Octave(2), 2, 4)));
        assertTrue(data.get(4).contains(new Note(Pitch.ASHARP, new Octave(2), 3, 2)));
        assertTrue(data.get(4).contains(new Note(Pitch.DSHARP, new Octave(2), 1, 4)));
        assertTrue(data.get(4).contains(new Note(Pitch.B, new Octave(2), 1, 4)));

        //Continuing at beat 5
        assertTrue(data.get(5).contains(new Note(Pitch.FSHARP, new Octave(2), 2, 4)));
        assertTrue(data.get(5).contains(new Note(Pitch.CSHARP, new Octave(3), 4, 3)));
        assertTrue(data.get(5).contains(new Note(Pitch.D, new Octave(3), 4, 3)));

        //Continuing at beat 6
        assertTrue(data.get(6).contains(new Note(Pitch.CSHARP, new Octave(3), 4, 3)));
        assertTrue(data.get(6).contains(new Note(Pitch.D, new Octave(3), 4, 3)));
    }

    private IViewPiece testViewPieceHelper() {
        IPiece test = new Piece();
        test.addNote(new Note(Pitch.A, new Octave(3), 0, 1));
        test.addNote(new Note(Pitch.A, new Octave(4), 1, 2));
        test.addNote(new Note(Pitch.B, new Octave(6), 2, 1));
        test.addNote(new Note(Pitch.A, new Octave(3), 3, 2));
        test.addNote(new Note(Pitch.CSHARP, new Octave(3), 4, 2));
        test.addNote(new Note(Pitch.G, new Octave(5), 4, 1));
        test.addNote(new Note(Pitch.FSHARP, new Octave(3), 4, 3));
        test.addNote(new Note(Pitch.A, new Octave(4), 5, 1));
        test.addNote(new Note(Pitch.B, new Octave(4), 6, 4));
        test.addNote(new Note(Pitch.A, new Octave(3), 7, 1));
        test.addNote(new Note(Pitch.E, new Octave(3), 7, 2));
        return new ViewPiece(test);
    }

    private IViewPiece testViewPieceHelper2() {
        IPiece test = new Piece();
        test.addNote(new Note(Pitch.DSHARP, new Octave(3), 0, 1));
        test.addNote(new Note(Pitch.CSHARP, new Octave(4), 1, 2));
        test.addNote(new Note(Pitch.FSHARP, new Octave(6), 2, 1));
        test.addNote(new Note(Pitch.ASHARP, new Octave(3), 3, 2));
        return new ViewPiece(test);
    }

    private IViewPiece testViewPieceHelper3() {
        IPiece test = new Piece();
        test.addNote(new Note(Pitch.DSHARP, new Octave(3), 0, 3));
        test.addNote(new Note(Pitch.CSHARP, new Octave(4), 1, 2));
        test.addNote(new Note(Pitch.FSHARP, new Octave(6), 2, 4));
        test.addNote(new Note(Pitch.ASHARP, new Octave(3), 3, 2));
        test.addNote(new Note(Pitch.DSHARP, new Octave(3), 1, 4));
        test.addNote(new Note(Pitch.CSHARP, new Octave(4), 4, 3));
        test.addNote(new Note(Pitch.FSHARP, new Octave(6), 1, 3));
        test.addNote(new Note(Pitch.ASHARP, new Octave(3), 2, 2));
        return new ViewPiece(test);
    }

    private IViewPiece testViewPieceHelper5() {
        IPiece test = new Piece();
        test.addNote(new Note(Pitch.DSHARP, new Octave(2), 0, 3));
        test.addNote(new Note(Pitch.CSHARP, new Octave(3), 1, 2));
        test.addNote(new Note(Pitch.FSHARP, new Octave(2), 2, 4));
        test.addNote(new Note(Pitch.ASHARP, new Octave(2), 3, 2));
        test.addNote(new Note(Pitch.DSHARP, new Octave(2), 1, 4));
        test.addNote(new Note(Pitch.CSHARP, new Octave(3), 4, 3));
        test.addNote(new Note(Pitch.FSHARP, new Octave(2), 1, 3));
        test.addNote(new Note(Pitch.ASHARP, new Octave(3), 2, 2));
        test.addNote(new Note(Pitch.B, new Octave(2), 1, 4));
        test.addNote(new Note(Pitch.D, new Octave(3), 4, 3));
        test.addNote(new Note(Pitch.C, new Octave(2), 1, 3));
        test.addNote(new Note(Pitch.E, new Octave(3), 2, 2));
        return new ViewPiece(test);
    }
}
