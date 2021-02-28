using System;
using System.Collections.Generic;
using Ninject;

namespace InjectionDependance
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("--- Ticket 1");

            // Ninject | Flag to true
            IKernel kernel = new StandardKernel(new NinModule(true));
            IChargementParam chargementParam = kernel.Get<IChargementParam>();
            TicketDeCaisse ticket1 = new TicketDeCaisse(chargementParam);
            ticket1.getClient(69).display();
            ticket1.getParamClient(69).display();


            Console.WriteLine("\n--- Ticket 2");

            // Ninject | Flag to false
            kernel = new StandardKernel(new NinModule(false));
            chargementParam = kernel.Get<IChargementParam>();
            TicketDeCaisse ticket2 = new TicketDeCaisse(chargementParam);
            ticket2.getClient(420).display();
            ticket2.getParamClient(420).display();
        }
    }
}
