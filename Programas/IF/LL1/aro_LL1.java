import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class aro_LL1{
    static String a, LEX, RENGLON, X;
    static int Posicion = 0;
	static String entrada = "";

	static String [] term = new String[13];
	static String [] nt = new String[7];

	static int M[][] = new int [13][7];
	static String prod[] = new String[14];
	static String pila[] = new String[1000];
	static int tope = -1;

	static int jump = 0;

	public static void print_pila(){
		System.out.print("\n P - ");
        for(int i = 0 ; i <= tope ; i++){
            System.out.print(pila[i] + " ");
        }
	}

	public static void push(String X){
		if(tope == 999){
			System.out.println("Pila llena");
			System.exit(4);	
		}
		if (!X.equals("epsilon")){
            pila [++tope] = X;
		}
	}
	
	public static String pop(){
		if(tope < 0){
			System.out.println("Pila vacia");
			System.exit(4);	
		}
		return(pila[tope--]);
	}

	public static int es_terminal(String x){
		for (int i = 0; i < term.length; i++) {
			if(x.equals(term[i])){
				return i;
			}
		}
		return -1;
	}
	
	public static int no_terminal(String x){
		for (int i = 0; i < nt.length; i++) {
			if(x.equals(nt[i])){
				return (i);
			}
		}
		return -1;
	}

    public static File xArchivo(String xName){
		File xFile = new File(xName);
		return xFile;
	}

    public static String pausa(){
		BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
		String nada = null;
		try{
			nada = entrada.readLine();
			return(nada);
		} catch(Exception e) {
			System.err.println(e);
		}
		return("");
	}

	public static void limpiarPantalla() {
        String sistemaOperativo = System.getProperty("os.name").toLowerCase();
        Process proceso;

        try {
            if (sistemaOperativo.contains("win")) {
                proceso = new ProcessBuilder("cmd", "/c", "cls").inheritIO().start();
                proceso.waitFor();
            } else {
                proceso = new ProcessBuilder("clear").inheritIO().start();
                proceso.waitFor();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

	public static void error(){
		System.out.println("\7Error en\nTOKEN: [" + a + "]\nRENGLÓN: '"+ RENGLON + "'");
		System.out.println("\n\n\tPresione cualquier tecla para terminar...");
		pausa();
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

	public static String lee_tok(File xFile){
		jump = jumps();
		try{
			FileReader fr = new FileReader(xFile);
			BufferedReader br = new BufferedReader(fr);
			long NoSirve = br.skip(Posicion);
			String linea = br.readLine();
			Posicion += linea.length() + jump;
			String XX = linea;
			linea = br.readLine();
			Posicion += linea.length() + jump;
			LEX = linea;
			linea = br.readLine();
			Posicion += linea.length() + jump;
			RENGLON = linea;
			fr.close();
			return (XX);
		}catch(IOException e){
			System.out.println("No Manches Un Errorsote");
			return "errorsote";
		}
	}

	public static void main(String[] args) {
		term[0] = "si";
		term[1] = "pa";
		term[2] = "pc";
		term[3] = "entonces";
		term[4] = "otro";
		term[5] = "finsi";
		term[6] = "num";
		term[7] = "id";
		term[8] = ">";
		term[9] = "<";
		term[10] = "=";
		term[11] = "read";
		term[12] = "eof";

		nt[0] = "IF";
		nt[1] = "INS";
		nt[2] = "SIG";
		nt[3] = "EXP";
		nt[4] = "E";
		nt[5] = "OP";
		nt[6] = "I";

		for(int i=0; i < term.length; i++){
			for(int j=0; i < nt.length; i++){
				M[i][j]=0;
			}
		}
				
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

		entrada = args[0] + ".al";

		if(!xArchivo(entrada).exists()){
			limpiarPantalla();
			System.out.println("\t\t* * * * * * * * * * ERROR * * * * * * * * * *");
			System.out.println("\nEl archivo '" + entrada + "' no existe");
			System.out.println("Presiona enter para terminar...");
			pausa();
			System.exit(4);
		}

		// limpiarPantalla();

		push("eof");
        push("IF");

		a = lee_tok(xArchivo(entrada));

		do{
			X = pop();
			// System.out.println(no_terminal(X));
			if(X.equals("eof") && a.equals("eof")){
				System.out.println("Hurra bien bien AS exitoso!!!");
			}else{
				if(es_terminal(X) != -1){
					if(X.equals(a)){
                        print_pila();
						a = lee_tok(xArchivo(entrada));
					}else{
						error();
					}
				}else{
					if(M[es_terminal(a)][no_terminal(X)] != 0){
                       	String Y[]= prod[M[es_terminal(a)][no_terminal(X)]].split(" ");
                        for(int i = Y.length-1; i >= 0; i--){
                        	push(Y[i]);
						}
                        print_pila();
                    }else{
                        error();
                    }
				}
			}

		}while(!X.equals("eof"));
		
		System.out.println("Analisis sintactico terminado con exito\n");
		System.out.println("Presiona enter para terminar...");
		// pausa();
	}
}