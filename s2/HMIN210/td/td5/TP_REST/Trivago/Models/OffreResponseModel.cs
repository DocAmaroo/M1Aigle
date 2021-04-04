using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Trivago.Models
{
    public class OffreResponseModel
    {
        public AgenceModel Agence { get; set; }
        public OffreModel[] Offres { get; set; }
    }
}
