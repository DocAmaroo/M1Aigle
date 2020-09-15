DROP TABLE IF EXISTS `keyword`;
DROP TABLE IF EXISTS `tag`;
DROP TABLE IF EXISTS `commentaire`;
DROP TABLE IF EXISTS `discussion`;
DROP TABLE IF EXISTS `album`;
DROP TABLE IF EXISTS `galerie`;
DROP TABLE IF EXISTS `configuration`;
DROP TABLE IF EXISTS `appareil`;
DROP TABLE IF EXISTS `utilisateur`;

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
    idUser NUMBER,
    CONSTRAINT PK_IDAPPPHOTO PRIMARY KEY (idAppPhoto),
    CONSTRAINT FK_APPPHOTO FOREIGN KEY (idUser) REFERENCES utilisateur (idUser) 
);

CREATE TABLE conf (
    ouvertureFocal FLOAT(5),
    tempsExpo NUMBER,
    flashActive BOOLEAN,
    distanceFocal FLOAT(5),
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