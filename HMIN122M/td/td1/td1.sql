DROP TABLE keyword;
DROP TABLE tag;
DROP TABLE commentaire;
DROP TABLE discussion;
DROP TABLE album;
DROP TABLE galerie;
DROP TABLE config;
DROP TABLE appareil;
DROP TABLE photographie;
DROP TABLE utilisateur;

CREATE TABLE utilisateur  (
    idUser NUMBER,
    CONSTRAINT PK_IDUSER PRIMARY KEY (idUser)
);

CREATE TABLE photographie (
    idPhoto NUMBER,
    licence VARCHAR(25),
    datePublication DATE,
    nbLike NUMBER,
    idUser NUMBER,
    CONSTRAINT PK_IDPHOTO PRIMARY KEY (idPhoto),
    CONSTRAINT FK_PHOTO_IDUSER FOREIGN KEY (idUser) REFERENCES utilisateur (idUser)
);

CREATE TABLE appareil (
    idPhoto NUMBER,
    datePrise DATE,
    coordonnee VARCHAR(20),
    CONSTRAINT PK_APPAREIL PRIMARY KEY (idPhoto), 
    CONSTRAINT FK_APPAREIL FOREIGN KEY (idPhoto) REFERENCES photographie (idPhoto) 
);

CREATE TABLE config (
    idPhoto NUMBER,
    ouvertureFocal FLOAT(5),
    tempsExpo NUMBER,
    flashActive INT,
    distanceFocal FLOAT(5),
    CONSTRAINT FK_CONFIG FOREIGN KEY (idPhoto) REFERENCES appareil (idPhoto)
);

CREATE TABLE keyword (
    mot VARCHAR(20)
);

CREATE TABLE tag (
    mot VARCHAR(20)
);

CREATE TABLE album (
    idAlbum NUMBER,
    idPhoto NUMBER,
    CONSTRAINT PK_IDALBUM PRIMARY KEY (idAlbum),
    CONSTRAINT FK_ALBUM FOREIGN KEY (idPhoto) REFERENCES photographie (idPhoto)
);

CREATE TABLE galerie (
    idGalerie NUMBER,
    idPhoto NUMBER,
    CONSTRAINT PK_IDGALERIE PRIMARY KEY (idGalerie),
    CONSTRAINT FK_GALERIE FOREIGN KEY (idPhoto) REFERENCES photographie (idPhoto) 
);

CREATE TABLE discussion (
    idDisc NUMBER,
    idPhoto NUMBER,
    CONSTRAINT PK_IDDISCUSSION PRIMARY KEY (idDisc),
    CONSTRAINT FK_DISCUSSION FOREIGN KEY (idPhoto) REFERENCES photographie (idPhoto) 
);

CREATE TABLE commentaire (
    idCom NUMBER,
    idPhoto NUMBER,
    CONSTRAINT PK_IDCOMMENTAIRE PRIMARY KEY (idCom),
    CONSTRAINT FK_COMMENTAIRE FOREIGN KEY (idPhoto) REFERENCES photographie (idPhoto)
);

INSERT INTO utilisateur (idUser) VALUES (1);
INSERT INTO utilisateur (idUser) VALUES (2); 
INSERT INTO utilisateur (idUser) VALUES (3);

INSERT INTO photographie (idPhoto, licence, datePublication, nbLike, idUser) VALUES (1, 'TOUS_DROITS', DATE '2020-09-01', 10, 1);
INSERT INTO photographie (idPhoto, licence, datePublication, nbLike, idUser) VALUES (2, 'UTILISATION_COMMERCIAL', DATE '2020-09-02', 15, 2);
INSERT INTO photographie (idPhoto, licence, datePublication, nbLike, idUser) VALUES (3, 'TOUT_DROITS', DATE '2020-09-03', 50, 3);

INSERT INTO appareil (idPhoto, datePrise, coordonnee) VALUES (1, DATE '2020-05-25', '46.329, 35.298');
INSERT INTO appareil (idPhoto, datePrise, coordonnee) VALUES (2, DATE '2020-01-05', '44.168, 33.268');
INSERT INTO appareil (idPhoto, datePrise, coordonnee) VALUES (3, DATE '2020-03-12', '42.769, 32.988');

INSERT INTO config (idPhoto, ouvertureFocal, tempsExpo, flashActive, distanceFocal) VALUES (1, 3.5, 2, 1, 2.98);
INSERT INTO config (idPhoto, ouvertureFocal, tempsExpo, flashActive, distanceFocal) VALUES (2, 2.8, 1, 0, 1.75);
INSERT INTO config (idPhoto, ouvertureFocal, tempsExpo, flashActive, distanceFocal) VALUES (3, 1.7, 1, 1, 3.02);