using System;
using System.Collections.Generic;

namespace MyApp
{
    class Library
    {
        Dictionary<int, string> books = new Dictionary<int, string>();
        
        // --- Accès (lecture et écriture) d'un ouvrage par un ISBN
        public string this[int ISBN]
        {
            get { return books[ISBN]; }
            set { books[ISBN] = value; }
        }

        // --- Accès (lecture) d'une liste d'ouvrage à partir d'un titre
        public List<int> this[string title]
        {
            get
            {
                List<int> isbn = new List<int>();
                foreach(KeyValuePair<int, string> book in books)
                {
                    if (book.Value == title)
                    {
                        isbn.Add(book.Key);
                    }
                }

                return isbn;
            }
        }
    }
}
