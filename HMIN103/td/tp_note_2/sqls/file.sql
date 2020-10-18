DROP TABLE IF EXISTS batiment
DROP TABLE IF EXISTS batiment_etage
DROP TABLE IF EXISTS batiment_etage_description
DROP TABLE IF EXISTS batiment_etage_bureau
DROP TABLE IF EXISTS batiment_etage_bureau_code
DROP TABLE IF EXISTS batiment_etage_bureau_personne
DROP TABLE IF EXISTS batiment_etage_salle
DROP TABLE IF EXISTS batiment_etage_salle_nbplace

CREATE TABLE batiment (
    node int PRIMARY KEY,
    txtval varchar(255),
    numval int
)

CREATE TABLE batiment_etage (
    node int PRIMARY KEY,
    txtval varchar(255),
    numval int
)

CREATE TABLE batiment_etage_bureau (
    node int PRIMARY KEY,
    txtval varchar(255),
    numval int
)

CREATE TABLE batiment_etage_bureau_code (
    node int PRIMARY KEY,
    txtval varchar(255),
    numval int
)

CREATE TABLE batiment_etage_description (
    node int PRIMARY KEY,
    txtval varchar(255),
    numval int
)

CREATE TABLE batiment_etage_personne (
    node int PRIMARY KEY,
    txtval varchar(255),
    numval int
)

CREATE TABLE batiment_etage_salle (
    node int PRIMARY KEY,
    txtval varchar(255),
    numval int
)

CREATE TABLE batiment_etage_salle_nbplace (
    node int PRIMARY KEY,
    txtval varchar(255),
    numval int
)