package hundun.gdxgame.libv3.demo.save;

import hundun.gdxgame.libv3.gamelib.starter.save.IRootSaveExtension;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RootSaveData {
    GameplaySaveData gameplaySave;
    SystemSettingSaveData systemSettingSaveData;

    public static final class RootSaveExtension implements IRootSaveExtension<RootSaveData, SystemSettingSaveData, GameplaySaveData> {

        public static final RootSaveExtension INSTANCE = new RootSaveExtension();

        @Override
        public SystemSettingSaveData getSystemSave(RootSaveData rootSaveData) {
            return rootSaveData.getSystemSettingSaveData();
        }

        @Override
        public GameplaySaveData getGameplaySave(RootSaveData rootSaveData) {
            return rootSaveData.getGameplaySave();
        }

        @Override
        public RootSaveData newRootSave(GameplaySaveData gameplaySave, SystemSettingSaveData systemSettingSave) {
            return new RootSaveData(gameplaySave, systemSettingSave);
        }

        @Override
        public GameplaySaveData newGameplaySave() {
            return new GameplaySaveData();
        }

        @Override
        public SystemSettingSaveData newSystemSave() {
            return new SystemSettingSaveData();
        }

    }
}
