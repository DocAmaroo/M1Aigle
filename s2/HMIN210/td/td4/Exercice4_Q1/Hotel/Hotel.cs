using System;
using System.Collections.Generic;
using System.Text;

namespace Hotel
{
    class Hotel
    {
        private int id;
        private String nom;
        private int nbEtoile;
        private String pays;
        private String ville;
        private String rue;
        private int numero;
        private String positionGPS;
        private List<Chambre> chambres;

        public Hotel(int id, string nom, string pays, string ville, string rue, int numero, string positionGPS, List<Chambre> chambres, int nbEtoile)
        {
            this.Id = id;
            this.Nom = nom;
            this.Pays = pays;
            this.Ville = ville;
            this.Rue = rue;
            this.Numero = numero;
            this.PositionGPS = positionGPS;
            this.Chambres = chambres;
            this.NbEtoile = nbEtoile;
        }

        public int Id 
        {
            get => id;
            set => id = value;
        }

        public string Nom 
        {
            get => nom;
            set => nom = value;
        }
        public string Pays
        {
            get => pays;
            set => pays = value;
        }

        public string Ville
        {
            get => ville;
            set => ville = value;
        }

        public string Rue
        {
            get => rue;
            set => rue = value;
        }

        public int Numero
        {
            get => numero;
            set => numero = value;
        }

        public string PositionGPS
        {
            get => positionGPS;
            set => positionGPS = value;
        }

        public int NbEtoile 
        {
            get => nbEtoile;
            set => nbEtoile = value;
        }

        public List<Chambre> Chambres 
        {
            get => chambres;
            set => chambres = value;
        }

        public Chambre this[int id]
        {
            get => Chambres[id];
        }
    }
}
