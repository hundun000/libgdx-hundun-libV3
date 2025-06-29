package hundun.gdxgame.libv3.gamelib.base.save;

import hundun.gdxgame.libv3.gamelib.base.IFrontend;

/**
 * @author hundun
 * Created on 2022/09/09
 */
public abstract class AbstractSaveHandler<T_SAVE> {

    protected ISaveTool<T_SAVE> saveTool;
    protected IFrontend frontend;

    public abstract void systemSettingLoadOrStarter();
    public abstract void gameplayLoadOrStarter(boolean load);
    //protected abstract void applyRootSaveData(T_SAVE saveData);
    protected abstract T_SAVE currentSituationToRootSaveData();
    protected abstract T_SAVE generateStarterRootSaveData();
    public abstract boolean hasContinuedGameplaySave();
    public abstract void registerSubHandler(Object object);


    public AbstractSaveHandler(IFrontend frontend, ISaveTool<T_SAVE> saveTool) {
        this.saveTool = saveTool;
        this.frontend = frontend;
    }

    public void lazyInitOnGameCreate() {
        this.saveTool.lazyInitOnGameCreate();
    }

    public T_SAVE gameSaveCurrent() {
        frontend.log(this.getClass().getSimpleName(), "saveCurrent called");
        T_SAVE saveData = this.currentSituationToRootSaveData();
        saveTool.writeRootSaveData(saveData);
        return saveData;
    }

}
