DROP TABLE IF EXISTS node
DROP TABLE IF EXISTS dewey

CREATE TABLE node(
    begin INT,
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

-- remplissage Node
INSERT INTO node (begin, end, parent, tag, type) VALUES 
(1, 24, 1, "batiment", "ELT"),
(2, 23, 1, "etage", "ELT"),
(3, 6, 2, "description", "ELT"),
(4, 5, 3, "Rez de chaussée", "TXT"),
(7, 16, 2, "bureau", "ELT"),
(8, 11, 7, "code", "ELT"),
(9, 10, 8, "0", "TXT"),
(12, 15, 7, "personne", "ELT"),
(13, 14, 12, "Michel", "TXT"),
(17, 22, 2, "Salle", "ELT"),
(18, 21, 17, "nombrePlaces", "ELT"),
(19, 20, 18, "18", "TXT");


 -- remplissage Dewey
INSERT INTO dewey (node, tag, type) VALUES 
(0, "batiment", "ELT"),
(1, "etage", "ELT"),
(11, "description", "ELT"),
(111, "Rez de chaussée", "TXT"),
(12 "bureau", "ELT"),
(121, "code", "ELT"),
(1211, "0", "TXT"),
(122, "personne", "ELT"),
(1222, "Michel", "TXT"),
(13, "salle", "ELT"),
(131, "nombrePlaces", "ELT"),
(1311, "18", "TXT");
