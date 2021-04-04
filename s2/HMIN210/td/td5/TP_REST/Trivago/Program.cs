using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using RestSharp;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Trivago.Models;
using Trivago.Forms;

namespace Trivago
{
    class Program
    {
        private static readonly string APIUrl = "https://localhost:44312/api/";
        static void Main(string[] args)
        {
            // --- Authentification utilisateur
            bool isAuth = false;
            do
            {
                isAuth = Authentification();
                if (isAuth) ConsoleWriteLineWithFlag(0, "Connexion réussie !", true);
                else ConsoleWriteLineWithFlag(3, "Connexion refusée ! Veuillez-réessayer", true);
            } while (!isAuth);

            while (true)
            {
                int userChoice = -1;
                string[] filter;
                List<OffreResponseModel> offres;
                do
                {
                    // --- On demande les informations de recherche au client
                    ConsoleWriteLineWithFlag(1, "Veuillez fournir les informations suivantes afin de vous guider vers le meilleur choix d'hotel:", false);
                    DisplayFilterInformation();
                    filter = GetFilter();

                    // --- On récupère toutes les offres de chaque agence
                    offres = Comparateur(filter);

                    // --- On laisse l'utilisateur choisir son offre
                    userChoice = LetUserChooseAnOffer(offres);
                } while (userChoice < 1);

                // --- Si annulation de commande.
                if (userChoice == 0) ConsoleWriteLineWithFlag(3, "Annulation en cours, vous allez être redirigez vers l'accueil!", true);
                else
                {
                    KeyValuePair<AgenceModel, OffreModel> offreChoose = GetOffreChoose(offres, userChoice);
                    ConsoleWriteLineWithFlag(1, "Vous souhaitez réserver l'offre suivante: ", false);
                    Console.WriteLine("\nAgence: " + offreChoose.Key.Nom);
                    offreChoose.Value.display();
                    ClientModel client = AskClientInformation();
                    if (client == null) ConsoleWriteLineWithFlag(3, "Annulation de la réservation.", true);
                    else
                    {
                        ReservationModel reservation = new ReservationModel(Convert.ToDateTime(filter[2]), Convert.ToDateTime(filter[3]), int.Parse(filter[1]), client);
                        if (ConfirmReservation(offreChoose, client))
                        {
                            var restClient = new RestClient(APIUrl);
                            var request = new RestRequest("Comparateur", Method.POST);

                            request.RequestFormat = DataFormat.Json;
                            request.AddJsonBody(new
                            {
                                AgenceName = offreChoose.Key.Nom,
                                OffreId = offreChoose.Value.Id,
                                Reservation = reservation
                            });
                            request.AddHeader("Content-Type", "application/json; charset=utf-8");
                            var response = restClient.Execute(request);

                            if (response.StatusCode == System.Net.HttpStatusCode.OK)
                            {
                                bool isReserved = JsonConvert.DeserializeObject<bool>(response.Content);
                                if (isReserved) ConsoleWriteLineWithFlag(0, "Réservation effectué avec succées!", true);
                                else ConsoleWriteLineWithFlag(3, "Nous n'avons pas pus validée votre réservation, veuillez-réessayer s'il vous plaît", true);
                            }
                        }
                        else ConsoleWriteLineWithFlag(3, "Annulation de la réservation.", true);
                    }
                }
            }
        }

        private static bool Authentification()
        {
            ConsoleWriteLineWithFlag(2, "Veuillez vous connecter pour accéder au service de Trivago", false);
            Console.Write("\n\tLogin    :(admin) ");
            string login = Console.ReadLine();
            Console.Write("\tPassword :(admin) ");
            string password = Console.ReadLine();

            var client = new RestClient(APIUrl);
            var request = new RestRequest("Home", Method.POST);

            request.RequestFormat = DataFormat.Json;
            request.AddJsonBody(new { Login = login, Password = password });
            request.AddHeader("Content-Type", "application/json; charset=utf-8");
            var response = client.Execute(request);

            if (response.StatusCode == System.Net.HttpStatusCode.OK)
            {
                var rawResponse = response.Content;
                bool isAuth = JsonConvert.DeserializeObject<bool>(rawResponse);
                return isAuth;
            }

            return false;
        }

        private static string[] GetFilter()
        {
            string[] filter = new string[5];
            Console.Write("\n\t Ville de séjour     : "); filter[0] = Console.ReadLine();
            Console.Write("\t Nombre de personnes : "); filter[1] = Console.ReadLine();
            Console.Write("\t Date de début       : "); filter[2] = Console.ReadLine();
            Console.Write("\t Date de fin         : "); filter[3] = Console.ReadLine();
            Console.Write("\t Nombre d'étoile     : "); filter[4] = Console.ReadLine();
            return filter;
        }

        private static KeyValuePair<AgenceModel, OffreModel> GetOffreChoose(List<OffreResponseModel> offres, int userChoice)
        {
            foreach (OffreResponseModel offreResponse in offres)
            {
                int nbOffres = offreResponse.Offres.Count();

                // l'offre choisis est dans la liste
                if (nbOffres >= userChoice) return new KeyValuePair<AgenceModel, OffreModel>(offreResponse.Agence, offreResponse.Offres.ElementAt(userChoice - 1));

                // l'offre n'est pas dans cette liste, donc surement dans celle qui suit.
                // On décrémente du nombres d'offre trouvés pour obtenir le potentiel index de l'offre à la prochaine étape
                else userChoice -= nbOffres;
            }

            return new KeyValuePair<AgenceModel, OffreModel>();
        }

