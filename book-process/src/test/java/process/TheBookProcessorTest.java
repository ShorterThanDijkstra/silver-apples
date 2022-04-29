package process;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.*;
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

    @Test
    public void book2Database() throws IOException {
        InputStream stream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(stream);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        Book2Database book2Database = new Book2Database(sqlSession);

        TheBookProcessor processor = TheBookProcessor.getInstance(book2Database);
        processor.processIntro();
        processor.processUnits();
    }

    private boolean noDiff(String original, String revised) throws IOException {
        String oriSha = DigestUtils.sha256Hex(new FileInputStream(original));
        String revSha = DigestUtils.sha256Hex(new FileInputStream(revised));
        return oriSha.equals(revSha);
    }
}