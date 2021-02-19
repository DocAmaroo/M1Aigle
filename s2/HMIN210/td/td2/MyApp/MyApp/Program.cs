using System;

namespace MyApp
{
    class Program
    {
        static void Main(string[] args)
        {
            // Delegates
            Delegate dl = new Delegate();
            dl.exec();

            // Indexeur
            Library lib = new Library();

            // Complexe
            Complexe c1 = new Complexe(3, 4);
            Complexe c2 = new Complexe(2, 5);
            Complexe add = c1 + c2;
            Complexe sub = c1 - c2;
            Complexe mult = c1 * c2;
            Complexe imult = 2 * c2;

            add.display();
            sub.display();
            mult.display();
            imult.display();
        }
    }
}