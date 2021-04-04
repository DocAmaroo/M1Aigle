using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Trivago.Models
{
    public class ClientModel
    {
        public int Id { get; set; }
        public string Nom { get; set; }
        public string Prenom { get; set; }
        public string CarteCredit { get; set; }

        public override string ToString ()
        {
            return this.Nom
                + "\n" + this.Prenom
                + "\n" + this.CarteCredit;
        }
    }
}
