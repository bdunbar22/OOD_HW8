package cs3500.music.view;

import javax.sound.midi.*;
import java.util.List;

/**
 * Created by Ben on 3/23/16.
 */
public class MockSynthesizer implements Synthesizer {

  //Methods we use during testing
  @Override public Receiver getReceiver() {
    return new MockReceiver();
  }

  @Override public long getMicrosecondPosition() {
    return 1000000;
  }

  //Methods we just had to override....
  @Override public void close() {
    //Do nothing
  }

  @Override public int getMaxPolyphony() {
    //Do nothing
    return 0;
  }


  @Override public long getLatency() {
    //Do nothing
    return 0;
  }


  @Override public MidiChannel[] getChannels() {
    //Do nothing
    return null;
  }


  @Override public VoiceStatus[] getVoiceStatus() {
    //Do nothing
    return null;
  }


  @Override public boolean isSoundbankSupported(Soundbank soundbank) {
    //Do nothing
    return false;
  }


  @Override public boolean loadInstrument(Instrument instrument) {
    //Do nothing
    return false;
  }


  @Override public void unloadInstrument(Instrument instrument) {
    //Do nothing
  }


  @Override public boolean remapInstrument(Instrument from, Instrument to) {
    //Do nothing
    return false;
  }


  @Override public Soundbank getDefaultSoundbank() {
    //Do nothing
    return null;
  }


  @Override public Instrument[] getAvailableInstruments() {
    //Do nothing
    return null;
  }


  @Override public Instrument[] getLoadedInstruments() {
    //Do nothing
    return null;
  }


  @Override public boolean loadAllInstruments(Soundbank soundbank) {
    //Do nothing
    return false;
  }



  @Override public void unloadAllInstruments(Soundbank soundbank) {
    //Do nothing
  }


  @Override public boolean loadInstruments(Soundbank soundbank, Patch[] patchList) {
    //Do nothing
    return false;
  }

  @Override public void unloadInstruments(Soundbank soundbank, Patch[] patchList) {
    //Do nothing
  }

  @Override public Info getDeviceInfo() {
    //Do nothing
    return null;
  }


  @Override public void open() throws MidiUnavailableException {
    //Do nothing
  }


  @Override public boolean isOpen() {
    //Do nothing
    return false;
  }


  @Override public int getMaxReceivers() {
    //Do nothing
    return 0;
  }


  @Override public int getMaxTransmitters() {
    //Do nothing
    return 0;
  }


  @Override public List<Receiver> getReceivers() {
    //Do nothing
    return null;
  }


  @Override public Transmitter getTransmitter() throws MidiUnavailableException {
    //Do nothing
    return null;
  }


  @Override public List<Transmitter> getTransmitters() {
    //Do nothing
    return null;
  }
}
