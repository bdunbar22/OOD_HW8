README

Working in collaboration: Ben Dunbar and Sam Letcher

TODO: update

Hello

This is my project submission for homework 5, The Music Editor: First Movement. It realizes a model
for representing and editing music. It allows for a number of operations to be made to a musical
work.

In this model music is represented as a list of notes. Each note is an object with a pitch, start
beat, duration and octave. The note class ensures that the start beat >= 0 and the duration is > 0.
While a song may have no notes at some point during editing, a note would not exist without a
duration and must start at a positive time in the song in my implementation, the note class ensures
these invariants. With this idea in mind, the class must be given values when initialized. To ensure
 this only a constructor with all fields is provided. Pitches and Octaves are supported and limited
according to the pitch enum and octave class as explained next. Songs always start at beat 0 even if
no notes are played at that beat.

The following pitches are supported: C C♯ D D♯ E F F♯ G G♯ A A♯ B
This model supports octaves -9 to 99. I made this decision based on the display of music having 5
characters. A display of a pitch and octave therefor has the full range of C-9 to B99 with a
character range of 2 characters to 4 characters. (Ex: C1 and A#10) This octave choice meets all
octave requirements because it allows for the 10 octaves of hearing an average human has, does not
interfere with the display of music, and will further allow for music to be played in circumstances
where the music maker may want to go outside of the range of human hearing, for dogs as an example.

The pitches were set up as an enum because of the limited and finite number of pitches to represent.
Octaves were enabled via an octave class that ensures that the octave invariants are met.

Notes are considered equal (Overridden) if their parameters have the same values. Notes also have
the functionality to produce copies of themselves and display their pitch and octave combination. If
given a note to check against in the isStarting function, the note can return whether or not
it is starting at that pitch, octave & starting beat. Likewise, if a note is given a note to check
against in the isPersistent function, the note can return if it is continuing to be played at the
starting beat of the check note and includes a check on the pitch and tone. This makes it possible
to display music in a logical fashion. Finally, edits can be made to notes and fields can be
incremented with the increment() function. Two comparators are provided for notes: NoteComparator
can be used to sort notes by starting beat then octave then pitch; PitchAndOctaveComparator can
be used to sort notes by octave then pitch to find the tone range in a piece of music.

Creating music from notes is enabled with the INoteList and IPiece interfaces.
Creating music from notes is realized with the NoteList and Piece classes.

Class Adapter Design:
INoteList                                     (gives basic functionality.)
NoteList implements INoteList.
IPiece extends INoteList                      (provide more complex functions.)
Piece extends NoteList and implements IPiece.

Piece is a final class and cannot be extended. This adds safety to the design. I added the piece
interface and class to the project to provide complex functionality but made it separate from the
note list so that if future edits are made on the project it would be possible to extend the
basic INoteList in different ways.

NoteList utilizes hidden private methods that are called from all of the public overridden methods.
This is a safe design and highly desired in the way I modeled music because I want classes that
extend NoteList to be able to call the functions in note list, but I do not want functions in note
list to cause unexpected errors for the aforementioned classes. Classes that extend NoteList should
use the super() call in their constructors.

This design offers the provided functionality:
From INoteList:
- add a note
- add many notes via a List or unspecified number of Note arguments
- change a note
- remove a note
- determine if the music contains a note (using overridden equals of Note class)
- Get the notes in a list of music (Returns a deep copied list for security)
- Get the number of the last beat that music will be playing in.
  and
- DISPLAY MUSIC TO THE CONSOLE: String musicOutput();

From IPiece
(All methods return a new piece of music so the original(s) aren't lost.)
- merge two pieces of music in a serial fashion: One plays after the other.
- merge two pieces of music in a parallel fashion: They will play at the same time.
- increment a field on every note in the piece:
    * The pitch of every note could be increased (wraps around pitches available)
    * The octave of every note could be increased (wraps around octaves available)
    * The starting beat of every not could be increased. (could make more interesting song merges)
    * The duration of every note could be increased.
- increment a field on every note a given number of times.
- reverse a piece of music
- copy a piece of music

All provided methods have been tested thoroughly.

Best Regards,
Ben Dunbar
