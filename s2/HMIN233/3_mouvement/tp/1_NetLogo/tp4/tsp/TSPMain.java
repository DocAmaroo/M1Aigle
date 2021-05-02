package tsp;

import java.util.ArrayList;
import java.util.*;

public class TSPMain{
public static void main(String[] args) {
		int nombreVilles = 10;
		int distances[][] = new int[nombreVilles][nombreVilles];
		int chemin[] = new int[nombreVilles - 1];
		// generer les distances entre chaque villes
		// la ligne (dimension 1) représente la ville de depart
		// la colonne (dimension 2) représente la ville d'arrive
		genererDistance(distances,nombreVilles);
		afficherDistances(distances,nombreVilles);
		// pour comparaison, voici un algo de bruteforce simple.
		// desactivez le au dela de 12 villes.
		int best = bruteForce(distances,nombreVilles);
		System.out.println("brute force : "+ best);

		// a vous de jouer !
		AlgoGenTSP gen = new AlgoGenTSP(distances,nombreVilles);

		// ....
	}

	public static void genererDistance(int distances[][],int nombreVilles)
	{
			for(int x = 0 ; x < nombreVilles ; x++)
				for(int y = 0 ; y < nombreVilles ; y++)
					distances[x][y] = (int)(Math.random() * 1000);
	}

	public static void afficherDistances(int distances[][],int nombreVilles)
	{
			for(int x = 0 ; x < nombreVilles ; x++)
			{
				for(int y = 0 ; y < nombreVilles ; y++)
					System.out.print(distances[x][y] + "\t");
				System.out.print('\n');
			}
	}

	public static void genererChemin(int chemin[],int nombreVilles)
	{
			for(int x = 0 ; x < nombreVilles - 1 ; x++)
				chemin[x] = x +1;

			for(int rep = 10; rep > 0; rep--)
			{
				int x = (int)(Math.random() * (nombreVilles - 1));
				int y = (int)(((Math.random() * (nombreVilles - 2)) + x + 1)) % (nombreVilles - 1);
				int temp = chemin[x];
				chemin[x] = chemin[y];
				chemin[y] = temp;
			}
	}

	public static void afficherChemin(int chemin[],int nombreVilles)
	{
		System.out.print("0->");	
		for(int x = 0 ; x < nombreVilles-2 ; x++)
		{
			System.out.print(chemin[x]+"->");	
		}
		System.out.println(chemin[nombreVilles-2]+"->0");
	}

	public static int calculerCout(int distances[][],int chemin[],int nombreVilles)
	{
		int precedent = 0;
		int result = 0;
		for(int x = 0 ; x < nombreVilles-1 ; x++)
		{
			result += distances[precedent][chemin[x]];
			precedent = chemin[x];
		}
		result += distances[precedent][0];
		return result;
	}

	//pas le plus elegant ...
	public static int bruteForce(int distances[][],int nombreVilles)
	{
		int chemin[] = new int[nombreVilles - 1];
		return bruteForceRec(distances,nombreVilles,0,chemin);
	}

	public static int bruteForceRec(int distances[][],int nombreVilles,int pos,int chemin[])
	{
		int score = 0;
		int scorecmp = 0;
		boolean used = false;
		for(int i = 1; i < nombreVilles; i++)
		{
			used = false;
			for(int j = 0; j < pos; j++)
			{
				if(chemin[j] == i)
					used = true;
			}
			if(!used)
			{
				chemin[pos] = i;
				if(pos == nombreVilles - 2)
				{
					scorecmp = calculerCout(distances,chemin,nombreVilles);
					if(scorecmp < score || score == 0)
						score = scorecmp;
				}
				else
				{
					scorecmp = bruteForceRec(distances,nombreVilles,pos+1,chemin);
					if(scorecmp < score || score == 0)
						score = scorecmp;
				}			
			}
		}
		return score;
	}
}
