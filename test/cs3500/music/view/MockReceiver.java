package cs3500.music.view;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

/**
 * Created by Ben on 3/23/16.
 */
public class MockReceiver implements Receiver{
    private StringBuilder stringToBuild;

    public MockReceiver() {
        this.stringToBuild = new StringBuilder();
    }

    @Override
    public void send(MidiMessage message, long startTime) {
        this.stringToBuild.append("Adding note to Midi: ");
        byte[] data = message.getMessage();
        this.stringToBuild.append(data.toString());
        this.stringToBuild.append(" " + String.valueOf(startTime) + "\n");
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
