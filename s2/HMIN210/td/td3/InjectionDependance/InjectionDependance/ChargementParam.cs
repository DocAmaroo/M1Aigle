using System;
using System.Collections.Generic;
using System.Text;

namespace InjectionDependance
{
    class ChargementParam : IChargementParam
    {
        public Client getClientById(int clientId)
        {
            return new Client(clientId, "Pierre", "Kimousse", new ParamClient("Famille nombreuses"));
        }

        public ParamClient getParamClient(int clientId)
        {
            return new ParamClient("Famille nombreuses");
        }
    }
}
