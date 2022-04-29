package process;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

class TheBookProcessorTest {
    public static final String INTRO_BACKUP = "src/main/resources/book/mwvb-intro-backup.json";
    public static final String UNITS_BACKUP = "src/main/resources/book/mwvb-units-backup.json";

    @Test
    public void book2Json() throws IOException {
        TheBookProcessor processor = TheBookProcessor.getInstance(new Book2Json());
        processor.processIntro();
        processor.processUnits();

        assert noDiff(INTRO_BACKUP, TheBookProcessor.WMVB_FILE_INTRO);
        assert noDiff(UNITS_BACKUP, TheBookProcessor.WMVB_FILE_UNITS);
    }

    private boolean noDiff(String original, String revised) throws IOException {
        String oriSha = DigestUtils.sha256Hex(new FileInputStream(original));
        String revSha = DigestUtils.sha256Hex(new FileInputStream(revised));
        return oriSha.equals(revSha);
    }
}