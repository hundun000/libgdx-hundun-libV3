package hundun.gdxgame.libv3.corelib.gamelib.base;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

/**
 * @author hundun
 * Created on 2022/08/31
 */
public class LogicFrameHelper {
    @Nullable
    @Getter
    private final Integer logicFramePerSecond;
    @Nullable
    @Getter
    private final Float logicFrameLength;
    @Getter
    private int clockCount = 0;
    private float logicFrameAccumulator;

    @Getter
    @Setter
    private boolean logicFramePause;
    @Getter
    @Setter
    private float scale;
    public LogicFrameHelper(@Nullable Integer logicFramePerSecond) {
        this.logicFramePerSecond = logicFramePerSecond;
        this.logicFrameLength = logicFramePerSecond != null ? 1f / logicFramePerSecond : null;
        this.scale = 1.0f;
    }

    public boolean logicFrameCheck(float delta) {
        logicFrameAccumulator += delta;
        if (logicFrameLength != null && logicFrameAccumulator >= logicFrameLength) {
            logicFrameAccumulator -= logicFrameLength;
            if (!logicFramePause) {
                clockCount++;
                return true;
            }
        }
        return false;
    }

    public double frameNumToSecond(int frameNum) {
        return logicFrameLength == null ? 0 : frameNum * logicFrameLength / scale;
    }

    public int secondToFrameNum(double second) {
        return logicFramePerSecond == null ? 0 : (int) Math.round(logicFramePerSecond * second * scale);
    }
}
