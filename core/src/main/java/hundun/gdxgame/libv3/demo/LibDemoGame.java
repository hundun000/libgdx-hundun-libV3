package hundun.gdxgame.libv3.demo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import hundun.gdxgame.libv3.corelib.base.BaseHundunGame;
import hundun.gdxgame.libv3.corelib.base.util.DrawableFactory;
import hundun.gdxgame.libv3.corelib.gamelib.base.save.ISaveTool;
import hundun.gdxgame.libv3.corelib.gamelib.starter.listerner.ILogicFrameListener;
import hundun.gdxgame.libv3.demo.save.RootSaveData;
import hundun.gdxgame.libv3.demo.save.SaveHandler;


public class LibDemoGame extends BaseHundunGame<RootSaveData> {

    MenuScreen menuScreen;

    public LibDemoGame(ISaveTool<RootSaveData> saveTool) {
        super(GameArg.DEFAULT);
        this.saveHandler = new SaveHandler(this.getFrontend(), saveTool);
    }

    @Override
    protected void createAfterGdxStatic() {
        super.createAfterGdxStatic();
    }

    @Override
    protected void createBody() {
        this.menuScreen = new MenuScreen(this);
    }

    @Override
    protected void createFinally() {
        screenManager.pushScreen(menuScreen, null);
    }

    @Override
    protected void onLogicFrame(ILogicFrameListener source) {
        source.onLogicFrame();
    }
}
