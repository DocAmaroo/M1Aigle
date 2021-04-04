using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace API.Models
{
    public class ChambreModel
    {

        public ChambreModel () { }
        public ChambreModel(int id, int numChambre, int nbPersonneMax, int nbLit, int prix, string imageURL, bool isDisponible)
        {
            this.Id = id;
            this.NumChambre = numChambre;
            this.NbLit = nbLit;
            this.Prix = prix;
            this.NbPersonneMax = nbPersonneMax;
            this.ImageURL = imageURL;
            this.IsDisponible = isDisponible;
            this.Reservation = new List<ReservationModel>();
        }
        public ChambreModel(int id, int numChambre, int nbPersonneMax, int nbLit, int prix, string imageURL, bool isDisponible, List<ReservationModel> reservation)
        {
            this.Id = id;
            this.NumChambre = numChambre;
            this.NbLit = nbLit;
            this.Prix = prix;
            this.NbPersonneMax = nbPersonneMax;
            this.ImageURL = imageURL;
            this.IsDisponible = isDisponible;
            this.Reservation = reservation;
        }

        public int Id { get; set; }
        public int NumChambre { get; set; }
        public int NbPersonneMax { get; set; }
        public int NbLit { get; set; }
        public double Prix { get; set; }
        public string ImageURL { get; set; }
        public bool IsDisponible { get; set; }
        public List<ReservationModel> Reservation { get; set; }
    }
}
