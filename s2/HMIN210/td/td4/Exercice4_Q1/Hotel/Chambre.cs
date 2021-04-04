using System;
using System.Collections.Generic;
using System.Text;

namespace Hotel
{
    class Chambre
    {
        private int id;
        private int numChambre;
        private int nbLit;
        private double prix;
        private int nbPersonneMax;

        public Chambre(int id, int numChambre, int nbLit, double prix, int nbPersonneMax)
        {
            this.Id = id;
            this.NumChambre = numChambre;
            this.NbLit = nbLit;
            this.Prix = prix;
            this.NbPersonneMax = nbPersonneMax;
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

        public int NbLit 
        { 
            get => nbLit; 
            set => nbLit = value; 
        }

        public double Prix 
        { 
            get => prix; 
            set => prix = value; 
        }

        public int NbPersonneMax 
        { 
            get => nbPersonneMax; 
            set => nbPersonneMax = value; 
        }


        public String display()
        {
            return "Chambre n°" + NumChambre + "\nNombre de Lit : " + NbLit + "\nPrix: " + Prix + "euros/nuit";
        }
    }
}
