using System;

namespace MyApp
{
    class Delegate
    {
        public delegate void Init(string msg);
        public delegate bool Done(int flag);
        public delegate void Step();

        public void exec()
        {
            // Delegate
            Init init = InitMethod;
            init("Welcome");


            // Delegate Anonyme
            Done done = delegate (int flag) { return (flag == 4); };

            int i = 0;
            while (!done(i)) { Console.WriteLine("i: " + i); i++; }

            // Delegate avec lambda expression
            Step step = () => { Console.WriteLine("Etape suivante!"); };
            step();
        }

        public void InitMethod(string msg)
        {
            Console.WriteLine(msg.ToUpper());
        }
    }


}
