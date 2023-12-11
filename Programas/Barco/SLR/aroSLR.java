import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class aroSLR {
    static String stack[] = new String[1000];
	static int max = -1; // Limit of the stack
	
	static String a;
	static String S;
	
	static String term[] = new String[13];
	static String nt[] = new String[4];
	static int M[][] = new int [24][17];
	
	static String pi[] = new String[12];
	static int lpd[] = new int[12];
	
	static String row, LEX;
	static int position = 0;
	static String in;
	
	// Variables para generación de código
	static double M_v;
	static double PC_x;
	static double PC_y;
	static double PC_d;
	static double T_x;
	static double T_y;
	static double T_d;
	static double TP_x;
	static double TP_y;
	static double TP_d;
	
	static double temp;

	static int jump; // jump to 1 (linux) or 2 (windows) char

	public static void clearScreen() {
        String OperatingSystem = System.getProperty("os.name").toLowerCase();
        Process process;

        try {
            if (OperatingSystem.contains("win")) {
                process = new ProcessBuilder("cmd", "/c", "cls").inheritIO().start();
                process.waitFor();
            } else {
                process = new ProcessBuilder("clear").inheritIO().start();
                process.waitFor();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
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

	public static void print_pila(){
		System.out.print("\nPila ==> ");
		for(int i = 0; i <= max; i++){
			System.out.print(stack[i] + " ");
		}
	}
	
	public static void push(String X){
		if(max == 999){
			System.out.println("stack llena");
			System.exit(4);	
		}
		if(!X.equals("epsilon")){
			stack[++max] = X;
		}
	}
	
	public static String pop(){
		if(max == -1){
			System.out.println("stack vacia");
			System.exit(4);	
		}
		return (stack[max--]);
	}
	
	public static int es_terminal(String X){
		for(int i = 0; i <= 12; i++){
			if(X.equals(term[i])){
				return(i);
			}
		}
		return (-1);
	}
	
	public static int no_terminal(String X){
		for(int i = 0; i <= 7; i++){
			if(X.equals(nt[i])){
				return(i + 13);
			}
		}
		return (-1);
	}
	
	public static File xArchivo(String xName){
		File xFile = new File(xName);
		return xFile;
	}
	
	public static String pausa(){
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String nada=null;
		try{
			nada = in.readLine();
			return(nada);
		} catch(Exception e) {
			System.err.println(e);
		}
		return("");
	}
	
	public static void error(){
		System.out.println("ERROR en el token: [" + a + "] cerca del row: " + row);
		System.exit(4);
	}
	
	public static String lee_tok(File xFile){
		jump = jumps();
		try{
			FileReader fr = new FileReader(xFile);
			BufferedReader br = new BufferedReader(fr);
			long NoSirve = br.skip(position);
			String line = br.readLine();
			position = position + line.length() + jump;
			String XX = line;
			line = br.readLine();
			position = position + line.length() + jump;
			LEX = line;
			line = br.readLine();
			position = position + line.length() + jump;
			row = line;
			fr.close();
			return(XX);
		}catch(IOException e) {
			System.out.println("Errorsote"); 
			return("errorsote");
		}
	}

	public static void gen_code_shift(int Shift){
		switch(Shift){
			case 22:
				System.out.println("En este momento el lexema leído es: " + LEX);
				temp = Double.parseDouble(LEX);
				// pausa();
				break;
		}
	}
	
	public static void gen_code_reduce(int Reduce){
		switch(Reduce){
			case -1:
				T_x = PC_x + T_x;
				T_y = PC_y + T_y;
				T_d = PC_d + T_d;
				break;
			case -2:
				T_x = PC_x;
				T_y = PC_y;
				T_d = PC_d;
				break;
			case -3:
				PC_x = 0;
				PC_y = M_v;
				PC_d = M_v;
				System.out.println("PCx = " + PC_x + ", PCy = " + PC_y + ", PCd = " + PC_d);
				// pausa();
				break;
			case -4:
				PC_x = 0;
				PC_y = -1 * M_v;
				PC_d = M_v;
				break;
			case -5:
				PC_x = M_v;
				PC_y = 0;
				PC_d = M_v;
				break;
			case -6:
				PC_x = -1 * M_v;
				PC_y = 0;
				PC_d = M_v;
				break;
			case -7:
				PC_x = M_v;
				PC_y = M_v;
				PC_d = M_v * Math.sqrt(2);
				break;
			case -8:
				PC_x = M_v;
				PC_y = -1 * M_v;
				PC_d = M_v * Math.sqrt(2);
				break;
			case -9:
				PC_x = -1 * M_v;
				PC_y = M_v;
				PC_d = M_v * Math.sqrt(2);
				break;
			case -10:
				PC_x = -1 * M_v;
				PC_y = -1 * M_v;
				PC_d = M_v * Math.sqrt(2);
				break;
			case -11:
				M_v = temp;
				System.out.println("M.v = " + M_v);
				// pausa();
				break;
		}
	}
	
	public static void main(String args[]){
		term[0] = "n";
		term[1] = "s";
		term[2] = "e";
		term[3] = "o";
		term[4] = "ne";
		term[5] = "se";
		term[6] = "no";
		term[7] = "so";
		term[8] = "(";
		term[9] = "num";
		term[10] = ")";
		term[11] = ",";
		term[12] = "eof";

		nt[0] = "TP";
		nt[1] = "T";
		nt[2] = "PC";
		nt[3] = "M";

		pi[0] = "TP";
		pi[1] = "T";
		pi[2] = "T";
		pi[3] = "PC";
		pi[4] = "PC";
		pi[5] = "PC";
		pi[6] = "PC";
		pi[7] = "PC";
		pi[8] = "PC";
		pi[9] = "PC";
		pi[10] = "PC";
		pi[11] = "M";

		lpd[0] = 1;
		lpd[1] = 3;
		lpd[2] = 1;
		lpd[3] = 2;
		lpd[4] = 2;
		lpd[5] = 2;
		lpd[6] = 2;
		lpd[7] = 2;
		lpd[8] = 2;
		lpd[9] = 2;
		lpd[10] = 2;
		lpd[11] = 3;
		
		for(int i = 0; i < M.length; i++){
			for(int j = 0; j < M[i].length; j++){
				M[i][j] = 0;
			}
		}
		
		M[0][0] = 3;
		M[0][1] = 1;
		M[0][2] = 5;
		M[0][3] = 3;
		M[0][4] = 5;
		M[0][5] = 6;
		M[0][6] = 7;
		M[0][7] = 4;
		M[1][12] = 666;
		M[0][14] = 1;
		M[0][15] = 2;
		M[2][11] = 11;
		M[2][12] = -2;
		M[3][8] = 13;
		M[3][16] = 12;
		M[4][8] = 13;
		M[4][16] = 14;
		M[5][2] = 15;
		M[5][8] = 13;
		M[6][8] = 13;
		M[6][16] = 16;
		M[7][8] = 13;
		M[7][16] = 17;
		M[8][8] = 13;
		M[8][16] = 18;
		M[9][8] = 13;
		M[9][16] = 19;
		M[10][7] = 20;
		M[10][8] = 13;
		M[11][0] = 3;
		M[11][1] = 4;
		M[11][2] = 5;
		M[11][3] = 6;
		M[11][4] = 7;
		M[11][5] = 8;
		M[11][6] = 9;
		M[11][7] = 10;
		M[11][14] = 21;
		M[11][15] = 2;
		M[12][11] = -3;
		M[12][12] = -3;
		M[13][9] = 22;
		M[14][11] = -4;
		M[14][12] = -4;
		M[15][11] = -5;
		M[15][12] = -5;
		M[16][11] = -6;
		M[16][12] = -6;
		M[17][11] = -7;
		M[17][12] = -7;
		M[18][11] = -8;
		M[18][12] = -8;
		M[19][11] = -9;
		M[19][12] = -9;
		M[20][11] = -10;
		M[20][12] = -10;
		M[21][12] = -1;
		M[22][10] = 23;
		M[23][11] = -11;
		M[23][12] = -11;

		
		in = args[0] + ".al";
		if(!xArchivo(in).exists()){
			System.out.println("\n\nError: El archivo no existe: [" + in + "]");
			System.exit(4);
		}
		
		push("0");
		a = lee_tok(xArchivo(in));
		do{
			S = stack[max];
			
			System.out.println("S = [" + S + "]");
			System.out.println("a = [" + a + "]");
			
			int xxx=Integer.parseInt(S);
			
			System.out.println("xxx = [" + xxx + "]");

			System.out.println("S = " + S + " " + a + " " + M[Integer.parseInt(S)][es_terminal(a)]);

			if(M[Integer.parseInt(S)][es_terminal(a)] == 666){
				System.out.println("Todo bien");
				System.out.println("Tx = " + T_x + ", Ty = " + T_y + ", Td = " + T_d);
				System.exit(0);
			} else {
				if(M[Integer.parseInt(S)][es_terminal(a)] > 0){
					push(a);
					push(M[Integer.parseInt(S)][es_terminal(a)] + "");
					gen_code_shift(M[Integer.parseInt(S)][es_terminal(a)]);
					a = lee_tok(xArchivo(in));
					print_pila();
					// pausa();
				} else {
					if(M[Integer.parseInt(S)][es_terminal(a)] < 0){
						for(int i = 1; i <= lpd[M[Integer.parseInt(S)][es_terminal(a)]*-1] * 2; i++){
							pop();
						}

						gen_code_reduce(M[Integer.parseInt(S)][es_terminal(a)]);
						print_pila();
						System.out.println("Haciendo reducción");

						int e = Integer.parseInt(stack[max]);
						push(pi[M[Integer.parseInt(S)][es_terminal(a)] * -1]);
						print_pila();

						System.out.println("E = "+e);
						System.out.println(stack[max]);
						System.out.println(no_terminal(stack[max]));
						if(M[e][no_terminal(stack[max])] == 0){
							error();
						} else {
							push(M[e][no_terminal(stack[max])] + "");
							print_pila();
							System.out.println("Haciendo go-to");
						}
					} else {
						error();
					}
				}	
			}
		}while(true);
	}	
}