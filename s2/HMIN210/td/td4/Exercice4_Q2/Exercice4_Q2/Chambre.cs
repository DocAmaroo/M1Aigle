using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Exercice4_App
{
    public class Chambre
    {
        private static int id = 0;
        private int numChambre;
        private int nbLit;
        private int prix;
        private int nbPersonneMax;
        private string imageURL;

        public Chambre () { }

        public Chambre(int numChambre, int nbPersonneMax, int nbLit, int prix, string imageURL)
        {
            id = id + 1;
            this.NumChambre = numChambre;
            this.NbLit = nbLit;
            this.Prix = prix;
            this.NbPersonneMax = nbPersonneMax;
            this.ImageURL = imageURL;
        }

        public int Id 
        { 
            get => id; 
            set => id = value; 
        }

        public int NumChambre 
        { 
            get => numChambre; 
            set => numChambre = value; 
        }

        public int NbPersonneMax 
        { 
            get => nbPersonneMax; 
            set => nbPersonneMax = value; 
        }

        public int NbLit 
        { 
            get => nbLit; 
            set => nbLit = value; 
        }

        public int Prix 
        { 
            get => prix; 
            set => prix = value; 
        }

        public string ImageURL 
        { 
            get => imageURL; 
            set => imageURL = value; 
        }

    }
}
