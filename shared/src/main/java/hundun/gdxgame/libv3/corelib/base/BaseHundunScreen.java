package hundun.gdxgame.libv3.corelib.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.crashinvaders.vfx.VfxManager;
import de.eskalon.commons.screen.ManagedScreen;
import hundun.gdxgame.libv3.corelib.gamelib.starter.listerner.ILogicFrameListener;
import lombok.*;
import org.jetbrains.annotations.Nullable;


/**
 * @author hundun
 * Created on 2021/11/02
 * @param <T_GAME>
 * @param <T_SAVE>
 */
public abstract class BaseHundunScreen<T_GAME extends BaseHundunGame<T_SAVE>, T_SAVE> extends ManagedScreen implements ILogicFrameListener {
    @Getter
    protected final T_GAME game;
    protected Stage uiStage;


    // ------ lazy init ------

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ScreenArg {
        boolean fitViewport;

        public static ScreenArg DEFAULT = ScreenArg.builder()
            .build();
    }
    public BaseHundunScreen(T_GAME game) {
        this(game, ScreenArg.DEFAULT);
    }
    public BaseHundunScreen(T_GAME game, ScreenArg arg) {
        this.game = game;
        baseInit(arg);
    }

    protected void baseInit(ScreenArg arg) {
        this.uiStage = new Stage(new FitViewport(game.getMainViewportWidth(), game.getMainViewportHeight()));

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f, 0f, 0f, 1f);

        game.clockDelta(delta, this);

        uiStage.getViewport().apply();

        uiStage.act();

        // ====== be careful of draw order ======



        // ------ only backUi and UI use vfx ------

        belowUiStageDraw(delta);
        uiStage.draw();
        aboveUiStageDraw(delta);



        // ------ popupUi out of vfx ------
        renderPopupAnimations(delta);
    }

    protected void belowUiStageDraw(float delta) {
        // base-class do nothing
    }

    protected void aboveUiStageDraw(float delta) {
        // base-class do nothing
    }

    protected void renderPopupAnimations(float delta) {
        // base-class do nothing
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void resize(int width, int height) {
        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
        if(width <= 0 || height <= 0) return;
//        Gdx.app.log(this.getClass().getSimpleName(), JavaFeatureForGwt.stringFormat(
//                "resize by width = %s, height = %s",
//                width,
//                height
//                ));
        this.uiStage.getViewport().update(width, height, true);
    }
}
