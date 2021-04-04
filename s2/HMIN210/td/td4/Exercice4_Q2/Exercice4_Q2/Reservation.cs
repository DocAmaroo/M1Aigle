using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Exercice4_App
{
    public class Reservation
    {
        private static int compteur = 0;
        private int id;
        private string dateArrivee;
        private string dateDepart;
        private int nbPersonne;
        private Client client;
        private Chambre chambre;


        public Reservation(String dateArrivee, String dateDepart, int nbPersonne, Client client, Chambre chambre)
        {
            compteur = compteur + 1;
            this.Id = compteur;
            this.DateArrivee = dateArrivee;
            this.DateDepart = dateDepart;
            this.NbPersonne = nbPersonne;
            this.Client = client;
            this.Chambre = chambre;
        }

        public int Id 
        { 
            get => id; 
            set => id = value; 
        }

        public string DateArrivee 
        { 
            get => dateArrivee; 
            set => dateArrivee = value; 
        }

        public string DateDepart 
        { 
            get => dateDepart; 
            set => dateDepart = value; 
        }

        public int NbPersonne 
        { 
            get => nbPersonne; 
            set => nbPersonne = value; 
        }

        public Client Client 
        { 
            get => client; 
            set => client = value; 
        }

        public Chambre Chambre 
        { 
            get => chambre; 
            set => chambre = value; 
        }

    }
}
