// COMPILADORES 2023B
// OCTAVIO DANIEL RODRIGUEZ GONZALEZ
// PRIMER LL1 (IF)

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class leonLL1 {
	
	static String RENGLON, LEX;
	static int Posicion = 0;
	static String Entrada;
	
	static String term[] = new String[13];
	static String nt[] = new String[7];
	
	static int M[][] = new int [13][7];
	static String prod[] = new String[14];
	static String pila[] = new String[1000];
	static int tope = -1;
	
	static String a;
	static String X;
	
	public static void print_pila(){
		System.out.print("\n");
		for(int i=0; i<=tope; i++)
			System.out.print(pila[i]+" ");
	}
	
	public static void push(String X){
		if(tope==999){
			System.out.println("Pila llena");
			System.exit(4);	
		}
		if(!X.equals("epsilon"))
			pila[++tope]=X;
	}
	
	public static String pop(){
		if(tope==-1){
			System.out.println("Pila vacia");
			System.exit(4);	
		}
		return(pila[tope--]);
	}
	
	public static int es_terminal(String X){
		for(int i=0;i<=12;i++){
			if(X.equals(term[i]))
				return(i);
		}
		return(-1);
	}
	
	public static int es_noterminal(String X){
		for(int i=0;i<=6;i++){
			if(X.equals(nt[i]))
				return(i);
		}
		return(-1);
	}
	
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
		System.out.println("ERROR en el token: ["+a+"] cerca del renglon: "+RENGLON);
		System.exit(4);
	}
	
	public static String lee_tok(File xFile){
		try{
			FileReader fr = new FileReader(xFile);
			BufferedReader br = new BufferedReader(fr);
			long NoSirve = br.skip(Posicion);
			String linea = br.readLine();
			Posicion = Posicion + linea.length() + 2;
			String XX = linea;
			linea = br.readLine();
			Posicion = Posicion + linea.length() + 2;
			LEX = linea;
			linea = br.readLine();
			Posicion = Posicion + linea.length() + 2;
			RENGLON = linea;
			fr.close();
			//System.out.print(".");
			return(XX);
		}catch(IOException e) {
			System.out.println("Errorsote"); 
			return("errorsote");
			}
	}
	
	public static void main(String PONCHITO[]){
		term[0]="si";
		term[1]="pa";
		term[2]="pc";
		term[3]="entonces";
		term[4]="otro";
		term[5]="finsi";
		term[6]="num";
		term[7]="id";
		term[8]=">";
		term[9]="<";
		term[10]="=";
		term[11]="read";
		term[12]="eof";
	
		nt[0]="IF";
		nt[1]="INS";
		nt[2]="SIG";
		nt[3]="EXP";
		nt[4]="E";
		nt[5]="OP";
		nt[6]="I";
		
		for(int i=0; i<=12; i++)
			for(int j=0; i<=6; i++)
				M[i][j]=0;
				
		M[0][0]=1;
		M[0][1]=2;
		M[0][6]=13;
		M[4][1]=3;
		M[4][2]=4;
		M[5][1]=3;
		M[5][2]=5;
		M[6][3]=6;
		M[6][4]=7;
		M[7][3]=6;
		M[7][4]=8;
		M[8][5]=9;
		M[9][5]=10;
		M[10][5]=11;
		M[11][1]=2;
		M[11][6]=12;
		
		prod[1]="si pa EXP pc entonces INS SIG";
		prod[2]="I INS";
		prod[3]="epsilon";
		prod[4]="otro INS finsi";
		prod[5]="finsi";
		prod[6]="E OP E";
		prod[7]="num";
		prod[8]="id";
		prod[9]=">";
		prod[10]="<";
		prod[11]="=";
		prod[12]="read";
		prod[13]="IF";
		
		Entrada = PONCHITO[0]+".al";
		if(!xArchivo(Entrada).exists()){
			System.out.println("\n\nError: El archivo no existe: ["+Entrada+"]");
			System.exit(4);
		}
		//System.out.println(es_terminal("fs"));
		//System.out.println(es_noterminal("EXP"));
		
		//push("gato");
		//push("come");
		//push("carne");
		//System.out.println(pop());
		//System.out.println(pop());
		//System.out.println(pop());
		
		push("eof");
		push("IF");
		
		a = lee_tok(xArchivo(Entrada));
		do{
			X=pop();
			System.out.print("a=["+a+"] con X=["+X+"]");
			//pausa();
			if(X.equals("eof") && a.equals("eof"))
				System.out.println("\nHurra! AS exitoso!!");
			else{
				
				if(es_terminal(X)!=-1){
					if(X.equals(a)){
						System.out.print("\nHacer pop y leer siguiente\n");
						print_pila();
						a = lee_tok(xArchivo(Entrada));
					}else
						error();
				} else {
					if(M[es_terminal(a)][es_noterminal(X)]!=0){
						// System.out.println(" ---> Prod["+M[es_terminal(a)][es_noterminal(X)]+"]");
						//pausa();
						String Y[]=prod[M[es_terminal(a)][es_noterminal(X)]].split(" ");
						for(int i = Y.length-1; i>=0; i--)
							push(Y[i]);
						print_pila();
						//pausa();
					} else {
						error();
					}
				}
			}			
		}while(!X.equals("eof"));
		System.out.println("Analisis Sintactico terminado");
	}
}