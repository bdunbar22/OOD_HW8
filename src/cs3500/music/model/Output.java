package cs3500.music.model;

/**
 * Allow for given outputs for playing state to be displayed.
 */
public enum Output {
    START  ("  X  "),
    PLAYING("  |  "),
    REST   ("     ");

    private final String output;

    private Output(final String output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return output;
    }
}
