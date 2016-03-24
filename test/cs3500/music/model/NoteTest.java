package cs3500.music.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test that the Note class is working correctly.
 *
 * Created by Ben on 3/2/16.
 */
public class NoteTest {
    /**
     * Test the display of a note with 2 characters for the tone
     */
    @Test
    public void testToString2Char() {
        INote testNote = new Note(Pitch.A, new Octave(4), 0, 2);
        assertEquals("  A4 ", testNote.toString());
    }

    /**
     * Test the display of a note with 3 characters for the tone
     */
    @Test
    public void testToString3Char() {
        INote testNote = new Note(Pitch.ASHARP, new Octave(5), 0, 2);
        assertEquals(" A#5 ", testNote.toString());
    }

    /**
     * Test the display of a note with 4 characters for the tone
     */
    @Test
    public void testToString4Char() {
        INote testNote = new Note(Pitch.GSHARP, new Octave(10), 0, 2);
        assertEquals(" G#10", testNote.toString());
    }

    /**
     * Test that a note can't be created before the song starts.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testNewNoteBeforeSong() {
        INote testNote = new Note(Pitch.GSHARP, new Octave(10), -1, 2);
    }

    /**
     * Test that a note must have a duration.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testNewNoteNoDuration() {
        INote testNote = new Note(Pitch.GSHARP, new Octave(10), 0, 0);
    }

    /**
     * Test that a note must have a positive duration.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testNewNoteNegDuration() {
        INote testNote = new Note(Pitch.GSHARP, new Octave(10), 0, -1);
    }

    /**
     * Test the error message of a note before the song starts.
     */
    @Test
    public void testNewNoteBeforeSongMessage() {
        try {
            INote testNote = new Note(Pitch.GSHARP, new Octave(10), -1, 2);
        }
        catch (IllegalArgumentException exp) {
            assertEquals("Error. Note must start at beat 0 or later.", exp.getMessage());
        }
    }

    /**
     * Test the error message of a note with no duration.
     */
    @Test
    public void testNewNoteNoDurationMessage() {
        try {
            INote testNote = new Note(Pitch.GSHARP, new Octave(10), 0, 0);
        }
        catch (IllegalArgumentException exp) {
            assertEquals("Error. Durations must be 1 beat or greater.", exp.getMessage());
        }
    }

    /**
     * Test the error message of a note with negative duration.
     */
    @Test
    public void testNewNoteNegDurationMessage() {
        try {
            INote testNote = new Note(Pitch.GSHARP, new Octave(10), 0, -1);
        }
        catch (IllegalArgumentException exp) {
            assertEquals("Error. Durations must be 1 beat or greater.", exp.getMessage());
        }
    }

    /**
     * Test that values of a note can be incremented.
     * increment all fields of a test note from E5 (half note starting at beat 0)
     * to F6 (3/4 note starting at beat 1)
     */
    @Test
    public void testIncrement() {
        INote testNote = new Note(Pitch.E, new Octave(5), 0, 2);
        assertEquals("  E5 ", testNote.toString());
        assertEquals(0, testNote.getStart());
        assertEquals(2, testNote.getDuration());
        testNote.increment(NoteField.OCTAVE);
        testNote.increment(NoteField.PITCH);
        testNote.increment(NoteField.START);
        testNote.increment(NoteField.DURATION);
        assertEquals("  F6 ", testNote.toString());
        assertEquals(1, testNote.getStart());
        assertEquals(3, testNote.getDuration());
    }


    /**
     * Test that values of a note can be incremented.
     * increment all fields in a wrapping scenario when possible.
     * B99 beat 10 duration 10
     * C-9 beat 11 duration 11
     */
    @Test
    public void testIncrementWrap() {
        INote testNote = new Note(Pitch.B, new Octave(99), 10, 10);
        assertEquals(" B99 ", testNote.toString());
        assertEquals(10, testNote.getStart());
        assertEquals(10, testNote.getDuration());
        testNote.increment(NoteField.PITCH);
        testNote.increment(NoteField.OCTAVE);
        testNote.increment(NoteField.START);
        testNote.increment(NoteField.DURATION);
        assertEquals(" C-9 ", testNote.toString());
        assertEquals(11, testNote.getStart());
        assertEquals(11, testNote.getDuration());
    }

