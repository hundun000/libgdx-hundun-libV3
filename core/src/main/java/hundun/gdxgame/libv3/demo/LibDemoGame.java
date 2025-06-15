package hundun.gdxgame.libv3.demo;

import hundun.gdxgame.libv3.corelib.base.BaseHundunGame;
import hundun.gdxgame.libv3.gamelib.base.save.ISaveTool;
import hundun.gdxgame.libv3.gamelib.starter.listerner.ILogicFrameListener;
import hundun.gdxgame.libv3.demo.save.RootSaveData;
import hundun.gdxgame.libv3.demo.save.DemoSaveHandler;


public class LibDemoGame extends BaseHundunGame<RootSaveData> {

    MenuScreen menuScreen;

    public LibDemoGame(ISaveTool<RootSaveData> saveTool) {
        super(
            GameArg.DEFAULT
                .mainSkinFilePath("skins/FreetypeDemo/IdleMushroom.json")
                .freeTypeSkin(true)
                .build()
        );
        this.saveHandler = new DemoSaveHandler(this.getFrontend(), saveTool);
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
