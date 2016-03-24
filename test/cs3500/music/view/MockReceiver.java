package cs3500.music.view;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

/**
 * Created by Ben on 3/23/16.
 */
public class MockReceiver implements Receiver{
    private StringBuilder stringToBuild;

    public MockReceiver() {
        this.stringToBuild = new StringBuilder();
        this.stringToBuild.append("Adding to Midi:\n");
    }

    @Override
    public void send(MidiMessage message, long startTime) {
        ShortMessage shortMessage = (ShortMessage) message;
        this.stringToBuild.append("Note:");

        this.stringToBuild.append(" Status=" + shortMessage.getCommand()); //Midi On off
        this.stringToBuild.append(" Instrument=" + shortMessage.getChannel()); //Instrument
        this.stringToBuild.append(" Pitch=" + shortMessage.getData1()); //Pitch
        this.stringToBuild.append(" Volume=" + shortMessage.getData2()); //Volume

        this.stringToBuild.append(" Time=" + String.valueOf(startTime) + "\n");
    }

    @Override
    public void close() {
        this.stringToBuild.append("Closed\n");
    }

    @Override
    public String toString() {
        return stringToBuild.toString();
    }
}
