using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Exercice4_App
{
    public class Hotel
    {
        private static int compteur = 0;

        private int id;
        private string nom;
        private string pays;
        private string ville;
        private string rue;
        private string lieuDit;
        private string positionGPS;
        private int nbEtoile;
        private List<Chambre> chambres;
        private List<Agence> agencesPartenaire;


        public Hotel(string nom, string pays, string ville, string rue, string lieuDit, string positionGPS, int nbEtoile, List<Chambre> chambres, List<Agence> agencesPartenaire)
        {
            compteur = compteur + 1;
            this.Id = compteur;
            this.Nom = nom;
            this.Pays = pays;
            this.Ville = ville;
            this.Rue = rue;
            this.lieuDit = lieuDit;
            this.PositionGPS = positionGPS;
            this.NbEtoile = nbEtoile;
            this.Chambres = chambres;
            this.agencesPartenaire = agencesPartenaire;
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

        public string LieuDit
        {
            get => lieuDit;
            set => lieuDit = value;
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

        public List<Agence> AgencesPartenaire
        {
            get => agencesPartenaire;
            set => agencesPartenaire = value;
        }
    }
}
