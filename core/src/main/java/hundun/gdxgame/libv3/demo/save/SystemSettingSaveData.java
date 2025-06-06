package hundun.gdxgame.libv3.demo.save;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SystemSettingSaveData {
    Language language;

    public enum Language {
        CN,
        EN,
        ;
    }
}
