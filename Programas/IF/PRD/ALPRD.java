// COMPILADORES 2023B
// OCTAVIO DANIEL RODRIGUEZ GONZALEZ
// Primer PRD

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ALPRD {
	
	static String CAB, RENGLON, LEX;
	static int Posicion = 0;
	static String Entrada;

	static int jump = 0;
	
	public static File xArchivo(String xName){
		File xFile = new File(xName);
		return xFile;
	}
	
	public static String pausa(){
		BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
		String nada=null;
		try{
			nada = entrada.readLine();
			return(nada);
		} catch(Exception e) {
			System.err.println(e);
		}
		return("");
	}
	
	public static void error(){
		System.out.println("ERROR en el token: ["+CAB+"]"+RENGLON);
		System.exit(4);
	}

	public static int jumps() {
        String OperatingSystem = System.getProperty("os.name").toLowerCase();

        try {
            if (OperatingSystem.contains("win")) {
                return 2;
            } else {
                return 1;
            }
        } catch (Exception e) {
			e.printStackTrace();
			return 0;
        }
    }
	
	public static void sig_cab(File xFile){
		jump = jumps();
		try{
			FileReader fr = new FileReader(xFile);
			BufferedReader br = new BufferedReader(fr);
			long NoSirve = br.skip(Posicion);
			String linea = br.readLine();
			Posicion = Posicion + linea.length() + jump;
			CAB = linea;
			linea = br.readLine();
			Posicion = Posicion + linea.length() + jump;
			LEX = linea;
			linea = br.readLine();
			Posicion = Posicion + linea.length() + jump;
			RENGLON = linea;
			fr.close();
			System.out.print(".");
		}catch(IOException e) {System.out.println("Errorsote");}
	}
	
	static void aso(String t){
		if(CAB.equals(t)){
			sig_cab(xArchivo(Entrada));
		}
		else
			error();
	}
	
	static void IF(){
		if(CAB.equals("si")){
			aso("si");
			aso("pa");
			EXP();
			aso("pc");
			aso("e");
			INS();
			SIG();
		} else
			error();
	}
	
	static void EXP(){
		if(CAB.equals("id")||CAB.equals("num")){
			E();
			OP();
			E();
		} else 
			error();
	}
	
	static void E(){
		if(CAB.equals("num"))
			aso("num");
		else if(CAB.equals("id"))
			aso("id");
		else
			error();
	}
	
	static void OP(){
		if(CAB.equals(">"))
			aso(">");
		else if(CAB.equals("<"))
			aso("<");
		else if(CAB.equals("="))
			aso("=");
		else
			error();
	}
	
	static void INS(){
		if(CAB.equals("a")||CAB.equals("si")){
			I();
			INS();
		}
	}
	
	static void I(){
		if(CAB.equals("a"))
			aso("a");
		else if(CAB.equals("si"))
			IF();
		else
			error();
	}
	
	static void SIG(){
		if(CAB.equals("o")){
			aso("o");
			INS();
			aso("fs");
		} else if (CAB.equals("fs"))
			aso("fs");
		else 
			error();
	}
	
	public static void main(String PONCHITO[]){
		Entrada = PONCHITO[0]+".al";
		if(!xArchivo(Entrada).exists()){
			System.out.println("\n\nError: El archivo no existe: ["+Entrada+"]");
			System.exit(4);
		}
		sig_cab(xArchivo(Entrada));
		IF();
		if(!CAB.equals("eof"))
			error();
		System.out.println("Analisis Sintactico Correcto...");
	}
}