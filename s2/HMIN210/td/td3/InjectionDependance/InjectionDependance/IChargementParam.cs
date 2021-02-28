using System;
using System.Collections.Generic;
using System.Text;

namespace InjectionDependance
{
    interface IChargementParam
    {
        Client getClientById(int clientId);
        ParamClient getParamClient(int clientId);
    }
}
