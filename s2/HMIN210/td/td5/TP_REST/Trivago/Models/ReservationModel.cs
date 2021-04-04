using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Trivago.Models
{
    public class ReservationModel
    {
        public ReservationModel(DateTime dateDebut, DateTime dateFin, int nbPersonne, ClientModel client)
        {
            this.DateDebut = dateDebut;
            this.DateFin = dateFin;
            this.NbPersonne = nbPersonne;
            this.Client = client;
        }

        public int OffreId { get; set; }
        public DateTime DateDebut { get; set; }
        public DateTime DateFin { get; set; }
        public int NbPersonne { get; set; }
        public ClientModel Client { get; set; }
    }
}
