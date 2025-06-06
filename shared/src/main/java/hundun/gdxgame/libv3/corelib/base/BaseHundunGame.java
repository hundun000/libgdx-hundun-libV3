package hundun.gdxgame.libv3.corelib.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import de.eskalon.commons.core.ManagedGame;
import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.transition.ScreenTransition;
import hundun.gdxgame.libv3.corelib.gamelib.base.LogicFrameHelper;
import hundun.gdxgame.libv3.corelib.gamelib.base.save.AbstractSaveHandler;
import hundun.gdxgame.libv3.corelib.gamelib.starter.listerner.ILogicFrameListener;
import lombok.*;
import org.jetbrains.annotations.Nullable;


public abstract class BaseHundunGame<T_SAVE> extends ManagedGame<ManagedScreen, ScreenTransition> {
    public boolean debugMode;
    @Getter
    protected final int mainViewportWidth;
    @Getter
    protected final int mainViewportHeight;
    @Nullable
    @Getter
    protected LogicFrameHelper logicFrameHelper;
    final GameArg gameArg;


    @Getter
    protected Skin mainSkin;

    @Getter
    protected final LibgdxFrontend frontend;
    // ------ init in createStage1(), or keep null ------
    @Getter
    protected AbstractSaveHandler<T_SAVE> saveHandler;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GameArg {
        int viewportWidth;
        int viewportHeight;
        @Nullable
        Integer logicFramePerSecond;
        @Nullable
        String mainSkinFilePath;

        public static GameArg DEFAULT = GameArg.builder()
            .viewportWidth(640)
            .viewportHeight(480)
            .build();
    }

    public BaseHundunGame(GameArg gameArg) {
        this.gameArg = gameArg;
        this.mainViewportWidth = gameArg.viewportWidth;
        this.mainViewportHeight = gameArg.viewportHeight;
        this.frontend = new LibgdxFrontend();
        this.logicFrameHelper = gameArg.logicFramePerSecond != null ? new LogicFrameHelper(gameArg.logicFramePerSecond) : null;
    }

    /**
     * 只依赖Gdx static的成员
     */
    protected void createAfterGdxStatic() {
        if (gameArg.mainSkinFilePath != null) {
            this.mainSkin = new Skin(Gdx.files.internal(gameArg.mainSkinFilePath));
        } else  {
            this.mainSkin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        }
        if (saveHandler != null) {
            saveHandler.lazyInitOnGameCreate();
        }
    }
    /**
     * 只依赖Stage1的成员
     */
    protected abstract void createBody();
    /**
     * 自由依赖
     */
    protected abstract void createFinally();

	@Override
	public void create() {
	    super.create();

	    createAfterGdxStatic();
        createBody();
        createFinally();
	}


    // ====== ====== ======



	@Override
	public void dispose () {

	}


    /**
     * 外部要求时间流逝。<br/>
     * 若是模拟运行，则调用者是模拟调度器；若是实际游戏，则调用者是某个Screen（激活状态的Screen从游戏引擎收到时间流逝delta）；
     */
    public void clockDelta(float delta, ILogicFrameListener source) {
        boolean isLogicFrame = this.logicFrameHelper != null && this.logicFrameHelper.logicFrameCheck(delta);
        if (isLogicFrame) {
            this.onLogicFrame(source);
        }
    }

    /**
     * 游戏逻辑帧到达。Game子类决定通知的实现（通知传递树）。一般一个子树是clockDelta的调用者source，故作为参数。
     */
    protected abstract void onLogicFrame(ILogicFrameListener source);
}
