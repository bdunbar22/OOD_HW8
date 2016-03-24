package cs3500.music.view;

import javax.sound.midi.Receiver;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.VoiceStatus;

/**
 * Created by Ben on 3/23/16.
 */
public class MockSynthesizer extends implements Synthesizer {
    @Override
    public VoiceStatus[]  getVoiceStatus() {
        return null;
    }

    @Override
    public Receiver getReceiver() {
        return new MockReceiver();
    }
}
