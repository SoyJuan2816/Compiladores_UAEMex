import java.io.*;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

public class aro_SLR {
    static String stack[] = new String[10000];
	static int max = -1; // Limit of the stack
	
	static String a = "";
	static String S = "";
	
	static String term[] = new String[31];
	static String nt[] = new String[17];
	static int M[][] = new int [68][48];
	
	static String pi[] = new String[37];
	static int lpd[] = new int[37];
	
	static String row, LEX = "";
	static int position = 0;
	static String in = "";
    static String output = "";
	
	static int jump = 0; // jump to 1 (linux) or 2 (windows) char

    static int countVar = 0; // Count var
    static int countLabel = 0; // Count label

    static String temp = "";

    /**
     * 
     * Arrays for symbols table
     * 
     */
    static ArrayList<String> vars = new ArrayList<>();
    static ArrayList<String> types = new ArrayList<>();

    /**
     * 
     * type vars 
     * 
     */
    static String Type = "";
    static String TypeDec = "";
    static String TypeExpected = "";

    static String DecVar = ""; // Declared Vars
    
    static String LeftVar = ""; // Var Left

    /*
     * 
     * Stack's Program
     * 
     */
    static String Stack_PROG_c[] = new String[100];
    static int maxStack_PROG_c = -1;
    static String Stack_PRIN_c[] = new String[100];
    static int maxStack_PRIN_c = -1;
    static String Stack_BLQ_c[] = new String[100];
    static int maxStack_BLQ_c = -1;
    static String Stack_INST_c[] = new String[100];
    static int maxStack_INST_c = -1;
    static String Stack_COND_c[] = new String[100];
    static int maxStack_COND_c = -1;
    static String Stack_CICLO_c[] = new String[100];
    static int maxStack_CICLO_c = -1;
    static String Stack_ASIG_c[] = new String[100];
    static int maxStack_ASIG_c = -1;
    static String Stack_EXP_c[] = new String[100];
    static int maxStack_EXP_c = -1;
    static String Stack_E_c[] = new String[100];
    static int maxStack_E_c = -1;
    static String Stack_F_c[] = new String[100];
    static int maxStack_F_c = -1;
    static String Stack_S_c[] = new String[100];
    static int maxStack_S_c = -1;
    static String Stack_OP_c[] = new String[100];
    static int maxStack_OP_c = -1;
    
    static String Stack_E_v[] = new String[100];
    static int maxStack_E_v = -1;
    static String Stack_F_v[] = new String[100];
    static int maxStack_F_v = -1;
    static String Stack_S_v[] = new String[100];
    static int maxStack_S_v = -1;
    
    static String Stack_E_t[] = new String[100];
    static int maxStack_E_t = -1;
    static String Stack_F_t[] = new String[100];
    static int maxStack_F_t = -1;
    static String Stack_S_t[] = new String[100];
    static int maxStack_S_t = -1;

    // Left Parts
    static String partLeftEx1;
    static String partLeftEx2;
    static String partLeftEx3;

    static String E1;
    static String E2;
    static String PosA = "";
    static String PosB = "";
    static String PosC = "";

    static String X;
    static String NoSirve;
    static String L1;
    static String L2;

	static int E = 0;

	public static void print_stack(){
		System.out.print("\nPila ==> ");
		for(int i = 0; i <= max; i++){
			System.out.print(stack[i] + " ");
		}
	}
	
