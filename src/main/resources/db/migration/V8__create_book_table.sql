CREATE  TABLE  book (
    Id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    Title VARCHAR(100) NOT NULL,
    Original_Title VARCHAR(100) NOT NULL,
    ISBN VARCHAR(30) NOT NULL,
    Edition VARCHAR(100) NOT NULL,
    Release_Date DATE NOT NULL,
    Edition_Date DATE NOT NULL,
    Price DECIMAL(10, 2) NOT NULL,
    Promotional_Price DECIMAL(10, 2),
    Is_Series BOOLEAN NOT NULL,
    Availability SMALLINT NOT NULL,
    Synopsis VARCHAR(1000) NOT NULL,
    Stock_Available INTEGER NOT NULL,
    Created_On TIMESTAMP NOT NULL,
    Updated_On TIMESTAMP  NULL,
    Publisher_Id BIGINT NOT NULL,
    FOREIGN KEY (Publisher_Id) REFERENCES publisher(Id)
);


CREATE INDEX idx_book_title ON book(Original_Title);
CREATE INDEX idx_book_ISBN ON book(ISBN);
