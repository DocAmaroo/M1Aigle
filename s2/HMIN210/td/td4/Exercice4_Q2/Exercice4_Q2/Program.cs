using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using Exercice4_App.Forms;
using System.Threading.Tasks;
using Exercice4_App.IbisHotelServices;
using Exercice4_App.Formule1HotelServices;

namespace Exercice4_App
{
    public class Program
    {
        static void Main(string[] args)
        {

            // --- Services des hotels
            IbisServices ibisServices = new IbisServices();
            Formule1Services formule1Services = new Formule1Services();
            Console.WriteLine("\t\t Bienvenue sur Trivaga, l'agence d'hotel au plus proche de ses clients !");

            while (true)
            {
                // --- On demande l'hotel à consulter
                string[] hotels = new string[2] { "Ibis", "Formule1" };
                int hotelChoose = askHotel(hotels);

                if (hotelChoose == 1)
                {
                    Console.WriteLine("[+] Vous avez choisis l'hotel Ibis");

                    // --- Authentification simple (login: agenceName, password: admin)
                    string[] auth = HandleAuthentification("Trivaga");
                    int flag = 0;
                    while (!ibisServices.Authentification(auth[0], auth[1]))
                    {
                        if (auth[0].Equals(auth[1]) && auth[0].Equals("exit"))
                        {
                            flag = -1;
                            break;
                        }
                        Console.WriteLine("[-] Login ou mot de passe incorrect. Veuillez recommencer");
                        auth = HandleAuthentification("Trivaga");
                        flag = 0;
                    }

                    if (flag != -1)
                    {
                        // --- On demande à l'utilisateur ce qu'il recherche
                        string[] filter = askOffresFilter();
                        string offres = ibisServices.GetOffres(ParseDate(filter[1]), int.Parse(filter[0]));

                        // --- On affiche à l'utilisateur les offres qui correspondent
                        string offreChoose;
                        while ((offreChoose = askOffre(offres)) != null)
                        {
                            // --- L'utilisateur ne veut pas réserver
                            string[] offreChooseSplit = ParseOffre(offreChoose);

                            Client client;
                            string reservationResponse = null;
                            Console.WriteLine("\n[~] Vous souhaitez réserver l'offre suivante: ");
                            displayOffre(offreChooseSplit);
                             
                            // --- On demande les informations clients, si non fournis on annule la réservation.
                            client = askClientInformation();
                            if (client == null)
                            {
                                Console.WriteLine("[-] Annulation de la réservation...");
                            }
                            else
                            {
                                if (confirmReservation(offreChooseSplit, client.toArrString()))
                                {
                                    reservationResponse = ibisServices.MakeReservation(offreChooseSplit[0], client.toArrString());
                                    if (reservationResponse == null)
                                    {
                                        Console.WriteLine("\n\n[-] Cette offre n'existe pas ou une erreur est survenue, veuillez réessayer.");
                                    }
                                    else
                                    {
                                        Console.WriteLine(reservationResponse);
                                        break;
                                    }
                                }
                                else
                                {
                                    Console.WriteLine("[-] Annulation de la réservation...");
                                }
                            }
                        }
                    }
                }

                if (hotelChoose == 2)
                {
                    Console.WriteLine("[+] Vous avez choisis l'hotel Formule1");

                    // --- Authentification simple (login: agenceName, password: admin)
                    string[] auth = HandleAuthentification("Trivaga");
                    int flag = 0;
                    while (!formule1Services.Authentification(auth[0], auth[1]))
                    {
                        if (auth[0].Equals(auth[1]) && auth[0].Equals("exit"))
                        {
                            flag = -1;
                            break;
                        }
                        Console.WriteLine("[-] Login ou mot de passe incorrect. Veuillez recommencer");
                        auth = HandleAuthentification("Trivaga");
                        flag = 0;
                    }
                    if (flag != -1)
                    {
                        // --- On demande à l'utilisateur ce qu'il recherche
                        string[] filter = askOffresFilter();
                        string offres = formule1Services.GetOffres(ParseDate(filter[1]), int.Parse(filter[0]));

                        // --- On affiche à l'utilisateur les offres qui correspondent
                        string offreChoose;
                        while ((offreChoose = askOffre(offres)) != null)
                        {
                            // --- L'utilisateur ne veut pas réserver
                            string[] offreChooseSplit = ParseOffre(offreChoose);

                            Client client;
                            string reservationResponse = null;
                            Console.WriteLine("\n[~] Vous souhaitez réserver l'offre suivante: ");
                            displayOffre(offreChooseSplit);

                            // --- On demande les informations clients, si non fournis on annule la réservation.
                            client = askClientInformation();
                            if (client == null)
                            {
                                Console.WriteLine("[-] Annulation de la réservation...");
                            }
                            else
                            {
                                if (confirmReservation(offreChooseSplit, client.toArrString()))
                                {
                                    reservationResponse = formule1Services.MakeReservation(offreChooseSplit[0], client.toArrString());
                                    if (reservationResponse == null)
                                    {
                                        Console.WriteLine("\n\n[-] Cette offre n'existe pas ou une erreur est survenue, veuillez réessayer.");
                                    }
                                    else
                                    {
                                        Console.WriteLine(reservationResponse);
                                        break;
                                    }
                                }
                                else
                                {
                                    Console.WriteLine("[-] Annulation de la réservation...");
                                }
                            }
                        }
                    }
                }
            }
        }

