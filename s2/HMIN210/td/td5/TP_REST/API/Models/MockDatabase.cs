using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace API.Models
{
    public class MockDatabase
    {
        private static List<ChambreModel> chambres;
        private static List<HotelModel> hotels;
        private static List<OffreModel> offres;

        public MockDatabase()
        {
            if (hotels == null)
            {
                this.GenerateHotels();
            }
        }

        public List<ChambreModel> Chambres { get => chambres; set => chambres = value; }
        public List<HotelModel> Hotels { get => hotels; set => hotels = value; }
        public List<OffreModel> Offres { get => offres; set => offres = value; }

        public void GenerateHotels()
        {
            ChambreModel c1 = new ChambreModel(0, 1, 2, 2, 300, "https://www.usine-digitale.fr/mediatheque/3/9/8/000493893_homePageUne/hotel-c-o-q-paris.jpg", true);
            ChambreModel c2 = new ChambreModel(1, 2, 3, 2, 400, "https://d397xw3titc834.cloudfront.net/images/original/2/b8/2b8e013e6c5fb747415b8a916eff7eb7.jpg", true);
            ChambreModel c3 = new ChambreModel(2, 3, 5, 5, 800, "http://www.furnotel.co.uk/wp-content/uploads/2015/11/furnotel-gold-and-black-boutique-hotel-suite-1024x683.jpg", true);
            ChambreModel c4 = new ChambreModel(3, 4, 3, 3, 250, "https://media.cntraveler.com/photos/53dabff3dcd5888e145ca051/4:3/w_480,c_limit/eccleston-square-hotel-london-england-2-113144.jpg", true);
            ChambreModel c5 = new ChambreModel(4, 5, 2, 1, 50, "https://cdn-image.travelandleisure.com/sites/default/files/styles/1600x1000/public/images/amexpub/0043/3588/201406-w-top-rated-hotel-beds-in-america-sofitel-new-york.jpg?itok=mmEvcf4W", true);
            ChambreModel c6 = new ChambreModel(5, 6, 4, 4, 250, "https://tse3.mm.bing.net/th?id=OIP.psw5wUf9RayO747hxsN05wAAAA&pid=Api", true);
            ChambreModel c7 = new ChambreModel(6, 7, 3, 1, 100, "https://tse4.mm.bing.net/th?id=OIP.JgNjG3rpPEF2s4ekGYskqgAAAA&pid=Api", true);
            ChambreModel c8 = new ChambreModel(7, 8, 4, 2, 150, "https://tse4.mm.bing.net/th?id=OIP.b6TDBFr8E0sNFDH5GJuxwQHaGP&pid=Api", true);
            ChambreModel c9 = new ChambreModel(8, 9, 4, 3, 175, "https://tse2.mm.bing.net/th?id=OIP.9OAGkziLUQ85E0nY8ydEegAAAA&pid=Api", true);
            ChambreModel c10 = new ChambreModel(9, 10, 5, 4, 200, "https://d397xw3titc834.cloudfront.net/images/original/2/31/231d85a09fb1959145b71f1408345f10.jpg", true);
            ChambreModel c11 = new ChambreModel(10, 11, 5, 3, 250, "https://media-cdn.tripadvisor.com/media/photo-s/06/28/8c/9f/balladins-sarcelles.jpg", true);
            ChambreModel c12 = new ChambreModel(11, 12, 2, 1, 60, "https://tse2.mm.bing.net/th?id=OIP.RFpDSVZNvh5qn8_cw5ZNSQHaHY&pid=Api", true);
            ChambreModel c13 = new ChambreModel(12, 13, 1, 1, 70, "https://media.cntraveler.com/photos/53dabff3dcd5888e145ca051/master/w_1200,c_limit/eccleston-square-hotel-london-england-2-113144.jpg", true);
            ChambreModel c14 = new ChambreModel(13, 14, 1, 1, 80, "https://businesstravellife.com/wp-content/uploads/2015/09/best-hotel-bed-business-travel-life.jpg", true);
            ChambreModel c15 = new ChambreModel(14, 15, 2, 2, 200, "https://www.vendee-hotel-restaurant.com/wp-content/uploads/2014/10/IMG_9063-700x467.jpg", true);

            chambres = new List<ChambreModel>() { c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15 };

            Random rnd = new Random();
            foreach (ChambreModel chambre in chambres)
            {
                int dayDebut = chambre.NumChambre * 2;
                int nbJours = 2;
                int nbPersonne = 1;
                ClientModel mockClient = new ClientModel("Dupont", "Dupont", "123");
                DateTime dateDebut = new DateTime(2021, 3, dayDebut);
                DateTime dateFin = dateDebut.AddDays(nbJours);
                chambre.IsDisponible = false;
                chambre.Reservation.Add(new ReservationModel(dateDebut, dateFin, nbPersonne, mockClient));
            }

            hotels = new List<HotelModel>();
            hotels.Add(new HotelModel(0, "Palace Hotel", "France", "Montpellier", "12 rue des oliviers", "", "49.726x3.648", 5, chambres));
            hotels.Add(new HotelModel(1, "Four Seasons", "France", "Montpellier", "52 avenue de la weeb", "", "49.726x3.648", 1, chambres));
            hotels.Add(new HotelModel(2, "BlingBlingMotel", "France", "Toulouse", "420 avenue des amandiers", "", "49.726x3.648", 2, chambres));
            hotels.Add(new HotelModel(3, "Hotel Paradise", "France", "Paris", "69 rue des olives", "", "49.726x3.648", 5, chambres));

            GenerateOffres();
        }

        public void GenerateOffres()
        {
            offres = new List<OffreModel>();
            int offreId = 0;
            foreach (HotelModel hotel in hotels)
            {
                foreach (ChambreModel chambre in hotel.Chambres)
                {
                    offres.Add(new OffreModel(offreId, hotel.Nom, hotel.GetAdresse(), hotel.NbEtoile, chambre));
                    offreId++;
                }
            }
        }
    }
}
