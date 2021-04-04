using System;
using System.Collections.Generic;
using System.Diagnostics.CodeAnalysis;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace API.Models
{
    public class OffreModel
    {
        public OffreModel() { }

        public OffreModel(int id, string hotelNom, string hotelAdresse, int hotelNbEtoile, ChambreModel chambre)
        {
            this.Id = id;
            this.HotelNom = hotelNom;
            this.HotelAdresse = hotelAdresse;
            this.HotelNbEtoile = hotelNbEtoile;
            this.Chambre = chambre;
        }

        public int Id { get; set; }
        public string HotelNom { get; set; }
        public string HotelAdresse { get; set; }
        public int HotelNbEtoile { get; set; }
        public ChambreModel Chambre { get; set; }
    }
}
