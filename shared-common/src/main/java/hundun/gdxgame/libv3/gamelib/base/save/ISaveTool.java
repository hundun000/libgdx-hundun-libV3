package hundun.gdxgame.libv3.gamelib.base.save;

/**
 * 待游戏引擎实现的工具方法
 */
public interface ISaveTool<T_SAVE> {
    void lazyInitOnGameCreate();
    boolean hasRootSave();
    void writeRootSaveData(T_SAVE saveData);
    T_SAVE readRootSaveData();
}
