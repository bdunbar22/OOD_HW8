KELSEY ENG AND THOMAS SHOCKMAN
MUSIC EDITOR - ASSIGNMENT 7
README.TXT
APRIL 6, 2016
----------------------------------------------------------------------------------------------------

Changes we made since homework 6:

We decided to keep our representation as List<List<Note>>, storing notes at every beat starting at
their startBeat and continuing up to their endBeat. We chose this representation because it
allows constant time access of all of the notes at a specified beat. We acknowledge that there is a
non-insignificant memory cost to this, but we feel it is more than made up for in the speed of
access of data. Additionally, we chose to use a List in the exterior, as opposed to a Map, to make
iterating over all notes easier. In the interior note container, we chose to use a List instead of a
Set, because using a List allows for more flexibility (adding multiple copies of same note for an
amplified sound).

We changed the way highestNote and lowestNote were retrieved from the MusicModelImpl. Instead of
iterating over all notes every time we wanted this information, we keep track of it as a field
which is updated every time a note is added or removed.

The grader said that we were leaking information through our Note implementation, so we decided to
hide it (NoteImpl class) behind a Note interface.
The grader also mentioned that our sound was a bit off, and we realized that it was happening
because we were off by 1 when interpreting the midi-instrument values.

We created additional view interfaces in addition to the MusicView interface: PlayableMusicViews,
which are views which have some concept of playing the music attached to them, and GuiMusicViews,
which are PlayableMusicViews, and also have some concept of displaying a view in GUI.

To increase functionality of the MusicView interface, the methods refresh and correspondTo were
added. Previously, we only had one method (render).

PlayableMusicViews have the following functionality: they can set the current beat, play and pause,
get the current beat, and determine whether the view is playing.

We added functionality to the gui view by allowing keyboard and mouselisteners to be added to
GuiMusicViews. GuiMusicViews also have the following functionality: specify the starting beat and
note to draw from, get these starting values, determine the note that exists at a certain
coordinate (if any), and create a new note that corresponds to a certain coordinate (if any).

We added a ModelFactory class and added more methods to the ViewFactory. The ModelFactory class
allows us to create a model, and instances of notes using different parameters. There are three
different views for the ViewFactory: a GuiMusicView, PlayableMusicView, and a MusicView. These
were added to hide implementation details of constructing these.

We created the new class GuiMidiView which is a GuiMusicView. Internally, this view has both a
GuiViewFrame and a MidiViewImpl, which it uses to display and play music respectively. In order
to sync the playback from the MidiViewImpl to the display of the GuiViewFrame, we start playing
the MidiViewImpl in a new Thread and update the progress line of the GuiViewFrame as long as the
MidiViewImpl is running within this thread. In order to get this method of control working
reliably, we had to make some of the methods in the MidiViewImpl class synchronized. We admit that
we don't fully understand Threads in Java, but we have a working and reliably tested way of
syncing the two views.

Finally, we created a controller to control GuiMusicViews. The class is quite simple, and all of
the complexity comes in creating the GuiMusicViewMouseHandler and configuring the
KeyboardHandler. How the keys and mouse interact is documented in full below.

HOW TO RUN: execute the MusicEditor.jar file with one command line argument, a file location to
read from.

How to use the Music Editor:

    Press the Enter key to start playback or pause playback of the music and view

    To Navigate Through the Editor:

        Use the arrow keys (left, right, up, and down) to scroll

        Press the Home key to jump to and view the beginning of the piece (starting at beat 0)
        Press the End key to jump to and view the ending of the piece (starting at the last beat)

        Use the P key to view the music editor from progress line
        Use the N key to move the progress line one beat to the left
        Use the M key to move the progress line one beat to the right
        Use the - key to move the progress line to the beginning of the piece (starting at beat 0)
        Use the = key to move the progress line to the end of the piece (starting at the last beat)


    To Modify Notes through the Music Editor:

    Adding new notes: Hold the Space Key, Select a position in the editor with mouse click to add a
    note with length of one. Release the Space Key after adding all necessary notes.

    Editing existing notes:
    (Multiple actions can happen consecutively for one click)
        Select a note with mouse click in the editor and move it around with:
            A key (moves the note over one beat to the left)
            D key (moves the note over one beat to the right)
            W key (moves the note over one pitch higher)
            S key (moves the note over one pitch lower)
        Select a note with mouse click in the editor and delete it by pressing the X key
        Select a note with mouse click and change its length by:
            , (<) key to make it one beat shorter. A beat cannot have a length less than one.
            . (>) key to make it one beat longer
    After finishing actions, press C key to clear the note selection