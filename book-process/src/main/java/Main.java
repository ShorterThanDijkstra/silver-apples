import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import process.Book2Database;
import process.Book2Json;
import process.TheBookProcessor;

import java.io.IOException;
import java.io.InputStream;

public class Main {
    private static void book2Json() throws IOException {
        TheBookProcessor processor = TheBookProcessor.getInstance(new Book2Json());
        processor.processIntro();
        processor.processUnits();
    }

    private static void book2Database() throws IOException {
        InputStream stream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(stream);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        Book2Database book2Database = new Book2Database(sqlSession);

        TheBookProcessor processor = TheBookProcessor.getInstance(book2Database);
        processor.processIntro();
        processor.processUnits();
    }

    public static void main(String[] args) throws IOException {
        // run book2Json() or book2Database()
        book2Database();
    }
}
