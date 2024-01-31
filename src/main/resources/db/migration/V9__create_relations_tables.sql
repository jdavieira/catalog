CREATE TABLE BookAuthor (
    Book_Author_Id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    Book_Id BIGINT NOT NULL,
    Author_Id BIGINT NOT NULL,
    CONSTRAINT fk_book_id FOREIGN KEY(Book_Id) REFERENCES book(id),
    CONSTRAINT fk_author_id FOREIGN KEY(Author_Id) REFERENCES author(id)
);

CREATE TABLE BookTag (
     Book_Tag_Id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
     Book_Id BIGINT NOT NULL,
     Tag_Id BIGINT NOT NULL,
     CONSTRAINT fk_book_id FOREIGN KEY(Book_Id) REFERENCES book(id),
     CONSTRAINT fk_tag_id FOREIGN KEY(Tag_Id) REFERENCES tag(id)
);

CREATE TABLE BookGenre (
      Book_Genre_Id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
      Book_Id BIGINT NOT NULL,
      Genre_Id BIGINT NOT NULL,
      CONSTRAINT fk_book_id FOREIGN KEY(Book_Id) REFERENCES book(id),
      CONSTRAINT fk_genre_id FOREIGN KEY(Genre_Id) REFERENCES Genre(id)
);

CREATE TABLE BookLanguage (
     Book_Language_Id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
     Book_Id BIGINT NOT NULL,
     Language_Id BIGINT NOT NULL,
     CONSTRAINT fk_book_id FOREIGN KEY(Book_Id) REFERENCES book(id),
     CONSTRAINT fk_language_id FOREIGN KEY(Language_Id) REFERENCES Language(id)
);

CREATE TABLE BookFormat (
      Book_Format_Id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
      Book_Id BIGINT NOT NULL,
      Format_Id BIGINT NOT NULL,
      CONSTRAINT fk_book_id FOREIGN KEY(Book_Id) REFERENCES book(id),
      CONSTRAINT fk_format_id FOREIGN KEY(Format_Id) REFERENCES Format(id)
);

CREATE TABLE BookPublisher (
        Book_Publisher_Id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
        Book_Id BIGINT NOT NULL,
        Publisher_Id BIGINT NOT NULL,
        CONSTRAINT fk_book_id FOREIGN KEY(Book_Id) REFERENCES book(id),
        CONSTRAINT fk_publisher_id FOREIGN KEY(Publisher_Id) REFERENCES Publisher(id)
);
