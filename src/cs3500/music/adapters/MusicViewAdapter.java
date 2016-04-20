package cs3500.music.adapters;

import cs3500.music.view.IMusicView;
import cs3500.music.view.IViewPiece;
import cs3500.music.viewGiven.MusicView;

/**
 * Adapts the provided music view to implement the functions provided by IMusicView so that the
 * current controller can use the provided views correctly.
 *
 * Created by Ben on 4/18/16.
 */
public class MusicViewAdapter implements IMusicView {
    private MusicView musicView;

    public MusicViewAdapter(MusicView musicView) {
        this.musicView = musicView;
    }

    /**
     * Use the given views render to implement view music.
     */
    @Override
    public void viewMusic() {
        this.musicView.render();
    }

    /**
     * Use the music view update and refresh to implement the update view piece function.
     *
     * @param viewPiece new model view to update with.
     */
    @Override
    public void updateViewPiece(IViewPiece viewPiece) {
        this.musicView.updateModel(new MusicModelImpl(viewPiece));
        this.musicView.refresh();
    }
}
