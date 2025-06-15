package hundun.gdxgame.libv3.gamelib.starter.save;
/**
 * 约定T_ROOT_SAVE内分细分为T_SYSTEM_SAVE和T_GAMEPLAY_SAVE;
 * 并要求提供三者对于新游戏时初始状态的工厂方法。
 */
public interface IRootSaveExtension<T_ROOT_SAVE, T_SYSTEM_SAVE, T_GAMEPLAY_SAVE> {

    // delegate getter
    T_SYSTEM_SAVE getSystemSave(T_ROOT_SAVE rootSaveData);
    T_GAMEPLAY_SAVE getGameplaySave(T_ROOT_SAVE rootSaveData);

    // delegate constructor
    T_ROOT_SAVE newRootSave(T_GAMEPLAY_SAVE gameplaySave, T_SYSTEM_SAVE systemSettingSave);
    T_GAMEPLAY_SAVE newGameplaySave();
    T_SYSTEM_SAVE newSystemSave();

}
