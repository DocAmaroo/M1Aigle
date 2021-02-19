using System;
using System.Collections.Generic;
using System.Text;

namespace MyApp
{
    class Complexe
    {
        private int reel;
        private int imaginaire;

        public Complexe(int reel, int imaginaire)
        {
            this.reel = reel;
            this.imaginaire = imaginaire;
        }

        public int Reel
        {
            get { return this.reel; }
            set { this.reel = value; }
        }

        public int Imaginaire
        {
            get { return this.imaginaire; }
            set { this.imaginaire = value; }
        }

        public static Complexe operator +(Complexe a, Complexe b)
        {

            return new Complexe(a.Reel + b.Reel, a.Imaginaire+ b.Imaginaire);
        }

        public static Complexe operator -(Complexe a, Complexe b)
        {
            return new Complexe(a.Reel - b.Reel, a.Imaginaire- b.Imaginaire);
        }

        public static Complexe operator *(Complexe a, Complexe b)
        {
            return new Complexe(
                (a.reel * b.reel - a.imaginaire * b.imaginaire),
                (a.reel * b.imaginaire + a.imaginaire * b.reel));
        }

        public static Complexe operator *(int f, Complexe c)
        {
            return new Complexe(f * c.Reel, f * c.Imaginaire);
        }

        public void display()
        {
            if (this.imaginaire < 0)
            {
                Console.WriteLine(this.reel + "" + this.imaginaire + "i");
            } else
            {
                Console.WriteLine(this.reel + "+" + this.imaginaire + "i");
            }
        }
    }
}
