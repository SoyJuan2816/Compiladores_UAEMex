import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class AL_Ensamblador{

    // For file
    static int filesize = 0;
    static char line[]; // number line on the file
    
    // For algorithm
    static int a_a = 0; // apuntador de avance
    static int a_i = 0; // apuntador de inicio
    static int row = 0; // row of the lex on the file
    static int c;       // c is an array for each character

    static int START; // start the diagrams
    static int STATE; // State of the diagram

    static String My_Lex = ""; // Save lex prhase
    static String My_Token = ""; // token

    static String inArgs, outArgs; // enter the name of file without the extention .fte and save the file with extention .al

    static Set<String> reservWords = new HashSet<String>(); // All the reserved words


    /*  Start the reserved words  */
    static{
        reservWords.add("MOV");
        reservWords.add("ADD");
        reservWords.add("SUB");
        reservWords.add("MUL");
        reservWords.add("DIV");
        reservWords.add("CMP");
        reservWords.add("JMP");
        reservWords.add("JE");
        reservWords.add("JZ");
        reservWords.add("JNE");
        reservWords.add("JNZ");
        reservWords.add("JG");
        reservWords.add("JNLE");
        reservWords.add("JGE");
        reservWords.add("JNL");
        reservWords.add("JL");
        reservWords.add("JNGE");
        reservWords.add("JLE");
        reservWords.add("JNG");
        reservWords.add("CALL");
        reservWords.add("RET");
        reservWords.add("PUSH");
        reservWords.add("POP");
        reservWords.add("INC");
        reservWords.add("DEC");
        reservWords.add("AND");
        reservWords.add("OR");
        reservWords.add("XOR");
        reservWords.add("NOT");
        reservWords.add("CMP");
        reservWords.add("NOP");
        reservWords.add("HLT");
        reservWords.add("DB");
        reservWords.add("DW");
        reservWords.add("DD");
        reservWords.add("ORG");
        reservWords.add("END");
    }
    /*  End the reserved words  */
    
    static boolean end_file = false; // end line file

    public static boolean cwFile(File xFile, String message){ // close-write File
        try{
            PrintWriter fileOut = new PrintWriter(
                new FileWriter(xFile, true));
            fileOut.println(message);
            fileOut.close();
            return true;
        }catch(IOException e){
            return false;
        }
    }

    public static File xFile(String xName){ // Save de file in a var
        File xF = new File(xName);
        return xF;
    }

    public static String pause(){ // pause the program
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String nothing = null;
        try{
            nothing = in.readLine();
            return nothing;
        }catch(Exception e){
            System.err.println(e);
        }
        return "";
    }

    public static char[] openReadClose(String xName){ // Open, Read and close file
        File xF = new File(xName);
        char[] data;
        try{
            FileReader end = new FileReader(xF);
            filesize = (int)xF.length();
            data = new char[filesize + 1];
            end.read(data, 0, filesize);
            data[filesize] = ' ';
            filesize++;
            return data;
        }catch (FileNotFoundException e){}
        catch (IOException e){}
        return null;
    }

    public static int read_char(){
        if(a_a <= filesize - 1){
            if(line[a_a] == 10){
                row++;
            }
            return line[a_a++];
        }else{
            end_file = true;
            return 255;
        }
    }

    public static void error(){
        char errorChar = line[a_a];
        System.out.println("Error en el caracter '" + errorChar + "' cerca del renglón " + row);
		System.out.println("\n\n\tPresione cualquier tecla para terminar...");
		pause();
		System.exit(4);
    }

    public static boolean is_delim(int x){
        if((x == 9) || (x == 10) || (x == 13) || (x == 32)){
			return true;
		}
		return false;
    }

    public static boolean is_letter(int x){
        return (Character.toUpperCase(x) >= 'A' && Character.toUpperCase(x) <= 'Z');
	}
    
    public static boolean is_num(int x){
        return (x >= '0' && x <= '9');
	}

    public static String getLex(){
        String x = "";
        for(int i = a_i; i < a_a; i++){
            x += line[i];
        }
        return x;
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

    public static int DIAGRAM(){
        a_a = a_i;
        
        switch(START){
            case 0:
                START = 11;
                break;
            case 11:
                START = 15;
                break;
            case 15:
                START = 19;
                break;
            case 19:
                START = 22;
                break;
            case 22:
                START = 24;
                break;
            case 24:
                error();
                break;
        }
        return START;
    }

    public static String TOKEN(){
        do{
            switch(STATE){
                case 0:
                    c = read_char();
                    if(Character.toUpperCase(c) == 'A' || Character.toUpperCase(c) == 'B'){
                        STATE = 1;
                    }else if(Character.toUpperCase(c) == 'C'){
                        STATE = 4;
                    }else if(Character.toUpperCase(c) == 'D'){
                        STATE = 5;
                    }else if(Character.toUpperCase(c) == 'E'){
                        STATE = 6;
                    }else if(Character.toUpperCase(c) == 'S'){
                        STATE = 9;
                    }else if(Character.toUpperCase(c) == 'I'){
                        STATE = 10;
                    }else{
                        STATE = DIAGRAM();
                    }
                    break;
                case 1:
                    c = read_char();
                    if(Character.toUpperCase(c) == 'X'){
                        STATE = 2;
                    }else{
                        STATE = DIAGRAM();
                    }
                    break;
                case 2:
                    c = read_char();
                    if(!is_letter(Character.toUpperCase(c)) && !is_num(c)){
                        STATE = 3;
                    }else{
                        STATE = DIAGRAM();
                    }
                    break;
                case 3:
                    a_a--;
                    My_Lex = getLex();
                    a_i = a_a;
                    return ("Reg");
                case 4:
                    c = read_char();
                    if(Character.toUpperCase(c) == 'X' || Character.toUpperCase(c) == 'S'){
                        STATE = 2;
                    }else{
                        STATE = DIAGRAM();
                    }
                    break;
                case 5:
                    c = read_char();
                    if(Character.toUpperCase(c) == 'X' || Character.toUpperCase(c) == 'S' || Character.toUpperCase(c) == 'I'){
                        STATE = 2;
                    }else{
                        STATE = DIAGRAM();
                    }
                    break;
                case 6:
                    c = read_char();
                    if(Character.toUpperCase(c) == 'S'){
                        STATE = 7;
                    }else{
                        STATE = DIAGRAM();
                    }
                    break;
                case 7:
                    c = read_char();
                    if(!is_letter(Character.toUpperCase(c)) && !is_num(c)){
                        STATE = 8;
                    }else{
                        STATE = DIAGRAM();
                    }
                    break;
                case 8:
                    a_a--;
                    My_Lex = getLex();
                    a_i = a_a;
                    return ("Reg");
                case 9:
                    c = read_char();
                    if(Character.toUpperCase(c) == 'S' || Character.toUpperCase(c) == 'I'){
                        STATE = 7;
                    }else{
                        STATE = DIAGRAM();
                    }
                    break;
                case 10:
                    c = read_char();
                    if(Character.toUpperCase(c) == 'P'){
                        STATE = 7;
                    }else{
                        STATE = DIAGRAM();
                    }
                    break;
                case 11:
                    c = read_char();
                    if((Character.toUpperCase(c) >= 'A' && Character.toUpperCase(c) <= 'F') || (is_num(c))){
                        STATE = 12;
                    }else{
                        STATE = DIAGRAM();
                    }
                    break;
                case 12:
                    c = read_char();
                    if((Character.toUpperCase(c) >= 'A' && Character.toUpperCase(c) <= 'F') || (is_num(c))){
                        STATE = 12;
                    }else if(Character.toUpperCase(c) == 'H'){
                        STATE = 13;
                    }else{
                        STATE = DIAGRAM();
                    }
                    break;
                
                case 13:
                    c = read_char();
                    if(!is_letter(Character.toUpperCase(c)) && !is_num(c)){
                        STATE = 14;
                    }else{
                        STATE = DIAGRAM();
                    }
                    break;
                case 14:
                    a_a--;
                    My_Lex = getLex();
                    a_i = a_a;
                    return ("Hex");
                case 15:
                    c = read_char();
                    if(is_letter(Character.toUpperCase(c))){
                        STATE = 16;
                    }else{
                        STATE = DIAGRAM();
                    }
                    break;
                case 16:
                    c = read_char();
                    if(is_letter(Character.toUpperCase(c)) || is_num(c)){
                        STATE = 16;
                    }else if(c == ':'){
                        STATE = 18;
                    }else{
                        STATE = 17;
                    }
                    break;
                case 17:
                    a_a--;
                    My_Lex = getLex();
                    a_i = a_a;
                    return ("Id");
                case 18:
                    My_Lex = getLex();
                    a_i = a_a;
                    return ("Etq");
                case 19:
                    c = read_char();
                    if(is_delim(c)){
                        STATE = 20;
                    }else{
                        STATE = DIAGRAM();
                    }
                    break;
                case 20:
                    c = read_char();
                    if(is_delim(c)){
                        STATE = 20;
                    }else{
                        STATE = 21;
                    }
                    break;
                case 21:
                    a_a--;
                    My_Lex = getLex();
                    a_i = a_a;
                    return ("NoSirve");
                case 22:
                    c = read_char();
                    if(c == ','){
                        STATE = 23;
                    }else{
                        STATE = DIAGRAM();
                    }
                    break;
                case 23:
                    My_Lex = getLex();
                    a_i = a_a;
                    return ("Coma");
                case 24:
                    c = read_char();
                    if(is_num(c) && (c != 'h' && c != 'H' && !is_letter(Character.toUpperCase(c)))){
                        STATE = 25;
                    }else{
                        STATE = DIAGRAM();
                    }
                    break;
                case 25:
                    My_Lex = getLex();
                    a_i = a_a;
                    return ("Num");
                case 26:
					c = read_char();
					if(c == 255){
						STATE = 15;
					}else{
						STATE = DIAGRAM();
					}
					break;
                case 27:
					My_Lex = getLex();
					a_i = a_a;
					return ("Nosirve");
            }
        }while(true);
    }

    public static void main(String[] arguments) {
        clearScreen();
        
        if(arguments.length == 0){
            System.out.println("\7ERROR: Falta proporcionar el archivo de entrada");
			System.exit(4);
		}
        
        inArgs = arguments[0] + ".fte"; // Extension for incoming file
        outArgs = arguments[0] + ".al"; // Extension for outgoing file
        
        if(!xFile(inArgs).exists()){
            System.out.println("\7ERROR: El archivo " + inArgs + " no existe");
			System.exit(4);
		}

        System.out.println("Ejecutando diagramas, por favor espere....");

        line = openReadClose(inArgs);

        xFile(outArgs).delete();

        do {
            START = 0;
            STATE = 0;
            My_Token = TOKEN();
            if (!My_Token.equals("NoSirve")) {
                // Verify if my token is a reserved word
                if (reservWords.contains(My_Lex.toUpperCase())) { // Upper the words in "My_Lex" for compare with the reserved words strings
                    // is a reserved word
                    cwFile(xFile(outArgs), "Res"); // mark as reserved
                    cwFile(xFile(outArgs), My_Lex);
                    cwFile(xFile(outArgs), row + "");
                } else {
                    // isn't a reserved word
                    cwFile(xFile(outArgs), My_Token);
                    cwFile(xFile(outArgs), My_Lex);
                    cwFile(xFile(outArgs), row + "");
                }
            }
        } while (!end_file);
		System.out.println("Analizador Lexicográfico terminado\n\n\tPresione cualquier tecla para terminar...");
		pause();
    }
}