using System;
using System.Collections.Generic;
using System.Text;

namespace InjectionDependance
{
    class ParamClient
    {
        private string type;

        public ParamClient(string type)
        {
            this.type = type;
        }

        public string Type
        {
            get { return this.type; }
            set { this.type = value; }
        }

        public void display()
        {
            Console.WriteLine("Paramètre client: " + this.Type);
        }
    }
}
