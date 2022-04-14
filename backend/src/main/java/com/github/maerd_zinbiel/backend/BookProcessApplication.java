package com.github.maerd_zinbiel.backend;

import com.github.maerd_zinbiel.backend.mwvb.parse.Book2Database;
import com.github.maerd_zinbiel.backend.mwvb.parse.Book2Json;
import com.github.maerd_zinbiel.backend.mwvb.parse.TheBookProcessor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;

@SpringBootApplication
@EnableTransactionManagement
public class BookProcessApplication {
    public static ConfigurableApplicationContext context;

    public static void book2Json() throws IOException {
        TheBookProcessor processor = TheBookProcessor.getInstance(new Book2Json());
        processor.processIntro();
        processor.processUnits();
    }


    public static void book2Database() throws IOException {
        Book2Database book2Database = new Book2Database();

        SqlSession session = context.getBean(SqlSession.class);
        book2Database.setSqlSession(session);

        TheBookProcessor processor = TheBookProcessor.getInstance(book2Database);
//        processor.processIntro();
        processor.processUnits();
    }

    public static void main(String[] args) throws IOException {
        context = SpringApplication.run(BookProcessApplication.class, args);
        book2Database();
    }
}
