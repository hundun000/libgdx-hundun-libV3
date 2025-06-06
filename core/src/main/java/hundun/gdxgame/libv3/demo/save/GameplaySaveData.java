package hundun.gdxgame.libv3.demo.save;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GameplaySaveData {
    Map<String, Long> ownResources;
}
