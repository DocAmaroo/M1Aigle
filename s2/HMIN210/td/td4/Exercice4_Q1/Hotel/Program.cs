using System;
using System.Collections.Generic;

namespace Hotel
{
    class Program
    {
        static void Main(string[] args)
        {
            // --- Initialisation des clients
            Client c1 = new Client(1, "Rick", "Astley", "06/02/1966", "4970403038467997");
            Client c2 = new Client(2, "Jean-Pierre", "Polounaréfou", "03/07/1944", "4970403022046984");

            // --- Initialisation des chambres
            Chambre ch1 = new Chambre(1, 1, 2, 50, 4);
            Chambre ch2 = new Chambre(2, 2, 1, 100, 2);
            Chambre ch3 = new Chambre(3, 3, 2, 150, 3);

            // --- Initialisation des hotels
            Hotel h1 = new Hotel(1, "Ibis Hotel", "France", "Montpellier", "Rue des weebs", 69, "49.66x3.81", new List<Chambre> { ch1, ch2 }, 3);
            Hotel h2 = new Hotel(2, "Valheim Paradise", "France", "Montpellier", "Avenue de la fumette ", 420, "49.67x3.81", new List<Chambre> { ch1 }, 2);
            Hotel h3 = new Hotel(1, "Formule1 Hotel", "France", "Paris", "Rue du pain perdus", 12, "49.65x3.75", new List<Chambre> { ch1, ch2, ch3 }, 1);


            List<Hotel> hotels = new List<Hotel> { h1, h2, h3 };
            List<string> villes = new List<string>();
            foreach (Hotel hotel in hotels)
            {
                string ville = hotel.Ville;
                if (!villes.Contains(ville))
                {
                    villes.Add(ville);
                }
            }

            // --- Début de l'affichage utilisateur
            Console.WriteLine("\n===== Bienvenue à vous utilisateur! =====\n");
            Console.WriteLine("\n[~] Dans quelle ville voulez-vous séjourner ?");
            Console.WriteLine("\nListe des villes disponibles: ");
            foreach (string ville in villes)
            {
                Console.WriteLine("- " + ville);
            }
            Console.Write("\nVotre choix: ");
            string villeInput = Console.ReadLine();

            Console.WriteLine("\n[~] Votre date d'arrivée ? (format: jj/mm/yyyy) (Appuyer sur entrée pour passer rapidement)");
            Console.Write("\nVotre choix: "); 
            string dateArrive = Console.ReadLine();

            Console.WriteLine("\n[~] Votre date de départ ? (format: jj/mm/yyyy) (Appuyer sur entrée pour passer rapidement)");
            Console.Write("\nVotre choix: "); 
            string dateDepart = Console.ReadLine();

            // --- Affichage des Hotels disponibles
            Console.WriteLine("\n\n[+] Hôtels disponibles: \n");
            bool found = false;
            foreach(Hotel hotel in hotels) {
                string ville = hotel.Ville;
                if (ville.Equals(villeInput))
                {
                    Console.WriteLine(" " + hotel.Id + "  " + hotel.Nom);
                    found = true;
                }
            }

            if (!found)
            {
                Console.WriteLine("\n[-] Nous sommes désolé mais aucun hotel n'est disponible dans cette ville.\nAppuyer sur la touche 'entrée' pour quitter l'application.");
                Console.ReadLine();
                Environment.Exit(1);
            }


            Console.Write("\nVotre choix: (utilisez le numéro à gauche du nom de l'hotel) ");
            String input = Console.ReadLine();
            Hotel hotelChoisis = findHotelById(hotels, int.Parse(input));

            Console.Write("\n[~] Prix Minimum (>= 1): ");
            int prixMin = int.Parse(Console.ReadLine());
            Console.Write("[~] Prix Max (<= 200) : ");
            int prixMax = int.Parse(Console.ReadLine());

            Console.WriteLine("\n\n[~] Voici les chambres disponibles\n");

            found = false;
            foreach (Chambre chambre in hotelChoisis.Chambres)
            {
                if (chambre.Prix <= prixMax && chambre.Prix >= prixMin)
                {
                    found = true;
                    Console.WriteLine(chambre.display() + "\n");
                }
            }

            if (!found)
            {
                Console.WriteLine("\nNous sommes désolé mais aucune chambres n'est disponibles.\nAppuyer sur la touche 'entrée' pour quitter l'application.");
                Console.ReadLine();
                Environment.Exit(1);
            }
            
            Console.Write("\nChoisissez une chambre: (choisir avec le numéro)");
            input = Console.ReadLine();
            Chambre chambreChoisi = hotelChoisis[int.Parse(input)];

            Reservation reservation = new Reservation(1, dateArrive, dateDepart, chambreChoisi.prix, chambreChoisi.nbPersonneMax);

            Console.WriteLine("\n[~] Votre réservation est bientôt terminé, avant de finaliser l'opération veuillez fournir les informations suivantes:\n");
            Console.Write("Votre nom: ");
            string nomClient = Console.ReadLine(); 
            Console.Write("Votre prénom: ");
            string prenomClient = Console.ReadLine();
            Console.Write("Vos numéros de votre carte de paiement: ");
            string carteCredit = Console.ReadLine();
            Client client = new Client(3, nomClient, prenomClient, "", carteCredit);

            Console.WriteLine("\n[+] Réservation effectué avec sucées".ToUpper());
        }

        static Hotel findHotelById(List<Hotel> hotels, int id)
        {
            foreach(Hotel hotel in hotels) {
                if (hotel.Id == id)
                {
                    return hotel;
                }
            }

            return null;
        }
    }
}
