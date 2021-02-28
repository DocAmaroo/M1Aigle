using System;
using System.Collections.Generic;
using System.Text;

namespace InjectionDependance
{
    class ChargementParam2 : IChargementParam
    {
        public Client getClientById(int clientId)
        {
            return new Client(clientId, "Namasse", "Pasmousse", new ParamClient("VIP"));
        }

        public ParamClient getParamClient(int clientId)
        {
            return new ParamClient("VIP");
        }
    }
}
