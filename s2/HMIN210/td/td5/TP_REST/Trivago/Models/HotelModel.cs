using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Trivago.Models
{
    public class HotelModel
    {
        public int Id { get; set; }
        public string Nom { get; set; }
        public string Pays { get; set; }
        public string Ville { get; set; }
        public string Rue { get; set; }
        public string LieuDit { get; set; }
        public string PositionGPS { get; set; }
        public int NbEtoile { get; set; }
        public List<ChambreModel> Chambres { get; set; }
    }
}
