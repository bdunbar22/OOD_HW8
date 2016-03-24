README

Working in collaboration: Ben Dunbar and Sam Letcher

Hello,

This is our project submission for homework 6, The Music Editor: Second Movement. It realizes a
model for representing and editing music. It allows for a number of operations to be made to a
musical work. It also provides three views to be used on the model.

Class Design:
Model:
    INote
    Note (final) implements INote
    INoteList                                     (gives basic functionality.)
    NoteList (final) implements INoteList.
    IPiece extends INoteList                      (provide more complex functions.)
    Piece extends NoteList and implements IPiece.
    Pitch enum, Octave class, Output enum, NoteField enum and Comparators also provided.
View:
    IMusicView
    GuiViewFrame implements IMusicView has a ConcreteGuiViewPanel
    MidiViewImpl implements IMusicView
    ConsoleView implements IMusicView
    MusicViewCreator allows for the creation of different views
    IViewPiece
    ViewPiece implements IViewPiece
Util:
    MusicReader
    ICompositionBuilder
    Composition builder implements ICompositionBuilder

MusicEditor

Functionality:
INote gives the functionality to display a note tone as a string, increment a field of a note, copy
a note, and determine whether or not a note is starting, continueing to play, or not playing at a
given beat.
Note realizes this functionality and is set as final so that it can not be exteneded.

From INoteList:
- add a note
- add many notes via a List or unspecified number of Note arguments
- change a note
- remove a note
- determine if the music contains a note (using overridden equals of Note class)
- Get the notes in a list of music (Returns a deep copied list for security)
- Get the number of the last beat that music will be playing in.
  and
- Get a consolidation map. Great data model for accessing notes that are audible at a given beat.
NoteList utilizes hidden private methods that are called from all of the public overridden methods.
This is a safe design and highly desired in the way I modeled music because I want classes that
extend NoteList to be able to call the functions in note list, but I do not want functions in note
list to cause unexpected errors for the aforementioned classes. Classes that extend NoteList should
use the super() call in their constructors.

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
- get the tempo of a piece of music
- get the timing (length of each measure) for a pice of music.
Piece is a final class and cannot be extended. This adds safety to the design. I added the piece
interface and class to the project to provide complex functionality but made it separate from the
note list so that if future edits are made on the project it would be possible to extend the
basic INoteList in different ways.

Decisions for VIEWS:
Decided to make one interface so that all of the views would have the same functionality in the main
method. Created a MusicViewCreator to allow for views to be returned based on the desired type of
view.

Each view utilizes a view model (functionally described in IViewPiece) that provides all of the
functions a view would need to do its job without the functions the view would not ever need. The
views have this viewPiece as a private field that is sent to the constructor.

IViewPiece provides these functions to the views:
    -List<INote> getNotes();
    -List<INote> getNotesInBeat(final int beat);
    -int getLastBeat();
    -Map<Integer, List<INote>> getConsolidationMap();
    -List<Pair<Octave, Pitch>> getToneRange();
    -String musicOutput();
    -int getMeasure();
    -int getBeat();
    -int getTempo();

Utility decisions:
We decided to implement the ICompositionBuilder with a CompositionBuilder class that would return an
IPiece because the IPiece gives all of the functionality to provide small and complex operations on
a piece of music during creation. This IPiece could then be used to make a IViewPiece to be used by
the views.

We decided to test our ConsoleView and MidiViewImpl by providing convenience constructors and
mocking classes.
In the console view we decided to use an appendable that could be sent an Appendable object to give
system output during production runs and could be sent an Appendable with a StringBuffer in testing.
In the MidiViewImpl we sent mock classes for a Synthesizer and Receiver. In the Mock synth we
decided to use overridden functions for getReceiver (to return our mock reciever) and
getMicrosecondPosition for testing. The receiver had a private StringBuilder that was updated with
information from the MidiMessage and StartTime whenever the receiver.send() function was used. At
the end of the test we were then able to compare the log from the string builder to what we would
expect that song to give us.

To make the program runnable we used string arguments to the main function to choose a text file to
run and to choose which view to create using our MusicViewCreator. This was then usable on the
command line by running java -jar OOD_hw6.jav "<filename>" "<console OR visual OR midi>".

We updated our models from homework 5 to include a tempo member variable in the piece class and to
provide the consolidation map mentioned above. We also decided to add the instrument and volume
fields to the note model to better represent notes in real life. Finally, in our music piece model
we added a measure variable so songs could have different timing like in real life and we added a
current beat variable so that songs could be played in real time in the future (visual and midi
views together).

All provided methods have been tested thoroughly.

Best Regards,
Ben Dunbar & Sam Letcher
