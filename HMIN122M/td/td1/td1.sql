CREATE TABLE `utilisateur` {
    idUser NUMBER,
    CONSTRAINT PK_IDUSER PRIMARY KEY (idUser)
}

CREATE TABLE `photographie` {
    idPhoto NUMBER,
    licence VARCHAR2(15),
    datePublication DATE,
    nbLike NUMBER,
    CONSTRAINT PK_IDPHOTO PRIMARY KEY (idPhoto),
    CONSTRAINT FK_PHOTO_IDUSER FOREIGN KEY (idUser) REFERENCES utilisateur (idUser)
}

CREATE TABLE `appareil_photo` {
    idAppPhoto NUMBER,
    datePrise DATE,
    coordonnee VARCHAR(20),
    CONSTRAINT PK_IDAPPPHOTO PRIMARY KEY (idAppPhoto),
    CONSTRAINT FK_APPPHOTO FOREIGN KEY (idUser) REFERENCES utilisateur (idUser) 
}

CREATE TABLE `config` {
    ouvertureFocal FLOAT(5),
    tempsExpo NUMBER,
    flashActive BOOLEAN,
    distanceFocal FLOAT(5),
}

CREATE TABLE `mot_clé` {
    mot VARCHAR(20)
}

CREATE TABLE `tag` {
    mot VARCHAR(20)
}

CREATE TABLE `album` {
    idAlbum INT,
    CONSTRAINT PK_IDALBUM PRIMARY KEY idAlbum,
    CONSTRAINT FK_ALBUM FOREIGN KEY (idPhoto) REFERENCES photographie (idPhoto)
}

CREATE TABLE `galerie` {
    idGalerie INT,
    CONSTRAINT PK_IDGALERIE PRIMARY KEY idGalerie,
    CONSTRAINT FK_GALERIE FOREIGN KEY (idPhoto) REFERENCES photographie (idPhoto) 
}

CREATE TABLE `discussion` {
    idDisc INT,
    CONSTRAINT PK_IDDISCUSSION PRIMARY KEY idDisc,
    CONSTRAINT FK_DISCUSSION FOREIGN KEY (idPhoto) REFERENCES photographie (idPhoto) 
}

CREATE TABLE `commentaire` {
    idCom INT,
    CONSTRAINT PK_IDCOMMENTAIRE PRIMARY KEY idCom,
    CONSTRAINT FK_COMMENTAIRE FOREIGN KEY (idPhoto) REFERENCES photographie (idPhoto)
}