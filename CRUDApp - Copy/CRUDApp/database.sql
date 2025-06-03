DROP DATABASE IF EXISTS db_CRUD;
CREATE DATABASE db_CRUD;
USE db_CRUD;

-- drop table if exists book;
CREATE TABLE Books (
  id   bigint AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255)    NOT NULL,
  author VARCHAR(255)   NOT NULL
);

INSERT INTO Books (title, author) VALUES 
  ('1984', 'George Orwell'),
  ('Brave New World', 'Aldous Huxley'),
  ('Harry Potter', 'JK Rowling'),
  ('The Hobbit', 'J.R.R. Tolkien'),
  ('To Kill a Mockingbird', 'Harper Lee'),
  ('The Great Gatsby', 'F. Scott Fitzgerald'),
  ('Moby-Dick', 'Herman Melville'),
  ('No Longer Human', 'Osamu Dazai'),
  ('Crime and Punishment', 'Fyodor Dostoevsky');


