package com.example.ManagingBooks.Config;

import com.example.ManagingBooks.Repository.BookRepository;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;

@Configuration
public class JdbiConfig {

    private final DataSource dataSource;

    public JdbiConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public Jdbi jdbi() {
        Jdbi jdbi = Jdbi.create(new TransactionAwareDataSourceProxy(dataSource));
        jdbi.installPlugin(new SqlObjectPlugin());
        return jdbi;
    }

    @Bean
    public BookRepository bookRepository(Jdbi jdbi) {
        return jdbi.onDemand(BookRepository.class);
    }
}