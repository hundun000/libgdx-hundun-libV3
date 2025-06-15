package hundun.gdxgame.libv3.demo.lwjgl3;

import com.badlogic.gdx.Gdx;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import hundun.gdxgame.libv3.corelib.base.save.AbstractLibgdxSaveTool;
import hundun.gdxgame.libv3.demo.save.RootSaveData;


/**
 * @author hundun
 * Created on 2021/11/10
 */
public class PreferencesSaveTool extends AbstractLibgdxSaveTool<RootSaveData> {

    private final ObjectMapper objectMapper;

    public PreferencesSaveTool(String preferencesName) {
        super(preferencesName);
        this.objectMapper = new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT)
                ;
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    protected String serializeRootSaveData(RootSaveData saveData) {
        try {
            return objectMapper.writeValueAsString(saveData);
        } catch (Exception e) {
            Gdx.app.error(getClass().getSimpleName(), "serializeRootSaveData() error", e);
            return null;
        }
    }

    @Override
    protected RootSaveData deserializeRootSaveData(String raw) {
        try {
            return objectMapper.readValue(raw, RootSaveData.class);
        } catch (Exception e) {
            Gdx.app.error(getClass().getSimpleName(), "deserializeRootSaveData() error", e);
            return null;
        }
    }

}