	public static void push(String X){
		if(max >= 999){
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
	
	public static int getTerm(String X){
		for(int i = 0; i <= term.length; i++){
			if(X.equals(term[i])){
				return(i);
			}
		}
		return (-1);
	}
	
	public static int getNotTerm(String X){
		for(int i = 0; i < nt.length; i++){
			if(X.equals(nt[i])){
				return(i + term.length);
			}
		}
		return (-1);
	}
	
	public static File xFile(String xName){
		File xFile = new File(xName);
		return xFile;
	}

	public static boolean cwFile(File xFile, String message) {
        PrintWriter fileOut;
        try {
            fileOut = new PrintWriter(new FileWriter(xFile, true));
            if (message == "666") {
                fileOut.print(message);
            } else {
                fileOut.println(message);
            }
            fileOut.close();
            return true;
        } catch (IOException e) {
            return false;
        }

    }
	
	public static String pause(){
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String nada = null;
		try{
			nada = in.readLine();
			return(nada);
		} catch(Exception e) {
			System.err.println(e);
		}
		return("");
	}

	public static void cls() {
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

	public static void error(){
		System.out.println("ERROR en el token: [" + a + "] cerca del renglón: " + row);
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
	
	public static void lee_tok(File xFile){
		jump = jumps();
		try{

			FileReader fr = new FileReader(xFile);
			BufferedReader br = new BufferedReader(fr);
			long NoSirve = br.skip(position);
			String line = br.readLine();
			position = position + line.length() + jump;
			a = line;
			line = br.readLine();
			position = position + line.length() + jump;
			LEX = line;
			line = br.readLine();
			position = position + line.length() + jump;
			row = line;
			fr.close();
		}catch(IOException e) {
			System.out.println("Errorsote"); 
		}
	}

	/*
	 * 
	 * Generate and return the unique name for label: V0, V1, ..., Vn
	 * 
	 */
	public static String GenVar(){
		String var = "V";
		var += String.valueOf(countVar);
		countVar += 1;
		return var;
	}
	
	/*
	 * 
	 * Generate and return the unique name for label: V0, V1, ..., Vn
	 * 
	 */
	public static String GenLabel(){
		String label = "E";
		label += String.valueOf(countLabel);
		countLabel += 1;
		return label;
	}


	/*
	 * 
	 * Chek if any var exist
	 * 
	 */
	public static int existVar(String var){
		for(int i = 0; i < vars.size(); i++){
			if(vars.get(i).equals(var)){
				return i;
			}
		}
		return -1;
	}

	/*
	 * 
	 * Return type of var
	 * 
	 */
	public static String getType(String var){
		int position = existVar(var);
		if(position < 0){
			System.out.println(var + " no es una variable declarada.");
			error();
		}else{
			return types.get(position);
		}
		return null;
	}

	/*
	 * 
	 * Add var on the table
	 * 
	 */
	public static void AddTab(String var, String Type){
		if(existVar(var) > -1){
			System.out.println("La variable '" + var + "' se encuentra duplicada.");
			error();
		}else{
			vars.add(var);
			types.add(Type);
		}
	}

	/*
	 * 
	 * Check if a variable is different between the same in other place
	 * 
	 */
	public static String compareType(String varA, String varB){
		if(varA.equals(varB)){
			return varA;
		}
		System.out.println(varA + " y " + varB + " son diferente tipo de datos.");
		error();
		return null;
	}

	/*
	 * 
	 * Add prefix to the instruction A if the instruction B is float or integer.
	 * 
	 */
	public static String InsArith(String A, String B){ // Change name for this function
		if (B.equals("decimal")) {
			return (A + "F");
		}else{
			return (A + "E");
		}
	}

	public static String VarTemps() {
        String notInteres = "";
        for (int i = 0; i < countVar; i++)
            notInteres += "\n\tPALABRA\tV" + i;
        return (notInteres);
    }

	public static void COD_REDUCE(int R) {
        switch (R) {
            case 1:
                Stack_PROG_c[++maxStack_PROG_c] = DecVar + VarTemps() + "\n\t" + Stack_PRIN_c[maxStack_PRIN_c--] + "\n\tVUEL\t0\n\tFIN\n";
                break;
            case 8:
                Stack_PRIN_c[++maxStack_PRIN_c] = Stack_BLQ_c[maxStack_BLQ_c--];
                break;
            case 11:
                partLeftEx1 = Stack_INST_c[maxStack_INST_c--] + Stack_BLQ_c[maxStack_BLQ_c--];
                Stack_BLQ_c[++maxStack_BLQ_c] = partLeftEx1;
                break;
            case 12:
                Stack_BLQ_c[++maxStack_BLQ_c] = Stack_INST_c[maxStack_INST_c--];
                break;
            case 13:
                Stack_INST_c[++maxStack_INST_c] = Stack_COND_c[maxStack_COND_c--];
                break;
            case 14:
                Stack_INST_c[++maxStack_INST_c] = Stack_ASIG_c[maxStack_ASIG_c--];
                break;
            case 15:
                Stack_INST_c[++maxStack_INST_c] = Stack_CICLO_c[maxStack_CICLO_c--];
                break;
            case 16:
                PosA = GenLabel();
                PosB = GenLabel();
                Stack_COND_c[++maxStack_COND_c] = Stack_EXP_c[maxStack_EXP_c--] + PosA + Stack_BLQ_c[maxStack_BLQ_c--] + "\n\tSAL\t" + PosB + "\n(" + PosA
                        + ")\tMUE\tRC,RC" + Stack_BLQ_c[maxStack_BLQ_c--] + "\n(" + PosB + ")\tMUE\tRC,RC";
                break;
            case 17:
                PosA = GenLabel();
                PosB = GenLabel();
                Stack_COND_c[++maxStack_COND_c] = Stack_EXP_c[maxStack_EXP_c--] + PosA + "\n\tSAL\t" + PosB + "\n(" + PosA + ")\tMUE\tRC,RC"
                        + Stack_BLQ_c[maxStack_BLQ_c--] + "\n(" + PosB + ")\tMUE\tRC,RC";// E1
                break;
            case 18:
                PosA = GenLabel();
                PosB = GenLabel();
                PosC = GenLabel();
                Stack_CICLO_c[++maxStack_CICLO_c] = "\n(" + PosA + ")\tMUE\tRC,RC" + Stack_EXP_c[maxStack_EXP_c--] + PosB + "\n\tSAL\t" + PosC
                        + "\n(" + PosB + ")\tMUE\tRC,RC" + Stack_BLQ_c[maxStack_BLQ_c--] + "\n\tSAL\t" + PosA + "\n(" + PosC
                        + ")\tMUE\tRC,RC";
                break;
            case 19:
                NoSirve = compareType(TypeExpected, Stack_E_t[maxStack_E_t--]);
                Stack_ASIG_c[++maxStack_ASIG_c] = Stack_E_c[maxStack_E_c--] + "\n\tMUE\t" + Stack_E_v[maxStack_E_v--] + "," + LeftVar;
                break;
            case 20:
                L2 = Stack_E_t[maxStack_E_t--];
                L1 = Stack_E_t[maxStack_E_t--];
                compareType(L1, L2);
                Type = L1;

                L2 = Stack_E_c[maxStack_E_c--];
                L1 = Stack_E_c[maxStack_E_c--];
                temp = L1 + L2;

                E2 = Stack_E_v[maxStack_E_v--];
                E1 = Stack_E_v[maxStack_E_v--];
                Stack_EXP_c[++maxStack_EXP_c] = temp + "\n\tMUE\t" + E1 + ",RA\n\tMUE\t" + E2 + ",RB\n\t"
                        + InsArith("CMP", Stack_E_t[++maxStack_E_t]) + "\tRA,RB\n\t" + Stack_OP_c[maxStack_OP_c--];
                ;
                break;
            case 21:
                L1 = compareType(Stack_E_t[maxStack_E_t], Stack_F_t[maxStack_F_t]);
                Stack_E_t[++maxStack_E_t] = L1;
                X = GenVar();
                L1 = Stack_E_c[maxStack_E_c--] + Stack_F_c[maxStack_F_c--] + "\n\tMUE\t" + Stack_E_v[maxStack_E_v--] + ",RA";
                L1 = L1 + "\n\t" + InsArith("SUM", Stack_E_t[maxStack_E_t--]) + "\t" + Stack_F_v[maxStack_F_v--];
                L1 = L1 + "\n\tMUE\tRA," + X;
                Stack_E_c[++maxStack_E_c] = L1;
                Stack_E_v[++maxStack_E_v] = X;
                break;
            case 22:
                L1 = compareType(Stack_E_t[maxStack_E_t], Stack_F_t[maxStack_F_t]);
                Stack_E_t[++maxStack_E_t] = L1;
                X = GenVar();
                L1 = Stack_E_c[maxStack_E_c--] + Stack_F_c[maxStack_F_c--] + "\n\tMUE\t" + Stack_E_v[maxStack_E_v--] + ",RA";
                L1 = L1 + "\n\t" + InsArith("SUB", Stack_E_t[maxStack_E_t--]) + "\t" + Stack_F_v[maxStack_F_v--];
                L1 = L1 + "\n\tMUE\tRA," + X;
                Stack_E_c[++maxStack_E_c] = L1;
                Stack_E_v[++maxStack_E_v] = X;
                break;
            case 23:
                Stack_E_c[++maxStack_E_c] = Stack_F_c[maxStack_F_c--];
                Stack_E_v[++maxStack_E_v] = Stack_F_v[maxStack_F_v--];
                Stack_E_t[++maxStack_E_t] = Stack_F_t[maxStack_F_t--];
                break;
            case 24:
                L1 = compareType(Stack_F_t[maxStack_F_t], Stack_S_t[maxStack_S_t--]);
                Stack_F_t[++maxStack_F_t] = L1;
                X = GenVar();
                L1 = Stack_F_c[maxStack_F_c--] + Stack_S_c[maxStack_S_c--] + "\n\tMUE\t" + Stack_F_v[maxStack_F_v--] + ",RA";
                L1 = L1 + "\n\t" + InsArith("MUL", Stack_F_t[maxStack_F_t--]) + "\t" + Stack_S_v[maxStack_S_v--];
                L1 = L1 + "\n\tMUE\tRA," + X;
                Stack_F_c[++maxStack_F_c] = L1;
                Stack_F_v[++maxStack_F_v] = X;
                break;
            case 25:
                L1 = compareType(Stack_F_t[maxStack_F_t], Stack_S_t[maxStack_S_t--]);
                Stack_F_t[++maxStack_F_t] = L1;
                X = GenVar();
                L1 = Stack_F_c[maxStack_F_c--] + Stack_S_c[maxStack_S_c--] + "\n\tMUE\t" + Stack_F_v[maxStack_F_v--] + ",RA";
                L1 = L1 + "\n\t" + InsArith("DIV", Stack_F_t[maxStack_F_t--]) + "\t" + Stack_S_v[maxStack_S_v--];
                L1 = L1 + "\n\tMUE\tRA," + X;
                Stack_F_c[++maxStack_F_c] = L1;
                Stack_F_v[++maxStack_F_v] = X;
                break;
            case 26:
                Stack_F_c[++maxStack_F_c] = Stack_S_c[maxStack_S_c--];
                Stack_F_v[++maxStack_F_v] = Stack_S_v[maxStack_S_v--];
                Stack_F_t[++maxStack_F_t] = Stack_S_t[maxStack_S_t--];
                break;
            case 27:
                Stack_S_c[++maxStack_S_c] = "";
                Stack_S_v[++maxStack_S_v] = temp + "e";
                Stack_S_t[++maxStack_S_t] = "entero";
                break;
            case 28:
                Stack_S_c[++maxStack_S_c] = "";
                Stack_S_v[++maxStack_S_v] = temp + "f";
                Stack_S_t[++maxStack_S_t] = "decimal";
                break;
            case 29:
                Stack_S_c[++maxStack_S_c] = "";
                Stack_S_v[++maxStack_S_v] = temp;
                Stack_S_t[++maxStack_S_t] = Type;
                break;
            case 30:
                Stack_S_c[++maxStack_S_c] = Stack_E_c[maxStack_E_c--];
                Stack_S_v[++maxStack_S_v] = Stack_E_v[maxStack_E_v--];
                Stack_S_t[++maxStack_S_t] = Stack_E_t[maxStack_E_t--];
                break;
            case 31:
                Stack_OP_c[++maxStack_OP_c] = "\tSMAY\t";
                break;
            case 32:
                Stack_OP_c[++maxStack_OP_c] = "\tSMEN\t";
                break;
            case 33:
                Stack_OP_c[++maxStack_OP_c] = "\tSMAI\t";
                break;
            case 34:
                Stack_OP_c[++maxStack_OP_c] = "\tSMEI\t";
                break;
            case 35:
                Stack_OP_c[++maxStack_OP_c] = "\tSIG\t";
                break;
            case 36:
                Stack_OP_c[++maxStack_OP_c] = "\tSDIF\t";
                break;
        }
    }

	public static void COD_SHIFT(int S) {
        switch (S) {
            case 32:
                temp = LEX;
                break;
            case 33:
                temp = LEX;
                Type = getType(temp);
                break;
            case 8:
                TypeDec = LEX;
                break;
            case 9:
                TypeDec = LEX;
                break;
            case 26:
                AddTab(LEX, TypeDec);
                DecVar = DecVar + "\n\tPALABRA\t" + LEX;
                break;
            case 16:
                LeftVar = LEX;
                TypeExpected = getType(LeftVar);
                break;
            case 31:
                temp = LEX;
                break;
        }
    }

	public static int getMaxStringLength(ArrayList<String> strings) {
        int maxLength = 0;
        for (String str : strings) {
            maxLength = Math.max(maxLength, str.length());
        }
        return maxLength;
    }

	public static String centerText(String text, int width) {
        int padding = width - text.length();
        int leftPadding = padding / 2;
        int rightPadding = padding - leftPadding;
        return " ".repeat(leftPadding) + text + " ".repeat(rightPadding);
    }

	public static void printTab(ArrayList<String> vars, ArrayList<String> types){
		Formatter formatt = new Formatter(System.out);
		
		int maxVarLength = 0;
		int maxTypeLength = getMaxStringLength(types);

		if("VARIABLE".length() >= getMaxStringLength(vars)){
			maxVarLength = "VARIABLE".length();
		}else{
			maxVarLength = getMaxStringLength(vars);
		}
        

		String separator = " +-" + "-".repeat(maxVarLength) + "-+-" + "-".repeat(maxTypeLength) + "-+";

		formatt.format("%s%n", separator);
		formatt.format(" | %-" + maxVarLength + "s | %-" + maxTypeLength + "s |%n", centerText("VARIABLE", maxVarLength), centerText("TIPO", maxTypeLength));
		formatt.format("%s%n", separator);
		
		for(int i = 0; i < vars.size(); i++){
			formatt.format(" | %-" + maxVarLength + "s | %-" + maxTypeLength + "s |%n", centerText(vars.get(i), maxVarLength), centerText(types.get(i), maxTypeLength));
		}

		formatt.format("%s%n", separator);
		formatt.flush();
	}


	
	public static void main(String args[]){
		term[0] = "datos";
        term[1] = "fin_datos";
        term[2] = ":";
        term[3] = "id";
        term[4] = ",";
        term[5] = "{";
        term[6] = "}";
        term[7] = "entero";
        term[8] = "decimal";
        term[9] = "cierto";
        term[10] = "(";
        term[11] = ")";
        term[12] = "haz";
        term[13] = "falso";
        term[14] = "fin_cond";
        term[15] = "mientras";
        term[16] = "fin_mientras";
        term[17] = "asig";
        term[18] = "+";
        term[19] = "-";
        term[20] = "*";
        term[21] = "/";
        term[22] = "ent";
        term[23] = "dec";
        term[24] = "may";
        term[25] = "men";
        term[26] = "mayi";
        term[27] = "meni";
        term[28] = "igual";
        term[29] = "dif";
        term[30] = "eof";

		nt[0] = "PROGP";
        nt[1] = "PROG";
        nt[2] = "DATSEC";
        nt[3] = "VARS";
        nt[4] = "LISTA";
        nt[5] = "PRIN";
        nt[6] = "TIPO";
        nt[7] = "BLQ";
        nt[8] = "INST";
        nt[9] = "COND";
        nt[10] = "CICLO";
        nt[11] = "ASIG";
        nt[12] = "EXP";
        nt[13] = "E";
        nt[14] = "F";
        nt[15] = "S";
        nt[16] = "OP";

		pi[0] = "PROGP";
        pi[1] = "PROG";
        pi[2] = "DATSEC";
        pi[3] = "DATSEC";
        pi[4] = "VARS";
        pi[5] = "VARS";
        pi[6] = "LISTA";
        pi[7] = "LISTA";
        pi[8] = "PRIN";
        pi[9] = "TIPO";
        pi[10] = "TIPO";
        pi[11] = "BLQ";
        pi[12] = "BLQ";
        pi[13] = "INST";
        pi[14] = "INST";
        pi[15] = "INST";
        pi[16] = "COND";
        pi[17] = "COND";
        pi[18] = "CICLO";
        pi[19] = "ASIG";
        pi[20] = "EXP";
        pi[21] = "E";
        pi[22] = "E";
        pi[23] = "E";
        pi[24] = "F";
        pi[25] = "F";
        pi[26] = "F";
        pi[27] = "S";
        pi[28] = "S";
        pi[29] = "S";
        pi[30] = "S";
        pi[31] = "OP";
        pi[32] = "OP";
        pi[33] = "OP";
        pi[34] = "OP";
        pi[35] = "OP";
        pi[36] = "OP";

		lpd[0] = 1;
        lpd[1] = 2;
        lpd[2] = 3;
        lpd[3] = 0;
        lpd[4] = 4;
        lpd[5] = 3;
        lpd[6] = 3;
        lpd[7] = 1;
        lpd[8] = 3;
        lpd[9] = 1;
        lpd[10] = 1;
        lpd[11] = 2;
        lpd[12] = 1;
        lpd[13] = 1;
        lpd[14] = 1;
        lpd[15] = 1;
        lpd[16] = 9;
        lpd[17] = 7;
        lpd[18] = 6;
        lpd[19] = 3;
        lpd[20] = 3;
        lpd[21] = 3;
        lpd[22] = 3;
        lpd[23] = 1;
        lpd[24] = 3;
        lpd[25] = 3;
        lpd[26] = 1;
        lpd[27] = 1;
        lpd[28] = 1;
        lpd[29] = 1;
        lpd[30] = 3;
        lpd[31] = 1;
        lpd[32] = 1;
        lpd[33] = 1;
        lpd[34] = 1;
        lpd[35] = 1;
        lpd[36] = 1;
		
		for(int i = 0; i < M.length; i++){
			for(int j = 0; j < M[i].length; j++){
				M[i][j] = 0;
			}
		}
		
		M[0][0]=3;
		M[2][5]=5;
		M[3][7]=8;
		M[3][8]=9;
		M[5][9]=15;
		M[5][3]=16;
		M[5][15]=17;
		M[6][1]=18;
		M[7][2]=19;
		M[10][6]=20;
		M[11][9]=15;
		M[11][3]=16;
		M[11][15]=17;
		M[15][10]=22;
		M[16][17]=23;
		M[17][10]=24;
		M[19][3]=26;
		M[22][22]=31;
		M[22][23]=32;
		M[22][3]=33;
		M[22][10]=34;
		M[23][22]=31;
		M[23][23]=32;
		M[23][3]=33;
		M[23][10]=34;
		M[24][22]=31;
		M[24][23]=32;
		M[24][3]=33;
		M[24][10]=34;
		M[25][7]=8;
		M[25][8]=9;
		M[26][4]=38;
		M[27][11]=39;
		M[28][24]=41;
		M[28][25]=42;
		M[28][26]=43;
		M[28][27]=44;
		M[28][28]=45;
		M[28][29]=46;
		M[28][18]=47;
		M[28][19]=48;
		M[29][20]=49;
		M[29][21]=50;
		M[34][22]=31;
		M[34][23]=32;
		M[34][3]=33;
		M[34][10]=34;
		M[35][18]=47;
		M[35][19]=48;
		M[36][11]=52;
		M[38][3]=26;
		M[39][12]=54;
		M[40][22]=31;
		M[40][23]=32;
		M[40][3]=33;
		M[40][10]=34;
		M[47][22]=31;
		M[47][23]=32;
		M[47][3]=33;
		M[47][10]=34;
		M[48][22]=31;
		M[48][23]=32;
		M[48][3]=33;
		M[48][10]=34;
		M[49][22]=31;
		M[49][23]=32;
		M[49][3]=33;
		M[49][10]=34;
		M[50][22]=31;
		M[50][23]=32;
		M[50][3]=33;
		M[50][10]=34;
		M[51][11]=60;
		M[51][18]=47;
		M[51][19]=48;
		M[52][9]=15;
		M[52][3]=16;
		M[52][15]=17;
		M[54][9]=15;
		M[54][3]=16;
		M[54][15]=17;
		M[55][18]=47;
		M[55][19]=48;
		M[56][20]=49;
		M[56][21]=50;
		M[57][20]=49;
		M[57][21]=50;
		M[61][16]=63;
		M[62][13]=64;
		M[62][14]=65;
		M[64][9]=15;
		M[64][3]=16;
		M[64][15]=17;
		M[66][14]=67;
		M[0][32]=1;
		M[0][33]=2;
		M[2][36]=4;
		M[3][34]=6;
		M[3][37]=7;
		M[5][38]=10;
		M[5][39]=11;
		M[5][40]=12;
		M[5][42]=13;
		M[5][41]=14;
		M[11][38]=21;
		M[11][39]=11;
		M[11][40]=12;
		M[11][42]=13;
		M[11][41]=14;
		M[19][35]=25;
		M[22][43]=27;
		M[22][44]=28;
		M[22][45]=29;
		M[22][46]=30;
		M[23][44]=35;
		M[23][45]=29;
		M[23][46]=30;
		M[24][43]=36;
		M[24][44]=28;
		M[24][45]=29;
		M[24][46]=30;
		M[25][34]=37;
		M[25][37]=7;
		M[28][47]=40;
		M[34][44]=51;
		M[34][45]=29;
		M[34][46]=30;
		M[38][35]=53;
		M[40][44]=55;
		M[40][45]=29;
		M[40][46]=30;
		M[47][45]=56;
		M[47][46]=30;
		M[48][45]=57;
		M[48][46]=30;
		M[49][46]=58;
		M[50][46]=59;
		M[52][38]=61;
		M[52][39]=11;
		M[52][40]=12;
		M[52][42]=13;
		M[52][41]=14;
		M[54][38]=62;
		M[54][39]=11;
		M[54][40]=12;
		M[54][42]=13;
		M[54][41]=14;
		M[64][38]=66;
		M[64][39]=11;
		M[64][40]=12;
		M[64][42]=13;
		M[64][41]=14;
		M[0][5]=-3;
		M[1][30]=3000;
		M[4][30]=-1;
		M[8][2]=-9;
		M[9][2]=-10;
		M[11][6]=-12;
		M[11][13]=-12;
		M[11][14]=-12;
		M[11][16]=-12;
		M[12][9]=-13;
		M[12][3]=-13;
		M[12][15]=-13;
		M[12][6]=-13;
		M[12][13]=-13;
		M[12][14]=-13;
		M[12][16]=-13;
		M[13][9]=-14;
		M[13][3]=-14;
		M[13][15]=-14;
		M[13][6]=-14;
		M[13][13]=-14;
		M[13][14]=-14;
		M[13][16]=-14;
		M[14][9]=-15;
		M[14][3]=-15;
		M[14][15]=-15;
		M[14][6]=-15;
		M[14][13]=-15;
		M[14][14]=-15;
		M[14][16]=-15;
		M[18][5]=-2;
		M[20][30]=-8;
		M[21][6]=-11;
		M[21][13]=-11;
		M[21][14]=-11;
		M[21][16]=-11;
		M[26][7]=-7;
		M[26][8]=-7;
		M[26][1]=-7;
		M[25][1]=-5;
		M[29][24]=-23;
		M[29][25]=-23;
		M[29][26]=-23;
		M[29][27]=-23;
		M[29][28]=-23;
		M[29][29]=-23;
		M[29][18]=-23;
		M[29][19]=-23;
		M[29][11]=-23;
		M[29][9]=-23;
		M[29][3]=-23;
		M[29][15]=-23;
		M[29][6]=-23;
		M[29][13]=-23;
		M[29][14]=-23;
		M[29][16]=-23;
		M[30][20]=-26;
		M[30][21]=-26;
		M[30][24]=-26;
		M[30][25]=-26;
		M[30][26]=-26;
		M[30][27]=-26;
		M[30][28]=-26;
		M[30][29]=-26;
		M[30][18]=-26;
		M[30][19]=-26;
		M[30][11]=-26;
		M[30][9]=-26;
		M[30][3]=-26;
		M[30][15]=-26;
		M[30][6]=-26;
		M[30][13]=-26;
		M[30][14]=-26;
		M[30][16]=-26;
		M[31][20]=-27;
		M[31][21]=-27;
		M[31][24]=-27;
		M[31][25]=-27;
		M[31][26]=-27;
		M[31][27]=-27;
		M[31][28]=-27;
		M[31][29]=-27;
		M[31][18]=-27;
		M[31][19]=-27;
		M[31][11]=-27;
		M[31][9]=-27;
		M[31][3]=-27;
		M[31][15]=-27;
		M[31][6]=-27;
		M[31][13]=-27;
		M[31][14]=-27;
		M[31][16]=-27;
		M[32][20]=-28;
		M[32][21]=-28;
		M[32][24]=-28;
		M[32][25]=-28;
		M[32][26]=-28;
		M[32][27]=-28;
		M[32][28]=-28;
		M[32][29]=-28;
		M[32][18]=-28;
		M[32][19]=-28;
		M[32][11]=-28;
		M[32][9]=-28;
		M[32][3]=-28;
		M[32][15]=-28;
		M[32][6]=-28;
		M[32][13]=-28;
		M[32][14]=-28;
		M[32][16]=-28;
		M[33][20]=-29;
		M[33][21]=-29;
		M[33][24]=-29;
		M[33][25]=-29;
		M[33][26]=-29;
		M[33][27]=-29;
		M[33][28]=-29;
		M[33][29]=-29;
		M[33][18]=-29;
		M[33][19]=-29;
		M[33][11]=-29;
		M[33][9]=-29;
		M[33][3]=-29;
		M[33][15]=-29;
		M[33][6]=-29;
		M[33][13]=-29;
		M[33][14]=-29;
		M[33][16]=-29;
		M[35][9]=-19;
		M[35][3]=-19;
		M[35][15]=-19;
		M[35][6]=-19;
		M[35][13]=-19;
		M[35][14]=-19;
		M[35][16]=-19;
		M[37][1]=-4;
		M[41][22]=-31;
		M[41][23]=-31;
		M[41][3]=-31;
		M[41][10]=-31;
		M[42][22]=-32;
		M[42][23]=-32;
		M[42][3]=-32;
		M[42][10]=-32;
		M[43][22]=-33;
		M[43][23]=-33;
		M[43][3]=-33;
		M[43][10]=-33;
		M[44][22]=-34;
		M[44][23]=-34;
		M[44][3]=-34;
		M[44][10]=-34;
		M[45][22]=-35;
		M[45][23]=-35;
		M[45][3]=-35;
		M[45][10]=-35;
		M[46][22]=-36;
		M[46][23]=-36;
		M[46][3]=-36;
		M[46][10]=-36;
		M[53][7]=-6;
		M[53][8]=-6;
		M[53][1]=-6;
		M[55][11]=-20;
		M[56][24]=-21;
		M[56][25]=-21;
		M[56][26]=-21;
		M[56][27]=-21;
		M[56][28]=-21;
		M[56][29]=-21;
		M[56][18]=-21;
		M[56][19]=-21;
		M[56][11]=-21;
		M[56][9]=-21;
		M[56][3]=-21;
		M[56][15]=-21;
		M[56][6]=-21;
		M[56][13]=-21;
		M[56][14]=-21;
		M[56][16]=-21;
		M[57][24]=-22;
		M[57][25]=-22;
		M[57][26]=-22;
		M[57][27]=-22;
		M[57][28]=-22;
		M[57][29]=-22;
		M[57][18]=-22;
		M[57][19]=-22;
		M[57][11]=-22;
		M[57][9]=-22;
		M[57][3]=-22;
		M[57][15]=-22;
		M[57][6]=-22;
		M[57][13]=-22;
		M[57][14]=-22;
		M[57][16]=-22;
		M[58][20]=-24;
		M[58][21]=-24;
		M[58][24]=-24;
		M[58][25]=-24;
		M[58][26]=-24;
		M[58][27]=-24;
		M[58][28]=-24;
		M[58][29]=-24;
		M[58][18]=-24;
		M[58][19]=-24;
		M[58][11]=-24;
		M[58][9]=-24;
		M[58][3]=-24;
		M[58][15]=-24;
		M[58][6]=-24;
		M[58][13]=-24;
		M[58][14]=-24;
		M[58][16]=-24;
		M[59][20]=-25;
		M[59][21]=-25;
		M[59][24]=-25;
		M[59][25]=-25;
		M[59][26]=-25;
		M[59][27]=-25;
		M[59][28]=-25;
		M[59][29]=-25;
		M[59][18]=-25;
		M[59][19]=-25;
		M[59][11]=-25;
		M[59][9]=-25;
		M[59][3]=-25;
		M[59][15]=-25;
		M[59][6]=-25;
		M[59][13]=-25;
		M[59][14]=-25;
		M[59][16]=-25;
		M[60][20]=-30;
		M[60][21]=-30;
		M[60][24]=-30;
		M[60][25]=-30;
		M[60][26]=-30;
		M[60][27]=-30;
		M[60][28]=-30;
		M[60][29]=-30;
		M[60][18]=-30;
		M[60][19]=-30;
		M[60][11]=-30;
		M[60][9]=-30;
		M[60][3]=-30;
		M[60][15]=-30;
		M[60][6]=-30;
		M[60][13]=-30;
		M[60][14]=-30;
		M[60][16]=-30;
		M[63][9]=-18;
		M[63][3]=-18;
		M[63][15]=-18;
		M[63][6]=-18;
		M[63][13]=-18;
		M[63][14]=-18;
		M[63][16]=-18;
		M[65][9]=-17;
		M[65][3]=-17;
		M[65][15]=-17;
		M[65][6]=-17;
		M[65][13]=-17;
		M[65][14]=-17;
		M[65][16]=-17;
		M[67][9]=-16;
		M[67][3]=-16;
		M[67][15]=-16;
		M[67][6]=-16;
		M[67][13]=-16;
		M[67][14]=-16;
		M[67][16]=-16;
		
		int z = 0;
		int w = 0;
		
		in = args[0] + ".sal";
		output = args[0] + ".asm";

		if(!xFile(in).exists()){
			System.out.println("\n\nError: El archivo no existe: [" + in + "]");
			System.exit(4);
		}

		xFile(output).delete();
		
		push("0");
		lee_tok(xFile(in));

		do{
			cls();
			S = stack[max];
			z = M[Integer.parseInt(S)][getTerm(a)];

			if (z == 3000) {
				System.out.println("Tabla De Variables:\n");
				printTab(vars, types);
				System.out.println("\nPRESIONE ENTER PARA CONTINUAR...");
				
				pause();
				cls();
				
				System.out.println("Código Generado:\n");
				System.out.println(Stack_PROG_c[maxStack_PROG_c]);
				cwFile(xFile(output), Stack_PROG_c[maxStack_PROG_c]);
				
				System.out.println("\t\tAnalisis Sintactico Correcto.\n\nPRESIONE ENTER PARA CONTINUAR...");
				pause();

				System.exit(0);
			}else{
				if(z > 0){
					COD_SHIFT(z);
					push(a);
					push(z + "");
					lee_tok(xFile(in));
				}else{
					if (z < 0) {
						COD_REDUCE(z * - 1);
						for(int i = 0; i < lpd[(z * - 1)] * 2; i++){
							pop();
						}
						E = Integer.parseInt(stack[max]);
						w = M[E][getNotTerm(pi[z * - 1])];
						
						push(pi[z * - 1]);
	
						if(w == 0){
							error();
						}else{
							push(w + "");
						}
					}else{
						error();
					}
				}
			}
		}while(true);
	}	
}