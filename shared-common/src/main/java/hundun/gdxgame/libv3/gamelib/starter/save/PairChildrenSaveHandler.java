package hundun.gdxgame.libv3.gamelib.starter.save;

import java.util.ArrayList;
import java.util.List;

import hundun.gdxgame.libv3.gamelib.base.IFrontend;
import hundun.gdxgame.libv3.gamelib.base.save.AbstractSaveHandler;
import hundun.gdxgame.libv3.gamelib.base.save.ISaveTool;

/**
 *
 */
public abstract class PairChildrenSaveHandler<T_ROOT_SAVE, T_SYSTEM_SAVE, T_GAMEPLAY_SAVE>
        extends AbstractSaveHandler<T_ROOT_SAVE> {

    private boolean gameSaveDirty = false;
    T_ROOT_SAVE starterRootSaveDataCache;
    T_ROOT_SAVE fileRootSaveDataCache;
    boolean hasContinuedGameplaySave;
    private final List<ISubGameplaySaveHandler<T_GAMEPLAY_SAVE>> subGameplaySaveHandlers = new ArrayList<>();
    private final List<ISubSystemSettingSaveHandler<T_SYSTEM_SAVE>> subSystemSettingSaveHandlers = new ArrayList<>();
    private final IRootSaveExtension<T_ROOT_SAVE, T_SYSTEM_SAVE, T_GAMEPLAY_SAVE> rootSaveExtension;


    public PairChildrenSaveHandler(
            IFrontend frontend,
            IRootSaveExtension<T_ROOT_SAVE, T_SYSTEM_SAVE, T_GAMEPLAY_SAVE> factory,
            ISaveTool<T_ROOT_SAVE> saveTool
            ) {
        super(frontend, saveTool);
        this.rootSaveExtension = factory;

    }

    @Override
    public void lazyInitOnGameCreate() {
        super.lazyInitOnGameCreate();
        if (saveTool.hasRootSave()) {
            fileRootSaveDataCache = saveTool.readRootSaveData();
        } else {
            fileRootSaveDataCache = null;
        }
        this.starterRootSaveDataCache = this.generateStarterRootSaveData();
        this.hasContinuedGameplaySave = fileRootSaveDataCache != null && rootSaveExtension.getGameplaySave(fileRootSaveDataCache) != null;
    }

    @Override
    public void systemSettingLoadOrStarter() {

        T_SYSTEM_SAVE systemSave;
        if (saveTool.hasRootSave() && fileRootSaveDataCache != null) {
            systemSave = rootSaveExtension.getSystemSave(fileRootSaveDataCache);
        } else {
            systemSave = rootSaveExtension.getSystemSave(starterRootSaveDataCache);
        }

        if (systemSave != null) {
            subSystemSettingSaveHandlers.forEach(it -> it.applySystemSetting(systemSave));
        }
        frontend.log(this.getClass().getSimpleName(), "systemSettingLoadOrStarter call");
    }


    @Override
    public boolean hasContinuedGameplaySave() {
        return hasContinuedGameplaySave;
    }

    @Override
    public void gameplayLoadOrStarter(boolean continued) {
        gameSaveDirty = true;

        T_GAMEPLAY_SAVE gameplaySave;
        if (continued) {
            gameplaySave = rootSaveExtension.getGameplaySave(fileRootSaveDataCache);
        } else {
            gameplaySave = rootSaveExtension.getGameplaySave(starterRootSaveDataCache);
        }


        if (gameplaySave != null) {
            subGameplaySaveHandlers.forEach(it -> it.applyGameplaySaveData(gameplaySave));
        }
        frontend.log(this.getClass().getSimpleName(), continued ? "continued game done" : "starter game done");
    }



    @Override
    protected T_ROOT_SAVE currentSituationToRootSaveData() {
        frontend.log(this.getClass().getSimpleName(), "currentSituationToRootSaveData by gameSaveDirty = " + gameSaveDirty);
        T_GAMEPLAY_SAVE gameplaySave;
        if (gameSaveDirty) {
            gameplaySave = rootSaveExtension.newGameplaySave();
            subGameplaySaveHandlers.forEach(it -> it.currentSituationToGameplaySaveData(gameplaySave));
        } else {
            if (hasContinuedGameplaySave) {
                gameplaySave = rootSaveExtension.getGameplaySave(fileRootSaveDataCache);
            } else {
                gameplaySave = null;
            }
        }

        T_SYSTEM_SAVE systemSettingSave;
        systemSettingSave = rootSaveExtension.newSystemSave();
        subSystemSettingSaveHandlers.forEach(it -> it.currentSituationToSystemSetting(systemSettingSave));

        return rootSaveExtension.newRootSave(
                gameplaySave,
                systemSettingSave
                );
    }

    /**
     * 读档发生时，将读出的`T_GAMEPLAY_SAVE`交给此Handler使用；
     * 存档发生时，要求此Handler修改`T_GAMEPLAY_SAVE`作为存档数据；
     */
    public interface ISubGameplaySaveHandler<T_GAMEPLAY_SAVE> {
        void applyGameplaySaveData(T_GAMEPLAY_SAVE gameplaySave);
        void currentSituationToGameplaySaveData(T_GAMEPLAY_SAVE gameplaySave);
    }

    /**
     * 读档发生时，将读出的`T_SYSTEM_SAVE`交给此Handler使用；
     * 存档发生时，要求此Handler修改`T_SYSTEM_SAVE`作为存档数据；
     */
    public interface ISubSystemSettingSaveHandler<T_SYSTEM_SAVE> {
        void applySystemSetting(T_SYSTEM_SAVE systemSettingSave);
        void currentSituationToSystemSetting(T_SYSTEM_SAVE systemSettingSave);
    }

    /**
     * 注册后才能在读档/存档时被调用；
     */
    @SuppressWarnings("unchecked")
    @Override
    public void registerSubHandler(Object object) {
        if (object instanceof ISubGameplaySaveHandler) {
            subGameplaySaveHandlers.add((ISubGameplaySaveHandler<T_GAMEPLAY_SAVE>)object);
            frontend.log(this.getClass().getSimpleName(), object.getClass().getSimpleName() + " register as " + ISubGameplaySaveHandler.class.getSimpleName());
        }
        if (object instanceof ISubSystemSettingSaveHandler) {
            subSystemSettingSaveHandlers.add((ISubSystemSettingSaveHandler<T_SYSTEM_SAVE>)object);
            frontend.log(this.getClass().getSimpleName(), object.getClass().getSimpleName() + " register as " + ISubSystemSettingSaveHandler.class.getSimpleName());
        }
    }
}
