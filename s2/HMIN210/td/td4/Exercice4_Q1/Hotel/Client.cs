
using System;
using System.Collections.Generic;
using System.Text;

namespace Hotel
{
    class Client
    {
        private int id;
        private string nom;
        private string prenom;
        private string dateNaissance;
        private string carteCredit;

        public Client(int id, string nom, string prenom, String dateNaissance, string carteCredit)
        {
            this.Id = id;
            this.Nom = nom;
            this.Prenom = prenom;
            this.DateNaissance = dateNaissance;
            this.CarteCreditClient = carteCredit;
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

        public string DateNaissance 
        { 
            get => dateNaissance; 
            set => dateNaissance = value; 
        }

        public string CarteCreditClient 
        { 
            get => carteCredit; 
            set => carteCredit = value; 
        }

    }
}
