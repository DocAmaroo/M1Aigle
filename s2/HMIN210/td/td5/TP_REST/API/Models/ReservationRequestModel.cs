using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace API.Models
{
    public class ReservationRequestModel
    {
        public String AgenceName { get; set; }
        public int OffreId { get; set; }
        public ReservationModel Reservation { get; set; }
    }
}