        private static List<OffreResponseModel> Comparateur(string[] filter)
        {
            var client = new RestClient(APIUrl);

            var request = new RestRequest("Comparateur", Method.GET);
            request.AddParameter("ville", filter[0]);
            request.AddParameter("nbPersonne", int.Parse(filter[1]));
            request.AddParameter("dateDebut", filter[2]);
            request.AddParameter("dateFin", filter[3]);
            request.AddParameter("nbEtoile", int.Parse(filter[4]));

            var response = client.Execute(request);
            if (response.StatusCode == System.Net.HttpStatusCode.OK)
            {
                var rawResponse = response.Content;
                string jsonResult = JsonConvert.DeserializeObject<string>(rawResponse);
                List<OffreResponseModel> offreResponse = JsonConvert.DeserializeObject<List<OffreResponseModel>>(jsonResult);
                return offreResponse;
            }

            return null;
        }

        private static int LetUserChooseAnOffer(List<OffreResponseModel> offresResponse)
        {
            if (offresResponse.Count == 0)
            {
                ConsoleWriteLineWithFlag(3, "Nous sommes désolé mais aucune n'offre n'est disponible pour le moment.\nVous pouvez réessayer avec de nouvelles informations ou quitter l'application.", true);
                return -1;
            }

            ConsoleWriteLineWithFlag(1, "Voici les offres disponibles, par agence, choississez celle qui vous plaît à l'aide du numéro de l'offre.", false);
            int userChoice = -1;
            int offreCounter = 1;
            do
            {
                foreach (OffreResponseModel offreResponse in offresResponse)
                {
                    Console.WriteLine("\n\n\t\t# -------------- " + offreResponse.Agence.Nom.ToUpper() + " -------------- #");
                    foreach (OffreModel offre in offreResponse.Offres)
                    {
                        Console.WriteLine("\n\nOffre#" + offreCounter + " --------------------------------");
                        offre.display();
                        
                        // Affichage avec un window form
                        OffreForm form = new OffreForm("Offre#" + offreCounter, "Agence: " + offreResponse.Agence.Nom, offre.Chambre.ToString(), "", offre.Chambre.ImageURL);
                        form.ShowDialog();

                        offreCounter++;
                    }
                }

                Console.Write("\n(Utiliser 0 pour annuler)\nVous souhaitez réserver l'Offre#");
                userChoice = int.Parse(Console.ReadLine());
            } while (userChoice < 0 && userChoice > offreCounter);

            // --- Si l'utilisateur décide d'annuler alors on renvoie -1
            // Sinon on renvoie son choix (-1 pour obtenir l'index)
            return userChoice;
        }

        private static ClientModel AskClientInformation()
        {
            ClientModel client = new ClientModel();

            ConsoleWriteLineWithFlag(1, "Veuillez saisir vos informations pour compléter la réservation: ", false);

            ConsoleWriteLineWithForegroundColor(ConsoleColor.DarkYellow, "\n/!\\ (Pour annuler la réservation taper uniquement sur entrée) /!\\");
            Console.Write("Votre nom               : ");
            string nom = Console.ReadLine();
            if (nom.Equals(""))
            {
                return null;
            }
            client.Nom = nom;
            Console.Write("Votre prénom            : ");
            client.Prenom = Console.ReadLine();
            Console.Write("Vos coordonnée bancaire : (peut-être vide ou n'importe quoi) ");
            client.CarteCredit = Console.ReadLine();

            return client;
        }

        private static bool ConfirmReservation(KeyValuePair<AgenceModel, OffreModel> offre, ClientModel client)
        {
            // Affichage avec une form
            OffreForm form = new OffreForm("Récapitulatif de commande", "Agence: " + offre.Key.Nom, offre.Value.Chambre.ToString(), client.ToString(), offre.Value.Chambre.ImageURL);
            form.ShowDialog();

            ConsoleWriteLineWithFlag(1, "Confirmer la réservation ? (NON: 0, OUI: 1) ", false);

            return (int.Parse(Console.ReadLine()) == 1);
        }

        private static void DisplayFilterInformation()
        {
            ConsoleWriteLineWithForegroundColor(ConsoleColor.DarkYellow, "\n\n\t\t******************************************************");
            ConsoleWriteLineWithForegroundColor(ConsoleColor.DarkYellow, "\n\t\t/!\\ ---------- Informations importantes ---------- /!\\");
            ConsoleWriteLineWithForegroundColor(ConsoleColor.DarkYellow, "\n\t\t******************************************************");
            Console.WriteLine("\n* Ville disponible: Montpellier || Toulouse || Paris");
            Console.WriteLine("* Format date     : jj/mm/aaaa. La date de début doit être supérieur au 1er Mars 2021 (01/03/2021)");
        }

        private static void ConsoleWriteLineWithForegroundColor(ConsoleColor color, String message)
        {
            Console.ForegroundColor = color;
            Console.WriteLine(message);
            Console.ResetColor();
        }

        private static void ConsoleWriteLineWithFlag(int flagType, string message, bool addForeground)
        {
            switch(flagType)
            {
                case 0:
                    Console.ForegroundColor = ConsoleColor.Green;
                    Console.Write("\n\n[+] ");
                    break;
                case 1:
                    Console.ForegroundColor = ConsoleColor.DarkYellow;
                    Console.Write("\n\n[~] ");
                    break;
                case 2:
                    Console.ForegroundColor = ConsoleColor.Red;
                    Console.Write("\n\n[!] ");
                    break;
                case 3:
                    Console.ForegroundColor = ConsoleColor.Red;
                    Console.Write("\n\n[-] ");
                    break;
                default:
                    break;
            }
            if (addForeground) 
            { 
                Console.Write(message); 
                Console.ResetColor(); 
            }
            else 
            { 
                Console.ResetColor(); 
                Console.Write(message); 
            }
        }
    }
}
