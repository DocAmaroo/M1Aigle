using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Trivago.Models
{
   public class ChambreModel
    {
        public int Id { get; set; }
        public int NumChambre { get; set; }
        public int NbPersonneMax { get; set; }
        public int NbLit { get; set; }
        public double Prix { get; set; }
        public string ImageURL { get; set; }
        public bool IsDisponible { get; set; }
        public List<ReservationModel> Reservation { get; set; }

        public override string ToString ()
        {
            return
                "\nChambre n°" + NumChambre
                + "\n-- Nombre de lit: " + NbLit
                + "\n-- Disponible le: " + Reservation.Last().DateFin.ToString()
                + "\n-- Prix: " + Prix + "euros/nuit";
        }
    }
}
