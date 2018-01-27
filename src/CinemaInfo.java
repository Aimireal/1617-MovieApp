


import java.util.Scanner;
import java.io.*;

public class CinemaInfo{

	public static String[] getMovieNames() throws IOException {
		int count=0;
		final int SIZE = 21;
		String[] movies = new String[SIZE];

		File myFile = new File("MoviePrices.txt");
		Scanner inputFile = new Scanner(myFile);

		while(inputFile.hasNext() && count < movies.length)
		{
			String str;

			str= inputFile.nextLine();

			String[] parts = str.split(",");
			movies[count]=parts[0];
		 	count++;
		}
		inputFile.close();

		return movies;
		}
	
	
	public static double[] getMoviePrices() throws IOException{
		int count=0;
		final int SIZE = 21;
		double[] prices = new double[SIZE];

		File myFile = new File("MoviePrices.txt");
		Scanner inputFile = new Scanner(myFile);

		while(inputFile.hasNext() && count < prices.length)
		{
			String str;

			str = inputFile.nextLine();
			String[] parts = str.split(",");

		 	prices[count]= Double.parseDouble(parts[1]);
		 	count++;
		}
		inputFile.close();

		return prices;
		}
		
	
		public static double[] getTicketRemaining() throws IOException{
			int count=0;
			final int TICKETS = 21;
			double[] tickets = new double[TICKETS];

			File myFile = new File("MoviePrices.txt");
			Scanner inputFile = new Scanner(myFile);

			while(inputFile.hasNext() && count < tickets.length)
			{
				String str;

				str = inputFile.nextLine();
				String[] parts = str.split(",");

			 	tickets[count]= Double.parseDouble(parts[1]);
			 	count++;
			}
			inputFile.close();

			return tickets;
			}
}