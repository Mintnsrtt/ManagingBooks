package com.example.ManagingBooks.Repository;

import com.example.ManagingBooks.Entity.BookEntity;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

@RegisterBeanMapper(BookEntity.class)
public interface BookRepository {

    @SqlQuery("SELECT * FROM books WHERE LOWER(author) = LOWER(:author)")
    List<BookEntity> findByAuthor(@Bind("author") String author);

    @SqlUpdate("INSERT INTO books (title, author, published_date) VALUES (:title, :author, :publishedDate)")
    @GetGeneratedKeys
    long createBook(@BindBean BookEntity book);

}
