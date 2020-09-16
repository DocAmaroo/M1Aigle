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
    licence VARCHAR2(15),
    datePublication DATE,
    nbLike NUMBER,
    idUser NUMBER,
    CONSTRAINT PK_IDPHOTO PRIMARY KEY (idPhoto),
    CONSTRAINT FK_PHOTO_IDUSER FOREIGN KEY (idUser) REFERENCES utilisateur (idUser)
);

CREATE TABLE appareil (
    idAppPhoto NUMBER,
    datePrise DATE,
    coordonnee VARCHAR(20),
    idPhoto NUMBER,
    CONSTRAINT PK_IDAPPPHOTO PRIMARY KEY (idAppPhoto),
    CONSTRAINT FK_APPPHOTO FOREIGN KEY (idPhoto) REFERENCES photographie (idPhoto) 
);

CREATE TABLE config (
    ouvertureFocal FLOAT(5),
    tempsExpo NUMBER,
    flashActive INT,
    distanceFocal FLOAT(5)
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