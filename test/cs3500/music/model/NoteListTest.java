package cs3500.music.model;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test the NoteList functionality. The note list implements the INoteList interface.
 *
 * Created by Ben on 3/2/16.
 */
public class NoteListTest {
    /**
     * Test add note
     */
    @Test
    public void testAddNote() {
        INoteList testList= new NoteList();
        testList.addNote(new Note(Pitch.C, new Octave(3), 0, 1));
        assertEquals(1, testList.getNotes().size());
    }

    /**
     * Test add duplicate note.
     * Duplicate should be ignored.
     */
    @Test
    public void testDuplicateAdd() {
        INoteList testList= new NoteList();
        testList.addNote(new Note(Pitch.C, new Octave(3), 0, 1));
        testList.addNote(new Note(Pitch.C, new Octave(3), 0, 1));
        assertEquals(1, testList.getNotes().size());
    }

    /**
     * Test add notes
     */
    @Test
    public void testAddNotes() {
        INoteList testList= new NoteList();
        testList.addNotes(new Note(Pitch.C, new Octave(3), 0, 1),
                          new Note(Pitch.C, new Octave(4), 0, 1));
        assertEquals(2, testList.getNotes().size());
    }

    /**
     * Test add notes (List)
     */
    @Test
    public void testAddNotesList() {
        INoteList testList= new NoteList();
        List<INote> notes = Arrays.asList(new Note(Pitch.C, new Octave(3), 0, 1),
                                         new Note(Pitch.C, new Octave(4), 0, 1));
        testList.addNotes(notes);
        assertEquals(2, testList.getNotes().size());
    }

    /**
     * Test add notes with duplicates
     */
    @Test
    public void testAddNotesDuplicates() {
        INoteList testList= new NoteList();
        List<INote> notes = Arrays.asList(new Note(Pitch.C, new Octave(3), 0, 1),
                                         new Note(Pitch.C, new Octave(4), 0, 1),
                                         new Note(Pitch.C, new Octave(4), 0, 1));
        testList.addNotes(notes);
        assertEquals(2, testList.getNotes().size());
    }

    /**
     * Test changing a note that is not present
     */
    @Test (expected = IllegalArgumentException.class)
    public void testChangeANoteNotPresent() {
        INoteList testList= new NoteList();
        INote note1 = new Note(Pitch.B, new Octave(7), 9, 3);
        INote note2 = new Note(Pitch.F, new Octave(6), 9, 3);
        testList.changeNote(note1, note2);
    }

    /**
     * Test that a note can be changed.
     */
    @Test
    public void testChangeANote() {
        INoteList testList= new NoteList();
        List<INote> notes = Arrays.asList(new Note(Pitch.C, new Octave(3), 0, 1),
                                         new Note(Pitch.C, new Octave(4), 0, 1),
                                         new Note(Pitch.C, new Octave(4), 0, 1));
        testList.addNotes(notes);
        INote note1 = new Note(Pitch.B, new Octave(7), 9, 3);
        testList.addNote(note1);
        INote note2 = new Note(Pitch.F, new Octave(6), 9, 3);
        testList.changeNote(note1, note2);
        assertEquals(3, testList.getNotes().size());
        assertTrue(testList.member(note2));
        assertFalse(testList.member(note1));
    }

    /**
     * Test that a note can be removed.
     */
    @Test
    public void testRemoveNote() {
        INoteList testList = testNormalListHelper();
        assertEquals(11, testList.getNotes().size());
        assertTrue(testList.member(new Note(Pitch.A, new Octave(4), 1, 2)));
        testList.removeNote(new Note(Pitch.A, new Octave(4), 1, 2));
        assertEquals(10, testList.getNotes().size());
        assertFalse(testList.member(new Note(Pitch.A, new Octave(4), 1, 2)));
    }

    /**
     * Try to remove a note that is not present
     */
    @Test (expected = IllegalArgumentException.class)
    public void testRemoveNoteNotPresent() {
        INoteList testList = testNormalListHelper();
        assertEquals(11, testList.getNotes().size());
        assertTrue(testList.member(new Note(Pitch.A, new Octave(4), 1, 2)));
        testList.removeNote(new Note(Pitch.A, new Octave(4), 1, 2));
        testList.removeNote(new Note(Pitch.A, new Octave(4), 1, 2));
    }

    /**
     * Test member check
     */
    @Test
    public void testMemberTrue() {
        INoteList testList = testNormalListHelper();
        assertTrue(testList.member(new Note(Pitch.A, new Octave(4), 1, 2)));
    }

