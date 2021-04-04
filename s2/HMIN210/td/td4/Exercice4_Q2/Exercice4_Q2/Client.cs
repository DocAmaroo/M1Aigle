using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Exercice4_App
{
    public class Client
    {
        private static int compteur = 0;

        private int id;
        private string nom;
        private string prenom;
        private string carteCredit;

        public Client() {
            compteur = compteur + 1;
            this.Id = compteur;
        }

        public Client(String nom, String prenom, String carteCredit)
        {
            compteur = compteur + 1;
            this.Id = compteur;
            this.Nom = nom;
            this.Prenom = prenom;
            this.CarteCredit = carteCredit;
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

        public string Prenom 
        { 
            get => prenom; 
            set => prenom = value; 
        }

        public string CarteCredit
        {
            get => this.carteCredit;
            set => this.carteCredit = value;
        }

        public string[] toArrString()
        {
            return new string[3] { this.Nom, this.Prenom, this.CarteCredit };
        }
    }
}
