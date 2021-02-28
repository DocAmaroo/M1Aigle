using System;
using System.Text;

namespace InjectionDependance
{
    class TicketDeCaisse
    {

        private IChargementParam chargementParam;

        public TicketDeCaisse(IChargementParam chargementParam)
        {
            this.chargementParam = chargementParam;
        }

        public Client getClient(int id)
        {
            return this.chargementParam.getClientById(id);
        }

        public ParamClient getParamClient(int id)
        {
            return this.chargementParam.getClientById(id).ParamClient;
        }
    }
}
