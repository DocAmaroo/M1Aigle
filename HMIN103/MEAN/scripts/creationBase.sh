mongoimport --db SUPERVENTES --collection membres --file ../jsons/membres.json --jsonArray --drop
mongoimport --db SUPERVENTES --collection produits --file ../jsons/produits.json --jsonArray --drop
mongoimport --db SUPERVENTES --collection marques --file ../jsons/marques.json --jsonArray --drop