    /**
     * Test member check by ref
     */
    @Test
    public void testMemberTrueRef() {
        INoteList testList = testNormalListHelper();
        INote note = new Note(Pitch.GSHARP, new Octave(4), 1, 2);
        testList.addNote(note);
        assertTrue(testList.member(note));
    }

    /**
     * Test member false
     */
    @Test
    public void testMemberFalseRef() {
        INoteList testList = testNormalListHelper();
        INote note = new Note(Pitch.GSHARP, new Octave(4), 1, 2);
        assertFalse(testList.member(note));
    }

    /**
     * Test member false
     */
    @Test
    public void testMemberFalse() {
        INoteList testList = testNormalListHelper();
        assertFalse(testList.member(new Note(Pitch.GSHARP, new Octave(4), 1, 2)));
    }

    /**
     * Test member of empty note list
     */
    @Test
    public void testMemberFalseWhenListEmpty() {
        INoteList testList = new NoteList();
        assertFalse(testList.member(new Note(Pitch.C, new Octave(9), 0, 1)));
    }

    /**
     * Test get notes
     */
    @Test
    public void testGetNotes() {
        INoteList testList = testNormalListHelper2();
        List<INote> notes = testList.getNotes();
        assertEquals(4, notes.size());
        assertTrue(notes.contains(new Note(Pitch.DSHARP, new Octave(3), 0, 1)));
        assertTrue(notes.contains(new Note(Pitch.CSHARP, new Octave(4), 1, 2)));
        assertTrue(notes.contains(new Note(Pitch.FSHARP, new Octave(6), 2, 1)));
        assertTrue(notes.contains(new Note(Pitch.ASHARP, new Octave(3), 3, 2)));
    }

