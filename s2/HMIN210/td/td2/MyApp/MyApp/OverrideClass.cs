using System;

namespace MyApp
{
    abstract class AbstractClass
    {
        public abstract void display();
    }

    class NewOverrideClass : AbstractClass
    {
        public override void display()
        {
            Console.WriteLine("Display from override class");
            Console.ReadLine();
        }
    }
}
