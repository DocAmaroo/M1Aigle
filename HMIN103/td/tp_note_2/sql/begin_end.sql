DROP TABLE IF EXISTS node
DROP TABLE IF EXISTS dewey

CREATE TABLE node(
    begin INT PRIMARY KEY,
    end INT,
    parent INT,
    tag VARCHAR(30),
    type VARCHAR(10),
    CONSTRAINT FK_PARENT FOREIGN KEY (parent) REFERENCES node(begin)
);

CREATE TABLE dewey
(
    node NUMBER(10),
    tag VARCHAR(30),
    type VARCHAR(10),
);