        static string[] ParseOffres(string offres)
        {
            string[] offresSplit = offres.Split('#');

            // --- On supprime la dernière cases car elle est vide
            Array.Resize(ref offresSplit, offresSplit.Length - 1);

            return offresSplit;
        }

        static string[] ParseOffre(string offre)
        {
            return offre.Split('|');
        }

        static DateTime ParseDate(string date)
        {
            string[] dateSplit = date.Split('/');
            return new DateTime(int.Parse(dateSplit[2]), int.Parse(dateSplit[1]), int.Parse(dateSplit[0]));
        }

        static string[] HandleAuthentification(string agenceName)
        {
            string[] auth = new string[2];
            Console.WriteLine("\n\n##### Veuillez-vous connecter pour continuer #####\n");
            Console.WriteLine("/!\\ (Pour annuler la réservation utiliser exit comme login et mot de passe) /!\\");
            Console.Write("Login    :(" + agenceName + ") ");
            string login = Console.ReadLine();
            if (login.Equals(""))
            {
                return null;
            }
            auth[0] = login;


            Console.Write("Password :(admin) ");
            auth[1] = Console.ReadLine();
            return auth;
        }

        static int askHotel(string[] hotels)
        {
            int choice = -1;
            do
            {
                Console.WriteLine("\n\n[~] Quel hotel souhaitez-vous consulter ? ");

                int i = 0;
                foreach (string hotel in hotels)
                {
                    Console.WriteLine(i + 1 + " - " + hotel);
                    i++;
                }

                Console.Write("\nVotre choix: ");
                choice = int.Parse(Console.ReadLine());
            } while (choice < 1 || choice > hotels.Length);

            return choice;
        }

        static string[] askOffresFilter()
        {
            Console.WriteLine("\n\n[~] Donner nous plus d'information sur les offres que vous recherchez");

            Console.Write("Nombre de personnes (entre 1 et 5): ");
            string nbPersonne = Console.ReadLine();

            Console.Write("Date de début (jj/mm/aaaa)        : (choisir une date en mars 2021 ex: 10/03/2021) ");
            string dateDebut = Console.ReadLine();

            Console.Write("Date de fin (jj/mm/aaaa)          : ");
            string dateFin = Console.ReadLine();

            return new string[] { nbPersonne, dateDebut, dateFin };
        }

        static string askOffre(string offres)
        {
            if (offres == null || offres.Equals(""))
            {
                Console.WriteLine("[-] Nous sommes désolé mais aucune offre n'est disponible");
                return null;
            }

            Console.WriteLine("\n\n[~] Voici les offres disponibles, choississez celle qui vous plaît.");

            int choice = -1;
            string[] offresSplit = ParseOffres(offres);

            do
            {
                int i = 1;
                foreach (string offre in offresSplit)
                {
                    Console.WriteLine("Offre#" + i);
                    string[] offreSplit = ParseOffre(offre);
                    displayOffre(offreSplit);

                    Form1 form = new Form1(offreSplit[5], " - Offre " + i + "\n", offreToString(offreSplit), "");
                    form.ShowDialog();
                    i++;
                }

                Console.Write("\n(Utiliser 0 pour annuler et revenir en arrière)\nVous souhaitez réserver l'Offre#");
                choice = int.Parse(Console.ReadLine());
            } while (choice < 0 || choice > offres.Length);

            if (choice > 0)
            {
                return offresSplit[choice - 1];
            }

            return null;
        }

        static Client askClientInformation()
        {
            Client client = new Client();

            Console.WriteLine("\n[~] Veuillez saisir vos informations pour compléter la réservation: ");
            Console.WriteLine("/!\\ (Pour annuler la réservation taper uniquement sur entrée) /!\\");
            Console.Write("Votre nom               : ");
            string nom = Console.ReadLine();
            if (nom.Equals(""))
            {
                return null;
            }
            client.Nom = nom;
            Console.Write("Votre prénom            : ");
            client.Prenom = Console.ReadLine();
            Console.Write("Vos coordonnée bancaire : (peut-être vide ou n'importe quoi ici) ");
            client.CarteCredit = Console.ReadLine();

            return client;
        }

        static void displayOffre(string[] offre)
        {
            Console.WriteLine("\tChambre n°" + offre[2]);
            Console.WriteLine("\tNombre lits   : " + offre[3]);
            Console.WriteLine("\tPrix          : " + offre[4] + "euros/nuit");
            Console.WriteLine("\tDisponible le : " + offre[1]);
        }

        static string offreToString(string[] offre)
        {
            return 
                "\tChambre n°" + offre[2]
                + "\n\tNombre lits   : " + offre[3]
                + "\n\tPrix          : " + offre[4] + "euros/nuit"
                + "\n\tDisponible le : " + offre[1];
        }
    
        static string clientToString(string[] client)
        {
            return
                "\tNom                    : " + client[0]
                + "\nPrénom               : " + client[1]
                + "\nCoordonées bancaires : " + client[2];
        }

        static bool confirmReservation(string[] offre, string[] client)
        {
            Form1 form = new Form1(offre[5], "Récapitulatif de réservation: " + "\n", offreToString(offre), clientToString(client));
            form.ShowDialog();

            Console.Write("[~] Confirmer la réservation ? (NON: 0, OUI: 1) ");

            return (int.Parse(Console.ReadLine()) == 1);
        }
    }
}
