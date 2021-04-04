using System;
using System.Collections.Generic;
using System.Text;

namespace Hotel
{
    class Reservation
    {
        private int id;
        private String dateArrive;
        private String dateDepart;
        private double prix;
        private int nbPersonne;
        private int idClient;
        private int idChambre;

        public Reservation(int id, String dateArrive, String dateDepart, double prix, int nbPersonne)
        {
            this.Id = id;
            this.dateArrive = dateArrive;
            this.dateDepart = dateDepart;
            this.Prix = prix;
            this.NbPersonne = nbPersonne;
        }

        public int Id 
        { 
            get => id; 
            set => id = value; 
        }

        public String DateArrive 
        { 
            get => dateArrive; 
            set => dateArrive = value; 
        }

        public String DateDepart 
        { 
            get => dateDepart; 
            set => dateDepart = value; 
        }

        public double Prix 
        { 
            get => prix; 
            set => prix = value; 
        }

        public int NbPersonne 
        { 
            get => nbPersonne; 
            set => nbPersonne = value; 
        }

    }
}
