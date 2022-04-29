package process;
import entity.TheIntro;
import entity.Unit;

import java.io.IOException;

public interface BookWriter {
    void writeIntro(TheIntro intro) throws IOException;
    void writeUnit(Unit unit) throws IOException;
    void writeUnitsDone() throws IOException;
}
