import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class aro_SLR {
    static String stack[] = new String[1000];
	static int max = -1; // Limit of the stack
	
	static String a;
	static String S;
	
	static String term[] = new String[13];
	static String nt[] = new String[8];
	static int M[][] = new int [25][21];
	
	static String pi[] = new String[14];
	static int lpd[] = new int[14];
	
	static String row, LEX;
	static int position = 0;
	static String in;
	
	static int jump; // jump to 1 (linux) or 2 (windows) char
	
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
	
	public static void main(String args[]){
		term[0] = "si";
		term[1] = "pa";
		term[2] = "pc";
		term[3] = "entonces";
		term[4] = "num";
		term[5] = "id";
		term[6] = ">";
		term[7] = "<";
		term[8] = "=";
		term[9] = "read";
		term[10] = "otro";
		term[11] = "finsi";
		term[12] = "eof";

		nt[0] = "IFP";
		nt[1] = "IF";
		nt[2] = "INS";
		nt[3] = "SIG";
		nt[4] = "EXP";
		nt[5] = "E";
		nt[6] = "OP";
		nt[7] = "I";

		pi[0] = "IFP";
		pi[1] = "IF";
		pi[2] = "INS";
		pi[3] = "INS";
		pi[4] = "SIG";
		pi[5] = "SIG";
		pi[6] = "EXP";
		pi[7] = "E";
		pi[8] = "E";
		pi[9] = "OP";
		pi[10] = "OP";
		pi[11] = "OP";
		pi[12] = "I";
		pi[13] = "I";

		lpd[0] = 1;
		lpd[1] = 7;
		lpd[2] = 2;
		lpd[3] = 0;
		lpd[4] = 3;
		lpd[5] = 1;
		lpd[6] = 3;
		lpd[7] = 1;
		lpd[8] = 1;
		lpd[9] = 1;
		lpd[10] = 1;
		lpd[11] = 1;
		lpd[12] = 1;
		lpd[13] = 1;
		
		for(int i = 0; i < M.length; i++){
			for(int j = 0; j < M[i].length; j++){
				M[i][j] = 0;
			}
		}
		
		M[0][0] = 2;
		M[0][14] = 1;
		M[1][12] = 666;
		M[2][1] = 3;
		M[3][3] = 5;
		M[3][4] = 6;
		M[3][5] = 7;
		M[3][17] = 4;
		M[3][18] = 5;
		M[4][2] = 8;
		M[5][6] = 10;
		M[5][7] = 11;
		M[5][8] = 12;
		M[5][19] = 9;
		M[6][2] = -7;
		M[6][6] = -7;
		M[6][7] = -7;
		M[6][8] = -7;
		M[7][2] = -8;
		M[7][6] = -8;
		M[7][7] = -8;
		M[7][8] = -8;
		M[8][3] = 13;
		M[9][4] = 6;
		M[9][5] = 6;
		M[9][18] = 14;
		M[10][4] = -9;
		M[10][5] = -9;
		M[11][4] = -10;
		M[11][5] = -10;
		M[12][4] = -11;
		M[12][5] = -11;
		M[13][0] = 2;
		M[13][9] = 17;
		M[13][10] = -3;
		M[13][11] = -3;
		M[13][14] = 18;
		M[13][15] = 15;
		M[13][20] = 16;
		M[14][2] = -6;
		M[15][10] = 20;
		M[15][11] = 21;
		M[15][16] = 19;
		M[16][0] = 2;
		M[16][9] = 17;
		M[16][10] = -3;
		M[16][11] = -3;
		M[16][14] = 18;
		M[16][15] = 22;
		M[16][20] = 16;
		M[17][0] = -12;
		M[17][9] = -12;
		M[17][10] = -12;
		M[17][11] = -12;
		M[18][0] = -13;
		M[18][9] = -13;
		M[18][10] = -13;
		M[18][11] = -13;
		M[19][0] = -1;
		M[19][9] = -1;
		M[19][10] = -1;
		M[19][11] = -1;
		M[19][12] = -1;
		M[20][0] = 2;
		M[20][9] = 17;
		M[20][10] = -3;
		M[20][11] = -3;
		M[20][14] = 18;
		M[20][15] = 23;
		M[20][20] = 16;
		M[21][0] = -5;
		M[21][9] = -5;
		M[21][10] = -5;
		M[21][11] = -5;
		M[21][12] = -5;
		M[22][10] = -2;
		M[22][11] = -2;
		M[23][11] = 24;
		M[24][0] = -4;
		M[24][9] = -4;
		M[24][10] = -4;
		M[24][11] = -4;
		M[24][12] = -4;

		clearScreen();
		
		in = args[0] + ".al";
		if(!xArchivo(in).exists()){
			System.out.println("\n\nError: El archivo no existe: [" + in + "]");
			System.exit(4);
		}
		
		push("0");
		a = lee_tok(xArchivo(in));

		do{
			S = stack[max];
			
			// System.out.println("S = [" + S + "]");
			// System.out.println("a = [" + a + "]");
			
			int xxx=Integer.parseInt(S);
			
			// System.out.println("xxx = [" + xxx + "]");
			// pausa();

			// System.out.println("S = " + S + " " + a + " " + M[Integer.parseInt(S)][es_terminal(a)]);
			// pausa();

			if(M[Integer.parseInt(S)][es_terminal(a)] == 666){
				System.out.println("\n\n\tTodo bien");
				System.exit(0);
			} else {
				if(M[Integer.parseInt(S)][es_terminal(a)] > 0){
					push(a);
					push(M[Integer.parseInt(S)][es_terminal(a)] + "");
					a = lee_tok(xArchivo(in));
					print_pila();
					// pausa();
				} else {
					if(M[Integer.parseInt(S)][es_terminal(a)] < 0){
						for(int i = 1; i <= lpd[M[Integer.parseInt(S)][es_terminal(a)]*-1] * 2; i++){
							pop();
						}
						int e = Integer.parseInt(stack[max]);
						push(pi[M[Integer.parseInt(S)][es_terminal(a)] * -1]);
						if(M[e][no_terminal(stack[max])] == 0){
							error();
						} else {
							push(M[e][no_terminal(stack[max])] + "");
						}
					} else {
						error();
					}
				}	
			}
		}while(true);
	}	
}