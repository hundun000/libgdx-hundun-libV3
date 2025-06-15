package hundun.gdxgame.libv3.corelib.base.save;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import hundun.gdxgame.libv3.gamelib.base.save.ISaveTool;

/**
 * 使用libgdx实现ISaveTool，即使用Preferences;
 * 仍待实现：不同形态客户端需要不同的ObjectMapper实现。
 */
public abstract class AbstractLibgdxSaveTool<T> implements ISaveTool<T> {

    protected String preferencesName;
    protected Preferences preferences;
    protected static final String ROOT_KEY = "root";

    public AbstractLibgdxSaveTool(String preferencesName) {
        this.preferencesName = preferencesName;
    }

    @Override
    public boolean hasRootSave() {
        return preferences != null && preferences.contains(ROOT_KEY);
    }

    @Override
    public void lazyInitOnGameCreate() {
        this.preferences = Gdx.app.getPreferences(preferencesName);
    }

    protected abstract String serializeRootSaveData(T saveData);
    protected abstract T deserializeRootSaveData(String raw);

    @Override
    public void writeRootSaveData(T saveData) {
        try {
            preferences.putString(ROOT_KEY, serializeRootSaveData(saveData));
            preferences.flush();
            Gdx.app.log(getClass().getSimpleName(), "save() done");
        } catch (Exception e) {
            Gdx.app.error(getClass().getSimpleName(), "save() error", e);
        }
    }



    @Override
    public T readRootSaveData() {

        try {
            String date = preferences.getString(ROOT_KEY);
            T saveData = deserializeRootSaveData(date);
            return saveData;
        } catch (Exception e) {
            Gdx.app.error(getClass().getSimpleName(), "load() error, will clear.", e);
            preferences.clear();
            preferences.flush();
            return null;
        }

    }


}
