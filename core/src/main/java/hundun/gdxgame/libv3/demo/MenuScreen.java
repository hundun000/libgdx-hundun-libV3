package hundun.gdxgame.libv3.demo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import hundun.gdxgame.libv3.corelib.base.BaseHundunScreen;
import hundun.gdxgame.libv3.corelib.base.util.DrawableFactory;
import hundun.gdxgame.libv3.demo.save.RootSaveData;
import org.jetbrains.annotations.Nullable;

public class MenuScreen extends BaseHundunScreen<LibDemoGame, RootSaveData> {

    public MenuScreen(LibDemoGame game) {
        super(game);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void onLogicFrame() {

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(uiStage);

        Image image = new Image(DrawableFactory.getViewportBasedAlphaBoard(game.getMainViewportWidth(), game.getMainViewportHeight()));
        uiStage.addActor(image);

        Window window = new Window("Example screen", game.getMainSkin(), "border");
        window.defaults().pad(4f);
        window.add("This is a simple Scene2D view.").row();
        final TextButton button = new TextButton("Click me!", game.getMainSkin());
        button.pad(8f);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                button.setText("Clicked.");
            }
        });
        window.add(button);
        window.pack();
        // We round the window position to avoid awkward half-pixel artifacts.
        // Casting using (int) would also work.
        window.setPosition(MathUtils.roundPositive(uiStage.getWidth() / 2f - window.getWidth() / 2f),
            MathUtils.roundPositive(uiStage.getHeight() / 2f - window.getHeight() / 2f));
        window.addAction(Actions.sequence(Actions.alpha(0f), Actions.fadeIn(1f)));
        uiStage.addActor(window);
    }


}
