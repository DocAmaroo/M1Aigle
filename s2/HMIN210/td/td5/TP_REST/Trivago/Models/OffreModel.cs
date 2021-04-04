using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Trivago.Models
{
    public class OffreModel
    {
        public int Id { get; set; }
        public string HotelNom { get; set; }
        public string HotelAdresse { get; set; }
        public int HotelNbEtoile { get; set; }
        public ChambreModel Chambre { get; set; }

        public void display()
        {
            string toDisplay =
                "Hotel: " + HotelNom + "\t" + HotelNbEtoile + "*"
                + "\nAdresse de l'hotel: " + HotelAdresse
                + "\nChambre n°" + Chambre.NumChambre
                + "\n-- Nombre de lit: " + Chambre.NbLit
                + "\n-- Disponible le: " + Chambre.Reservation.Last().DateFin.ToString()
                + "\n-- Prix: " + Chambre.Prix + "euros/nuit";

            Console.WriteLine(toDisplay);
        }
    }
}
