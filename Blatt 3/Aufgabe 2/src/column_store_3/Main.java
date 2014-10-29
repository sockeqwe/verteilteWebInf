package column_store_3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;

public class Main {
	
	public static void main(String[] args) {

    //
		//import data
    //
		TransactionsColumn columnstore = new TransactionsColumn();
		TransactionsRows rowstore = new TransactionsRows();
		BufferedReader br;
		String strLine;
		String[] aryLine;
		try {
			br = new BufferedReader(new FileReader("3m.txt"));
			while((strLine = br.readLine()) != null){
				aryLine = strLine.split("\t");
				columnstore.add(new BigInteger(aryLine[0]), Integer.parseInt(aryLine[1]), Integer.parseInt(aryLine[2]), Integer.parseInt(aryLine[3]), Long.parseLong(aryLine[4]));
				rowstore.add(new Row(new BigInteger(aryLine[0]), Integer.parseInt(aryLine[1]), Integer.parseInt(aryLine[2]), Integer.parseInt(aryLine[3]), Long.parseLong(aryLine[4])));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

    //
    // Column Store
    //

		System.out.println("Column Store:");
		//init Timer
		long time_start = System.currentTimeMillis();
    //Teilaufgabe b
		int countColumnStore = columnstore.getAnzahlLetzteNTage(30);;
		//Teilaufgabe c
		QueryResult colRes = columnstore.statistikNTage(30);
		long time_columnstore = System.currentTimeMillis()-time_start;


    System.out.println("Anzahl der abgewickelten Transaktionen in den letzten 30 Tagen: "+countColumnStore);
    System.out.println("Statistiken über Umsatz und Anzahl der Kunden pro Geschäft in den letzten 30 Tagen:");
    colRes.print();

		System.out.println("");
		System.out.println("----------------------------------------- \nRow Store:");


    //
    // RowStore
    //

		time_start = System.currentTimeMillis();

		//Teilaufgabe b
    int countRowStore = rowstore.getAnzahlLetzteNTage(30);

		//Teilaufgabe c
		QueryResult statsRowStore = rowstore.statistikNTage(30);
    long time_end = System.currentTimeMillis();


    System.out.println("Anzahl der abgewickelten Transaktionen in den letzten 30 Tagen: "+countRowStore);
    System.out.println("Statistiken über Umsatz und Anzahl der Kunden pro Geschäft in den letzten 30 Tagen:");
    statsRowStore.print();


    //
    // Benchmarking results
    //

		System.out.println("");
		System.out.println("\n-> Column Store: " + time_columnstore + " Millisekunden");
		System.out.println("\n-> Row Store: " + (time_end-time_start) + " Millisekunden");
		
	}

	/*
	 * Ergebnis mit 3 Mio. Datensätzen: 
	 * Column Store: 304 Millisekunden
	 * Row Store: 325 Millisekunden
	 * */
	
}
