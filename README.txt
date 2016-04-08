README

Working in collaboration: Ben Dunbar and Sam Letcher

Hello,

IMPORTANT!!!
Configured location of text files in relation to the JAR for our Project:
"../../../text/" + filename
filename should include .txt in the command line argument.

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
- Get Notes (copy of notes for security)
- Get Notes in Beat
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

Controller Info:
IController - controller interface
Controller - controller impl
KeyBoardHandler - implements KeyListener
MouseHandler - implements mouse listener
MouseHandlerHelper - Class similar to a Runnable except it offers specific functions to
interact with the controller the way that was required.

NEW INFORMATION:
Controller functionality!!!!!!!!!!
<h1>Functionality:</h1>
<p>Right click - delete a note</p>
<p>TOGGLE 'a', 'm', 'c', 'l' for operation modes.</p>
<p>A = Add (default)</p>
<p>M = Move </p>
<p>C = Copy </p>
<p>L = Location (user gets to enter a location outside of the song area)</p>
<i>Pressing the key will enter the operation mode. Pressing a different key will switch
modes. Starts at Add mode.</i>
<p>Press 'r' reverses the song.</p>
<p>Press 'b' to go back to the start</p>
<p>Press arrow keys to scroll</p>
<p>Press home and end to get to start and end of piece for viewing</p>
<p>Press 1 and 0 also goes to start and end of piece for viewing</p>
<p>Press 't' to update the tempo! Should do during pause. Have fun.</p>

Space starts and starts in composition view.

This uses a combination of key listening and mouse listening to create a music editor that is
useful and fun to use. You can reverse the song, add notes, move notes, copy notes and scroll
your view. What's not to love?

Our KeyboardHandler and MouseHandler use a great design practice by being outside of the
controller and getting configured by the controller to be added to the views.

We made our composite view a IGuiView and allowed IGuiViews the ability to have listeners. We
placed the location logic for the mouse in the panel view with functions the controller could
call. This way the controller was able to make edits to the model, but the gui view itself was
the only class that knew the actual nitty gritty details of the graphics and locations.

Our design changes from the previous assignment were mainly updates to the way the views worked
. The most notable of which was updateViewPiece() which allows the view to get a new view model
 and then has the view redisplay music with this new model. It was essential to create this to
 perform editing while the song was playing and the result is spectacular.

All provided methods have been tested thoroughly.

Best Regards,
Ben Dunbar & Sam Letcher
