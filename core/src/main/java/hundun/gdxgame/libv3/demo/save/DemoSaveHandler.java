package hundun.gdxgame.libv3.demo.save;

import hundun.gdxgame.libv3.gamelib.base.IFrontend;
import hundun.gdxgame.libv3.gamelib.base.save.ISaveTool;
import hundun.gdxgame.libv3.gamelib.starter.save.PairChildrenSaveHandler;
import hundun.gdxgame.libv3.demo.constant.ResourceType;
import hundun.gdxgame.libv3.demo.save.SystemSettingSaveData.Language;

import java.util.*;

public class DemoSaveHandler extends PairChildrenSaveHandler<RootSaveData, SystemSettingSaveData, GameplaySaveData> {

    static final String SINGLETON = "SINGLETON";

    int worldSize = 8;
    double treeTileRate = 0.25;

    public DemoSaveHandler(IFrontend frontEnd, ISaveTool<RootSaveData> saveTool) {
        super(frontEnd, RootSaveData.RootSaveExtension.INSTANCE, saveTool);

    }

    @Override
    protected RootSaveData generateStarterRootSaveData() {

        Map<String, Long> ownResources = new HashMap<>();
        ownResources.put(ResourceType.MUSHROOM, 200L);
        ownResources.put(ResourceType.DNA_POINT, 0L);

        return RootSaveData.builder()
            .gameplaySave(GameplaySaveData.builder()
                .ownResources(ownResources)
                .build())
            .systemSettingSaveData(SystemSettingSaveData.builder()
                .language(Language.EN)
                .build())
            .build();
    }
}
