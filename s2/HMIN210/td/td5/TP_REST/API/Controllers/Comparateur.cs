using API.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using RestSharp;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class Comparateur : ControllerBase
    {
        private static readonly string APIUrl = "https://localhost:44312/api/";
        private static List<String> agences = new List<String> { "Ibis", "Formule1" };

        [HttpGet]
        public string Comparator(string ville, int nbPersonne, string dateDebut, string dateFin, int nbEtoile)
        {
            List<OffreResponseModel> allOffres = new List<OffreResponseModel>();

            foreach (string agenceName in agences)
            {
                // --- Création d'un client Rest
                var client = new RestClient(APIUrl);

                // --- Mise en place de la requête
                var request = new RestRequest(agenceName + "/Offres/Filter", Method.GET);
                request.AddParameter("ville", ville);
                request.AddParameter("nbPersonne", nbPersonne);
                request.AddParameter("dateDebut", dateDebut);
                request.AddParameter("dateFin", dateFin);
                request.AddParameter("nbEtoile", nbEtoile);
                request.AddHeader("Content-Type", "application/json; charset=utf-8");

                // --- Exécution et gestion de la réponse
                var response = client.Execute(request);
                if (response.StatusCode == System.Net.HttpStatusCode.OK)
                {
                    var rawResponse = response.Content;
                    string jsonResult = JsonConvert.DeserializeObject<string>(rawResponse);
                    if (!jsonResult.Equals("null"))
                    {
                        OffreResponseModel offreResponse = JsonConvert.DeserializeObject<OffreResponseModel>(jsonResult);
                        allOffres.Add(offreResponse);
                    }
                    
                }
            }

            return JsonConvert.SerializeObject(allOffres);
        }

        [HttpPost]
        public bool MakeReservation([FromBody] ReservationRequestModel reservationRequest)
        {
            // --- Création d'un client Rest
            var client = new RestClient(APIUrl);

            // --- Mise en place de la requête
            var request = new RestRequest(reservationRequest.AgenceName + "/Reservation", Method.POST);
            request.AddJsonBody(reservationRequest);
            request.AddHeader("Content-Type", "application/json; charset=utf-8");

            // --- Exécution et gestion de la réponse
            var response = client.Execute(request);
            if (response.StatusCode == System.Net.HttpStatusCode.OK)
            {
                return JsonConvert.DeserializeObject<bool>(response.Content);
            }

            return false;
        }
    }
}
