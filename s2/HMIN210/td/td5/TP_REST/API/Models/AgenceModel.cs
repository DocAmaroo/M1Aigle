using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace API.Models
{
    public class AgenceModel
    {

        public AgenceModel(string nom, double tarifPropre) 
        {
            this.Nom = nom;
            this.TarifPropre = tarifPropre;
        }

        public string Nom { get; set; }

        public double TarifPropre { get; set; }
    }
}