    /**
     * Test get notes is not by reference
     */
    @Test
    public void testGetNotesNotByRef() {
        INoteList testList = testNormalListHelper2();
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
     * Test get notes empty note list
     */
    @Test
    public void testGetNotesEmpty() {
        INoteList test = new NoteList();
        List<INote> notes = test.getNotes();
        assertEquals(0, notes.size());
    }

    /**
     * Test last beat
     */
    @Test
    public void testLastBeat() {
        INoteList testList = testNormalListHelper2();
        assertEquals(4, testList.getLastBeat());
    }

    /**
     * Test last beat one note song. The duration is 1 so 0 was that last beat a note was heard.
     * Should be 0.
     */
    @Test
    public void testLastBeatShort() {
        INoteList testList = new NoteList();
        testList.addNote(new Note(Pitch.C, new Octave(9), 0, 1));
        assertEquals(0, testList.getLastBeat());
    }

    /**
     * Test last beat long.
     */
    @Test
    public void testLastBeatLongSong() {
        INoteList testList = testNormalListHelper2();
        testList.addNote(new Note(Pitch.C, new Octave(9), 99, 20));
        assertEquals(118, testList.getLastBeat());
    }

    /**
     * Test last beat empty note list
     */
    @Test
    public void testEmpty() {
        INoteList test = new NoteList();
        assertEquals(0, test.getLastBeat());
    }

    /**
     * Test cs3500.music display of an empty song
     */
    @Test
    public void testEmptyDisplay() {
        INoteList test = new NoteList();
        assertEquals("There are no musical notes to display.", test.musicOutput());
    }

    /**
     * Test cs3500.music display of an 1 note, 1 duration song.
     * Song starts and ends at beat 0
     */
    @Test
    public void testOneNoteDisplay() {
        INoteList test = new NoteList();
        test.addNotes(new Note(Pitch.C, new Octave(5), 0, 1));
        assertEquals("   C5 \n" + "0  X  \n", test.musicOutput());
    }

    /**
     * Test cs3500.music display of an 1 octave song.
     * Song starts and ends at beat 0
     */
    @Test
    public void testOneOctaveDisplay() {
        INoteList test = new NoteList();
        test.addNotes(new Note(Pitch.C, new Octave(5), 0, 1),
            new Note(Pitch.F, new Octave(5), 0, 1));
        assertEquals("   C5  C#5   D5  D#5   E5   F5 \n" + "0  X                        X  \n",
            test.musicOutput());
    }

    /**
     * Test cs3500.music display of an 1 octave song.
     * Song is more than 1 beat long.
     */
    @Test
    public void testOneOctaveDisplayLongDuration() {
        INoteList test = new NoteList();
        test.addNotes(new Note(Pitch.C, new Octave(5), 0, 1),
            new Note(Pitch.F, new Octave(5), 0, 7));
        assertEquals("   C5  C#5   D5  D#5   E5   F5 \n" + "0  X                        X  \n"
                + "1                           |  \n" + "2                           |  \n"
                + "3                           |  \n" + "4                           |  \n"
                + "5                           |  \n" + "6                           |  \n",
            test.musicOutput());
    }

    /**
     * Run a test on a normal note list.
     * Lowest tone is C#3
     * Highest tone is B6
     * Song has cs3500.music in beats 0 - 9
     */
    @Test
    public void testNormalNoteList() {
        INoteList test = testNormalListHelper();
        assertEquals("  C#3   D3  D#3   E3   F3  F#3   G3  G#3   A3  A#3   B3   C4  C#4   D4  D#4  "
            + " E4   F4  F#4   G4  G#4   A4  A#4   B4   C5  C#5   D5  D#5   E5   F5  F#5   G5  G#5 "
            + "  A5  A#5   B5   C6  C#6   D6  D#6   E6   F6  F#6   G6  G#6   A6  A#6   B6 \n"
            + "0                                          X                                        "
            + "                                                                                    "
            + "                                                                    \n"
            + "1                                                                                   "
            + "                   X                                                                "
            + "                                                                    \n"
            + "2                                                                                   "
            + "                   |                                                                "
            + "                                                                 X  \n"
            + "3                                          X                                        "
            + "                                                                                    "
            + "                                                                    \n"
            + "4  X                        X              |                                        "
            + "                                                                     X              "
            + "                                                                    \n"
            + "5  |                        |                                                       "
            + "                   X                                                                "
            + "                                                                    \n"
            + "6                           |                                                       "
            + "                             X                                                      "
            + "                                                                    \n"
            + "7                 X                        X                                        "
            + "                             |                                                      "
            + "                                                                    \n"
            + "8                 |                                                                 "
            + "                             |                                                      "
            + "                                                                    \n"
            + "9                                                                                   "
            + "                             |                                                      "
            + "                                                                    \n",
            test.musicOutput());
    }

    /**
     * Test that the cs3500.music display will display the beat # correctly with larger than 1 digit
     */
    @Test
    public void testBeatNumberDisplay() {
        INoteList testList = new NoteList();
        testList.addNote(new Note(Pitch.C, new Octave(4), 2, 1000));
        for(int i = 0; i < 250; i++) {
            testList.addNote(new Note(Pitch.FSHARP, new Octave(3), i * 4, 2));
        }
        String output = testList.musicOutput();
        assertTrue(output.contains("     F#3   G3  G#3   A3  A#3   B3   C4 "));
        assertTrue(output.contains("   2                                X  "));
        assertTrue(output.contains("  12  X                             |  "));
        assertTrue(output.contains(" 100  X                             |  "));
        assertTrue(output.contains(" 999                                |  "));
        assertTrue(output.contains("1000                                |  "));
    }

    /**
     * Test the cs3500.music display with unusual octaves
     */
    @Test
    public void testDisplayLowOctaves() {
        INoteList testList = new NoteList();
        testList.addNotes(new Note(Pitch.C, new Octave(-9), 0, 5),
                 new Note(Pitch.CSHARP, new Octave(0), 4, 2),
                 new Note(Pitch.F, new Octave(-4), 2, 3));
        assertEquals("  C-9  C#-9 D-9  D#-9 E-9  F-9  F#-9 G-9  G#-9 A-9  A#-9 B-9  C-8  C#-8 D-8  "
            + "D#-8 E-8  F-8  F#-8 G-8  G#-8 A-8  A#-8 B-8  C-7  C#-7 D-7  D#-7 E-7  F-7  F#-7 G-7 "
            + " G#-7 A-7  A#-7 B-7  C-6  C#-6 D-6  D#-6 E-6  F-6  F#-6 G-6  G#-6 A-6  A#-6 B-6  C-5"
            + "  C#-5 D-5  D#-5 E-5  F-5  F#-5 G-5  G#-5 A-5  A#-5 B-5  C-4  C#-4 D-4  D#-4 E-4  "
            + "F-4  F#-4 G-4  G#-4 A-4  A#-4 B-4  C-3  C#-3 D-3  D#-3 E-3  F-3  F#-3 G-3  G#-3 A-3"
            + "  A#-3 B-3  C-2  C#-2 D-2  D#-2 E-2  F-2  F#-2 G-2  G#-2 A-2  A#-2 B-2  C-1  C#-1 "
            + "D-1  D#-1 E-1  F-1  F#-1 G-1  G#-1 A-1  A#-1 B-1   C0  C#0 \n"
            + "0  X                                                                                "
            + "                                                                                    "
            + "                                                                                    "
            + "                                                                                    "
            + "                                                                                    "
            + "                                                                                    "
            + "                                               \n"
            + "1  |                                                                                "
            + "                                                                                    "
            + "                                                                                    "
            + "                                                                                    "
            + "                                                                                    "
            + "                                                                                    "
            + "                                               \n"
            + "2  |                                                                                "
            + "                                                                                    "
            + "                                                                                    "
            + "                                                                            X       "
            + "                                                                                    "
            + "                                                                                    "
            + "                                               \n"
            + "3  |                                                                                "
            + "                                                                                    "
            + "                                                                                    "
            + "                                                                            |       "
            + "                                                                                    "
            + "                                                                                    "
            + "                                               \n"
            + "4  |                                                                                "
            + "                                                                                    "
            + "                                                                                    "
            + "                                                                            |       "
            + "                                                                                    "
            + "                                                                                    "
            + "                                            X  \n"
            + "5                                                                                   "
            + "                                                                                    "
            + "                                                                                    "
            + "                                                                                    "
            + "                                                                                    "
            + "                                                                                    "
            + "                                            |  \n", testList.musicOutput());
    }

    /**
     * Test getting all the notes at a specific beat.
     */
    @Test
    public void testBeatFetch() {
        INoteList testList = testNormalListHelper3();
        List<INote> notesInBeat = testList.getNotesInBeat(3);
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
        INoteList testList = testNormalListHelper3();
        List<INote> notes = testList.getNotes();
        assertTrue(notes.contains(new Note(Pitch.FSHARP, new Octave(6), 1, 3)));
        assertTrue(notes.contains(new Note(Pitch.FSHARP, new Octave(6), 2, 4)));
    }

    private INoteList testNormalListHelper() {
        INoteList testList = new NoteList();
        testList.addNote(new Note(Pitch.A, new Octave(3), 0, 1));
        testList.addNote(new Note(Pitch.A, new Octave(4), 1, 2));
        testList.addNote(new Note(Pitch.B, new Octave(6), 2, 1));
        testList.addNote(new Note(Pitch.A, new Octave(3), 3, 2));
        testList.addNote(new Note(Pitch.CSHARP, new Octave(3), 4, 2));
        testList.addNote(new Note(Pitch.G, new Octave(5), 4, 1));
        testList.addNote(new Note(Pitch.FSHARP, new Octave(3), 4, 3));
        testList.addNote(new Note(Pitch.A, new Octave(4), 5, 1));
        testList.addNote(new Note(Pitch.B, new Octave(4), 6, 4));
        testList.addNote(new Note(Pitch.A, new Octave(3), 7, 1));
        testList.addNote(new Note(Pitch.E, new Octave(3), 7, 2));
        return testList;
    }

    private INoteList testNormalListHelper2() {
        INoteList testList = new NoteList();
        testList.addNote(new Note(Pitch.DSHARP, new Octave(3), 0, 1));
        testList.addNote(new Note(Pitch.CSHARP, new Octave(4), 1, 2));
        testList.addNote(new Note(Pitch.FSHARP, new Octave(6), 2, 1));
        testList.addNote(new Note(Pitch.ASHARP, new Octave(3), 3, 2));
        return testList;
    }

    private INoteList testNormalListHelper3() {
        INoteList testList = new NoteList();
        testList.addNote(new Note(Pitch.DSHARP, new Octave(3), 0, 3));
        testList.addNote(new Note(Pitch.CSHARP, new Octave(4), 1, 2));
        testList.addNote(new Note(Pitch.FSHARP, new Octave(6), 2, 4));
        testList.addNote(new Note(Pitch.ASHARP, new Octave(3), 3, 2));
        testList.addNote(new Note(Pitch.DSHARP, new Octave(3), 1, 4));
        testList.addNote(new Note(Pitch.CSHARP, new Octave(4), 4, 3));
        testList.addNote(new Note(Pitch.FSHARP, new Octave(6), 1, 3));
        testList.addNote(new Note(Pitch.ASHARP, new Octave(3), 2, 2));
        return testList;
    }
}
