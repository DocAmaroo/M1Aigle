using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Service_Web.Calculatrice;

namespace Service_Web
{
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    public class WebService1 : WebService
    {

        CalculatorWebService calculatrice = new CalculatorWebService();

        [WebMethod]
        public int add(int a, int b)
        {
            return calculatrice.Add(a, b);
        }

        [WebMethod]
        public int sub(int a, int b)
        {
            return calculatrice.Subtract(a, b);
        }
    }
}
