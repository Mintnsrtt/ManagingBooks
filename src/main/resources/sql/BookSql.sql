CREATE DATABASE IF NOT EXISTS booksdb;
USE booksdb;

CREATE TABLE IF NOT EXISTS books (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    published_date DATE NOT NULL,
    INDEX idx_author_lower ((LOWER(author))) -- index case-insensitive
);

INSERT INTO books (title, author, published_date) VALUES
('Book One', 'John Doe', '2023-01-01'),
('Book Two', 'Jane Smith', '2022-05-10'),
('Book Three', 'John Doe', '2021-07-15');
