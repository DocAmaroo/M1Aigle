using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Exercice4_App;

namespace HotelServices
{
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]

    public class Formule1Services : System.Web.Services.WebService
    {

        private static Hotel hotel;
        private static List<Offre> offres;
        private static string defaultPassword = "admin";
        private static bool isAuth = false;
        private static double tarifPropre = 1.00;

        public Formule1Services()
        {
            this.GenerateHotel();
            this.GenerateOffres();
        }

        [WebMethod]
        public double getTarifPropre()
        {
            return tarifPropre;
        }

        [WebMethod]
        public bool Authentification(string login, string password)
        {
            if (password.Equals(defaultPassword))
            {
                if (login.Equals("Trivaga") || login.Equals("admin"))
                {
                    isAuth = true;

                    if (login.Equals("Trivaga"))
                    {
                        tarifPropre = 0.96;
                    }

                    return isAuth;
                }
            }

            isAuth = false;
            return isAuth;
        }

        [WebMethod]
        public string GetOffres(DateTime date, int nbPersonne)
        {
            if (isAuth)
            {

                string offresFound = "";

                foreach (Offre offre in offres)
                {
                    if (offre.Disponible && DateTime.Compare(offre.DateDisponibilite, date) <= 0 && offre.Chambre.NbPersonneMax >= nbPersonne)
                    {
                        offresFound += offre.Id
                            + "|" + offre.DateDisponibilite
                            + "|" + offre.Chambre.NumChambre
                            + "|" + offre.Chambre.NbLit
                            + "|" + offre.Prix * tarifPropre
                            + "|" + offre.ImageURL
                            + "#";
                    }
                }
                return offresFound;
            }

            return null;
        }

        [WebMethod]
        public string GetOffre(string id)
        {
            foreach (Offre offre in offres)
            {
                if (offre.Id.Equals(id) && offre.Disponible)
                {
                    return offre.Id
                        + "," + offre.DateDisponibilite
                        + "," + offre.Chambre.NumChambre
                        + "," + offre.Chambre.NbLit
                        + "," + offre.Prix * tarifPropre
                        + "," + offre.ImageURL
                        + "|";
                }
            }
            return null;
        }

        [WebMethod]
        public string MakeReservation(string id, string[] client)
        {
            int offerIndex = this.CheckOffre(int.Parse(id));
            if (offerIndex > 0)
            {
                offres.ElementAt(offerIndex).Disponible = false;
                return "[+] Réservation effectué avec succées!";
            }

            return null;
        }

        public void GenerateHotel()
        {
            Chambre c1 = new Chambre(1, 2, 2, 300, "https://www.usine-digitale.fr/mediatheque/3/9/8/000493893_homePageUne/hotel-c-o-q-paris.jpg");
            Chambre c2 = new Chambre(2, 3, 2, 400, "https://d397xw3titc834.cloudfront.net/images/original/2/b8/2b8e013e6c5fb747415b8a916eff7eb7.jpg");
            Chambre c3 = new Chambre(3, 5, 5, 800, "http://www.furnotel.co.uk/wp-content/uploads/2015/11/furnotel-gold-and-black-boutique-hotel-suite-1024x683.jpg");
            Chambre c4 = new Chambre(4, 3, 3, 250, "https://media.cntraveler.com/photos/53dabff3dcd5888e145ca051/4:3/w_480,c_limit/eccleston-square-hotel-london-england-2-113144.jpg");
            Chambre c5 = new Chambre(5, 2, 1, 50, "https://cdn-image.travelandleisure.com/sites/default/files/styles/1600x1000/public/images/amexpub/0043/3588/201406-w-top-rated-hotel-beds-in-america-sofitel-new-york.jpg?itok=mmEvcf4W");
            Chambre c6 = new Chambre(6, 4, 4, 250, "https://tse3.mm.bing.net/th?id=OIP.psw5wUf9RayO747hxsN05wAAAA&pid=Api");
            Chambre c7 = new Chambre(7, 3, 1, 100, "https://tse4.mm.bing.net/th?id=OIP.JgNjG3rpPEF2s4ekGYskqgAAAA&pid=Api");
            Chambre c8 = new Chambre(8, 4, 2, 150, "https://tse4.mm.bing.net/th?id=OIP.b6TDBFr8E0sNFDH5GJuxwQHaGP&pid=Api");
            Chambre c9 = new Chambre(9, 4, 3, 175, "https://tse2.mm.bing.net/th?id=OIP.9OAGkziLUQ85E0nY8ydEegAAAA&pid=Api");
            Chambre c10 = new Chambre(10, 5, 4, 200, "https://d397xw3titc834.cloudfront.net/images/original/2/31/231d85a09fb1959145b71f1408345f10.jpg");
            Chambre c11 = new Chambre(11, 5, 3, 250, "https://media-cdn.tripadvisor.com/media/photo-s/06/28/8c/9f/balladins-sarcelles.jpg");
            Chambre c12 = new Chambre(12, 2, 1, 60, "https://tse2.mm.bing.net/th?id=OIP.RFpDSVZNvh5qn8_cw5ZNSQHaHY&pid=Api");
            Chambre c13 = new Chambre(13, 1, 1, 70, "https://media.cntraveler.com/photos/53dabff3dcd5888e145ca051/master/w_1200,c_limit/eccleston-square-hotel-london-england-2-113144.jpg");
            Chambre c14 = new Chambre(14, 1, 1, 80, "https://businesstravellife.com/wp-content/uploads/2015/09/best-hotel-bed-business-travel-life.jpg");
            Chambre c15 = new Chambre(15, 2, 2, 200, "https://www.vendee-hotel-restaurant.com/wp-content/uploads/2014/10/IMG_9063-700x467.jpg");

            List<Chambre> chambres = new List<Chambre>() { c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15 };

            List<Agence> agencesPartenaire = new List<Agence> { new Agence("Trivaga", 0.96) };
            hotel = new Hotel("Formule1 Hotel", "France", "Montpellier", "12 rue des oliviers", "", "49.726x3.648", 5, chambres, agencesPartenaire);
        }

        public void GenerateOffres()
        {
            offres = new List<Offre>();
            Random rnd = new Random();
            int offreId = 0;
            foreach (Chambre chambre in hotel.Chambres)
            {
                int day = rnd.Next(1, 31);
                offres.Add(new Offre(offreId, chambre, new DateTime(2021, 3, day), chambre.Prix, chambre.ImageURL, true));
                offreId++;
            }
        }

        public int CheckOffre(int id)
        {
            int i = 0;
            foreach (Offre offre in offres)
            {
                if (offre.Id == id && offre.Disponible)
                {
                    return i;
                }
                i++;
            }
            return -1;
        }
    }
}
