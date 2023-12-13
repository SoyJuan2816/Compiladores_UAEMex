import java.io.*;

public class aro_Primer_AL{

    static int filesize = 0;
    static char linea [];
    
    static int a_a = 0;
    static int a_i = 0;
	static int Renglon = 0;
	static int c;

	static int COMIENZO;
	static int ESTADO;

	static String Mi_Lex = "";
	static String Mi_Token = "";

	static String entrada, salida;

    static boolean fin_archivo = false;

	public static boolean es_delim(int x){
		if((x == 9) || (x == 10) || (x == 13) || (x == 32)){
			return true;
		}
		return false;
	}

	public static boolean es_vocal(int x){
		if((x == 97) || (x == 101) || (x == 105) || (x == 111) || (x == 117) || (x == 65) || (x == 69) || (x == 73) || (x == 79) || (x == 85)){
			return true;
		}
		return false;
	}

	public static String ob_lex(){
		String x = "";
		for(int i = a_i; i < a_a; i++){
			x += linea[i];
		}
		return x;
	}

	public static boolean es_digito(int x){
		if(c >= 48 && c <= 57){
			return true;
		}
		return false;
	}
    
    public static boolean creaEscribeArchivo(File xFile, String mensaje){
		try {
			PrintWriter fileOut = new PrintWriter(
			new FileWriter(xFile, true) );
			fileOut.println(mensaje);
			fileOut.close();
			return true;
		} catch (IOException ex) {
			return false;
		}
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

    public static char[] abreLeeCierra(String xName){
		File xFile = new File(xName);
		char[] data;
		try{
			FileReader fin = new FileReader(xFile);
			filesize = (int)xFile.length();
			data= new char[filesize+1];
			fin.read(data,0,filesize);
			data[filesize]=' ';
			filesize++;
			return(data);
		} catch (FileNotFoundException exc){}
		catch (IOException exc){}
		return null;
	}

    public static int lee_car(){
        if(a_a<=filesize-1){
           if(linea[a_a]==10){
               Renglon++;
           }
           return(linea[a_a++]);
        }else{
           fin_archivo=true;
           return 255;
        }
    }

	public static void error(){
		char errorChar = linea[a_a];
		System.out.println("Error en el caracter '" + errorChar + "' en el renglón " + Renglon);
		System.out.println("\n\n\tPresione cualquier tecla para terminar...");
		pausa();
		System.exit(4);
	}
	

	public static int DIAGRAMA(){
		a_a = a_i;

		switch(COMIENZO){
			case 0:
				COMIENZO = 4;
				break;
			case 4:
				COMIENZO = 8;
				break;
			case 8:
				COMIENZO = 11;
				break;
			case 11:
				COMIENZO = 14;
				break;
			case 14:
				error();
				break;
		}
		return COMIENZO;
	}

	public static String TOKEN(){
		do{
			switch(ESTADO){
				case 0:
					c = lee_car();
					if(c == '#'){
						ESTADO = 1;
					}else{
						ESTADO = DIAGRAMA();
					}
					break;
				case 1:
					c = lee_car();
					if(es_digito(c)){
						ESTADO = 2;
					}else{
						ESTADO = DIAGRAMA();
					}
					break;
				case 2:
					c = lee_car();
					if(es_digito(c)){
						ESTADO = 2;
					}else{
						ESTADO = 3;
					}
					break;
				case 3:
					a_a--;
					Mi_Lex = ob_lex();
					a_i = a_a;
					return ("E1");
				case 4:
					c = lee_car();
					if(c == '$'){
						ESTADO = 5;
					}else{
						ESTADO = DIAGRAMA();
					}
					break;
				case 5:
					c = lee_car();
					if(c == '$'){
						ESTADO = 6;
					}else{
						if(es_vocal(c)){
							ESTADO = 5;
						}else{
							ESTADO = DIAGRAMA();
						}
					}
					break;
				case 6:
					c = lee_car();
					if(c == '$'){
						ESTADO = 7;
					}else{
						ESTADO = DIAGRAMA();
					}
					break;
				case 7:
					Mi_Lex = ob_lex();
					a_i = a_a;
					return ("E2");
				case 8:
					c = lee_car();
					if(es_vocal(c)){
						ESTADO = 9;
					}else{
						ESTADO = DIAGRAMA();
					}
					break;
				case 9:
					c = lee_car();
					if(es_digito(c) || es_vocal(c)){
						ESTADO = 9;
					}else{
						ESTADO = 10;
					}
					break;
				case 10:
					a_a--;
					Mi_Lex = ob_lex();
					a_i = a_a;
					return ("E3");
				case 11:
					c = lee_car();
					if(es_delim(c)){
						ESTADO = 12;
					}else{
						ESTADO = DIAGRAMA();
					}
					break;
				case 12:
					c = lee_car();
					if(es_delim(c)){
						ESTADO = 12;
					}else{
						ESTADO = 13;
					}
					break;
				case 13:
					a_a--;
					Mi_Lex = ob_lex();
					a_i = a_a;
					return ("Nosirve");
				case 14:
					c = lee_car();
					if(c == 255){
						ESTADO = 15;
					}else{
						ESTADO = DIAGRAMA();
					}
					break;
				case 15:
					Mi_Lex = ob_lex();
					a_i = a_a;
					return ("Nosirve");
			}
		}while(true);
	}
    
    public static void main(String[] arguments) {
		limpiarPantalla();

		if(arguments.length == 0){
			System.out.println("\7ERROR: Falta proporcionar el archivo de entrada");
			System.exit(4);
		}
		
		entrada = arguments[0] + ".fte";
		salida = arguments[0] + ".al";

		if(!xArchivo(entrada).exists()){
			System.out.println("\7ERROR: El archivo " + entrada + " no existe");
			System.exit(4);
		}

		linea = abreLeeCierra(entrada);

		xArchivo(salida).delete();

        do{
			ESTADO = 0;
			COMIENZO = 0;
			Mi_Token = TOKEN();
			if(!Mi_Token.equals("Nosirve")){
				creaEscribeArchivo(xArchivo(salida), Mi_Token);
				creaEscribeArchivo(xArchivo(salida), Mi_Lex);
				creaEscribeArchivo(xArchivo(salida), Renglon + "");
			}
		}while(!fin_archivo);
		System.out.println("Analizador Lexicográfico terminado\n\n\tPresione cualquier tecla para terminar...");
		pausa();
    }
}