    /**
     * Test the getters and setters for a note.
     */
    @Test
    public void testGetters() {
        INote testNote = new Note(Pitch.B, new Octave(5), 0, 2);
        assertEquals(0, testNote.getStart());
        assertEquals(2, testNote.getDuration());
        assertEquals(Pitch.B, testNote.getPitch());
        assertEquals(5, testNote.getOctave().getValue());
    }

    /**
     * Test that Octave is returned using a copy
     */
    @Test
    public void testOctaveGetter() {
        Octave testOctave = new Octave(5);
        INote testNote = new Note(Pitch.B, testOctave, 0, 2);
        assertEquals(5, testNote.getOctave().getValue());
        assertNotEquals(testOctave, testNote.getOctave());
    }

    /**
     * Test the getters and setters for a note.
     */
    @Test
    public void testSetters() {
        INote testNote = new Note(Pitch.C, new Octave(3), 1, 6);
        testNote.setDuration(2);
        testNote.setStart(0);
        testNote.setOctave(new Octave(5));
        testNote.setPitch(Pitch.A);

        assertEquals(0, testNote.getStart());
        assertEquals(2, testNote.getDuration());
        assertEquals(Pitch.A, testNote.getPitch());
        assertEquals(5, testNote.getOctave().getValue());
    }

    /**
     * Test that the equals returns true if all of the fields are equal.
     */
    @Test
    public void testEquals() {
        INote testNote1 = new Note(Pitch.C, new Octave(3), 1, 6);
        INote testNote2 = new Note(Pitch.C, new Octave(3), 1, 6);
        assertTrue(testNote1.equals(testNote2));
    }

    /**
     * Test that the equals function returns true if they are the same reference.
     */
    @Test
    public void testEqualsSameRef() {
        INote testNote1 = new Note(Pitch.C, new Octave(3), 1, 6);
        INote testNote2 = testNote1;
        assertTrue(testNote1.equals(testNote2));
    }

    /**
     * Test that equals function returns false if pitch is different
     */
    @Test
    public void testEqualsFalse1() {
        INote testNote1 = new Note(Pitch.C, new Octave(3), 1, 6);
        INote testNote2 = new Note(Pitch.D, new Octave(3), 1, 6);
        assertFalse(testNote1.equals(testNote2));
    }

    /**
     * Test that equals function returns false if octave is different
     */
    @Test
    public void testEqualsFalse2() {
        INote testNote1 = new Note(Pitch.C, new Octave(3), 1, 6);
        INote testNote2 = new Note(Pitch.C, new Octave(4), 1, 6);
        assertFalse(testNote1.equals(testNote2));
    }

    /**
     * Test that equals function returns false if start beat is different
     */
    @Test
    public void testEqualsFalse3() {
        INote testNote1 = new Note(Pitch.C, new Octave(3), 1, 6);
        INote testNote2 = new Note(Pitch.C, new Octave(3), 2, 6);
        assertFalse(testNote1.equals(testNote2));
    }

    /**
     * Test that equals function returns false if duration is different
     */
    @Test
    public void testEqualsFalse4() {
        INote testNote1 = new Note(Pitch.C, new Octave(3), 1, 6);
        INote testNote2 = new Note(Pitch.C, new Octave(3), 1, 2);
        assertFalse(testNote1.equals(testNote2));
    }

    /**
     * Test that the hashcode will be the same for notes that are equal
     */
    @Test
    public void testHashCode() {
        INote testNote1 = new Note(Pitch.C, new Octave(3), 1, 6);
        INote testNote2 = new Note(Pitch.C, new Octave(3), 1, 6);
        assertEquals(testNote1.hashCode(), testNote2.hashCode());
    }

    /**
     * Test that the hash codes are equal if the notes have the same reference.
     */
    @Test
    public void testHashCodeSameRef() {
        INote testNote1 = new Note(Pitch.C, new Octave(3), 1, 6);
        INote testNote2 = testNote1;
        assertEquals(testNote1.hashCode(), testNote2.hashCode());
    }

