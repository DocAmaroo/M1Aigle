using API.Models;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using System.Text.Json;
using System.Text.Json.Serialization;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class IbisController : ControllerBase
    {
        private static readonly MockDatabase db = new MockDatabase();
        private static readonly AgenceModel agence = new AgenceModel("Ibis", 0.969);

        // GET: api/Ibis/Offres
        [HttpGet("Offres")]
        public string GetOffres()
        {
            List<OffreModel> offres = new List<OffreModel>(db.Offres);
            foreach (OffreModel offre in offres)
            {
                offre.Chambre.Prix = Math.Ceiling(offre.Chambre.Prix * agence.TarifPropre);
            }
            return JsonConvert.SerializeObject(new { Agence = agence, Offres = offres });
        }

        // GET: api/ibis/Offres/Filter
        [HttpGet("Offres/Filter")]
        public string GetOffres(string ville, int nbPersonne, string dateDebut, string dateFin, int nbEtoile)
        {
            List<OffreModel> offreFound = new List<OffreModel>();
            foreach (HotelModel hotel in db.Hotels)
            {
                if (hotel.Ville.Equals(ville) && hotel.NbEtoile == nbEtoile)
                {
                    foreach (OffreModel offre in db.Offres)
                    {
                        // --- Plusieurs hotels pourrait effectivement avoir le même nom, mais dans notre exemple nous n'en avons qu'un seul
                        // Ainsi, par praticité nous pouvons utiliser cette comparaison. Sinon il faudrait comparer les deux instances d'hotels.
                        if (offre.HotelNom.Equals(hotel.Nom) && offre.Chambre.NbPersonneMax >= nbPersonne)
                        {
                            // Si la chambre est disponible ou
                            // Si il y'a une réservation en cours et la chambre se libère avant l'arrivée de notre client
                            if (offre.Chambre.IsDisponible ||
                                offre.Chambre.Reservation != null && DateTime.Compare(offre.Chambre.Reservation.Last().DateFin, Convert.ToDateTime(dateDebut)) <= 0)
                            {
                                offreFound.Add(offre);
                            }
                        }
                    }
                }
            }

            if (offreFound.Count == 0)
            {
                return JsonConvert.SerializeObject(null);
            }
            
            // --- On applique le tarif propre, ici global à l'aide d'un pourcentage (-0.31%), de l'agence
            foreach (OffreModel offre in offreFound)
            {
                offre.Chambre.Prix = Math.Ceiling(offre.Chambre.Prix * agence.TarifPropre);
            }
            return JsonConvert.SerializeObject(new { Agence = agence, Offres = offreFound });
        }

        [HttpPost("Reservation")]
        public bool MakeReservation(ReservationRequestModel reservationRequest)
        {
            foreach (OffreModel offre in db.Offres)
            {
                if (offre.Id == reservationRequest.OffreId)
                {
                    offre.Chambre.Reservation.Add(reservationRequest.Reservation);
                    return true;
                }
            }

            return false;
        }
    }
}
