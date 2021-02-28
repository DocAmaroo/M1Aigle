using System;

namespace InjectionDependance
{
    class Client
    {
        private int id;
        private string nom;
        private string prenom;
        private ParamClient paramClient;

        public Client(int id, string nom, string prenom, ParamClient paramClient)
        {
            this.id = id;
            this.nom = nom;
            this.prenom = prenom;
            this.paramClient = paramClient;
        }

        public int Id
        {
            get { return this.id; }
        }

        public string Fullname
        {
            get { return this.nom + " " + this.prenom; }
        }

        public ParamClient ParamClient
        {
            get { return this.paramClient; }
        }

        public void display()
        {
            Console.WriteLine(this.Id + " | " + this.Fullname + " | " + this.ParamClient.Type);
        }
    }
}
