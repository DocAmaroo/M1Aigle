using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace API.Models
{
    public class HotelModel
    {
        public HotelModel(int id, string nom, string pays, string ville, string rue, string lieuDit, string positionGPS, int nbEtoile, List<ChambreModel> chambres)
        {
            this.Id = id;
            this.Nom = nom;
            this.Pays = pays;
            this.Ville = ville;
            this.Rue = rue;
            this.LieuDit = lieuDit;
            this.PositionGPS = positionGPS;
            this.NbEtoile = nbEtoile;
            this.Chambres = chambres;
        }

        public int Id { get; set; }
        public string Nom { get; set; }
        public string Pays { get; set; }
        public string Ville { get; set; }
        public string Rue { get; set; }
        public string LieuDit { get; set; }
        public string PositionGPS { get; set; }
        public int NbEtoile { get; set; }
        public List<ChambreModel> Chambres { get; set; }

        public String GetAdresse()
        {
            return this.Rue + ", " + this.LieuDit + this.Ville + ", " + this.Pays;
        }
    }
}
