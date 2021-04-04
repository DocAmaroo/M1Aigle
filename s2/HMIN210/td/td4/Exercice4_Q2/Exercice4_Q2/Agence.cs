using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Exercice4_App
{
    public class Agence
    {

        private string nom;
        private double tarifPropre;

        public Agence(string nom, double tarifPropre) 
        {
            this.nom = nom;
            this.tarifPropre = tarifPropre;
        }

        public string Nom
        {
            get => nom;
            set => nom = value;
        }

        public double Prix
        {
            get => tarifPropre;
            set => tarifPropre = value;
        }
    }
}
