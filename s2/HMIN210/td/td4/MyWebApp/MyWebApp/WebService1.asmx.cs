using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using MyWebApp.Calculatrice;

namespace MyWebApp
{
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    public class WebService1 : WebService
    {

        [WebMethod]
        public string HelloWorld()
        {
            Calculator calc = new Calculator();
            int add = calc.Add(2, 2);
            Console.Out.WriteLine("Le résultat est ", add);
            return add.ToString();
        }
    }
}