    /**
     * Test that hash code value will be different if pitch is different
     */
    @Test
    public void testHashCodeFalse1() {
        INote testNote1 = new Note(Pitch.C, new Octave(3), 1, 6);
        INote testNote2 = new Note(Pitch.D, new Octave(3), 1, 6);
        assertNotEquals(testNote1.hashCode(), testNote2.hashCode());
    }

    /**
     * Test that hash code value will be different if octave is different
     */
    @Test
    public void testHashCodeFalse2() {
        INote testNote1 = new Note(Pitch.C, new Octave(3), 1, 6);
        INote testNote2 = new Note(Pitch.C, new Octave(4), 1, 6);
        assertNotEquals(testNote1.hashCode(), testNote2.hashCode());
    }

    /**
     * Test that hash code value will be different if start beat is different
     */
    @Test
    public void testHashCodeFalse3() {
        INote testNote1 = new Note(Pitch.C, new Octave(3), 1, 6);
        INote testNote2 = new Note(Pitch.C, new Octave(3), 2, 6);
        assertNotEquals(testNote1.hashCode(), testNote2.hashCode());
    }

    /**
     * Test that hashCode function will be different if the note's durations are different
     */
    @Test
    public void testHashCodeFalse4() {
       INote testNote1 = new Note(Pitch.C, new Octave(3), 1, 6);
       INote testNote2 = new Note(Pitch.C, new Octave(3), 1, 2);
        assertNotEquals(testNote1.hashCode(), testNote2.hashCode());
    }

    /**
     * Test copy of a note. Values should be deep copies.
     */
    @Test
    public void testCopy() {
       INote testNote1 = new Note(Pitch.C, new Octave(3), 1, 6);
       INote testNote2 = testNote1.copy();
        assertEquals(testNote1.getPitch(), testNote2.getPitch());
        assertEquals(testNote1.getDuration(), testNote2.getDuration());
        assertEquals(testNote1.getStart(), testNote2.getStart());
        assertEquals(testNote1.getOctave().getValue(), testNote2.getOctave().getValue());
        assertNotEquals(testNote1.getOctave(), testNote2.getOctave());
    }

    /**
     * Test the isStarting method when should be true.
     */
    @Test
    public void testIsStarting() {
       INote testNote1 = new Note(Pitch.C, new Octave(3), 1, 6);
       INote testNote2 = new Note(Pitch.C, new Octave(3), 1, 1);
        assertTrue(testNote1.isStarting(testNote2));
    }

    /**
     * Test the isStarting method when should be false.
     */
    @Test
    public void testIsStartingFalse() {
       INote testNote1 = new Note(Pitch.C, new Octave(3), 1, 6);
       INote testNote2 = new Note(Pitch.D, new Octave(3), 1, 1);
        //Different pitch
        assertFalse(testNote1.isStarting(testNote2));
        testNote2 = new Note(Pitch.C, new Octave(4), 1, 1);
        //Different Octave
        assertFalse(testNote1.isStarting(testNote2));
        testNote2 = new Note(Pitch.C, new Octave(3), 2, 1);
        //Different Start Beat
        assertFalse(testNote1.isStarting(testNote2));
    }

    /**
     * Test the isPersistent method when should be true.
     */
    @Test
    public void testIsPersistent() {
       INote testNote1 = new Note(Pitch.C, new Octave(3), 1, 6);
       INote testNote2 = new Note(Pitch.C, new Octave(3), 2, 1);
        assertTrue(testNote1.isPersisting(testNote2));
    }

    /**
     * Test the isPersistent method when should be false.
     */
    @Test
    public void testIsPersistentFalse() {
       INote testNote1 = new Note(Pitch.C, new Octave(3), 1, 6);
       INote testNote2 = new Note(Pitch.D, new Octave(3), 1, 1);
        //Different pitch
        assertFalse(testNote1.isPersisting(testNote2));
        testNote2 = new Note(Pitch.C, new Octave(4), 1, 1);
        //Different Octave
        assertFalse(testNote1.isPersisting(testNote2));
        testNote2 = new Note(Pitch.C, new Octave(3), 0, 1);
        //start beat out of bounds before
        assertFalse(testNote1.isPersisting(testNote2));
        testNote2 = new Note(Pitch.C, new Octave(3), 7, 1);
        //start beat out of bounds after
        assertFalse(testNote1.isPersisting(testNote2));
    }
}
