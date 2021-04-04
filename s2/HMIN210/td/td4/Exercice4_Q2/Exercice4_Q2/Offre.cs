using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Exercice4_App
{
    public class Offre
    {
        private int id;
        private Chambre chambre;
        private DateTime dateDisponibilite;
        private double prix;
        private string imageURL;
        private bool disponible;

        public Offre() { }

        public Offre(int id, Chambre chambre, DateTime dateDisponibilite, double prix, string imageURL, bool disponible)
        {
            this.Id = id;
            this.Chambre = chambre;
            this.DateDisponibilite = dateDisponibilite;
            this.Prix = prix;
            this.ImageURL = imageURL;
            this.Disponible = disponible;
        }

        public int Id 
        { 
            get => id; 
            set => id = value; 
        }

        public Chambre Chambre 
        { 
            get => chambre; 
            set => chambre = value; 
        }

        public DateTime DateDisponibilite 
        { 
            get => dateDisponibilite; 
            set => dateDisponibilite = value; 
        }

        public double Prix 
        { 
            get => prix; 
            set => prix = value; 
        }

        public bool Disponible 
        { 
            get => disponible; 
            set => disponible = value; 
        }

        public string ImageURL 
        { 
            get => imageURL; 
            set => imageURL = value; 
        }
    }
}
