import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class aroAL{

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
        reservWords.add("n");
        reservWords.add("s");
        reservWords.add("e");
        reservWords.add("o");
        reservWords.add("ne");
        reservWords.add("se");
        reservWords.add("no");
        reservWords.add("so");
    }
    /*  End the reserved words  */
    
    static boolean end_file = false; // end line file

    public static boolean is_reserved(String X){
        // Verify if my token is a reserved word
        if (reservWords.contains(X)) {
            return true;
        } else {
            return false;
        }
    }

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
		System.exit(0);
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
                START = 3;
                break;
            case 3:
                START = 6;
                break;
            case 6:
                START = 10;
                break;
            case 10:
                START = 13;
                break;
            case 13:
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
                    if(is_num(c)){
                        STATE = 1;
                    }else{
                        STATE = DIAGRAM();
                    }
                    break;
                case 1:
                    c = read_char();
                    if(is_num(c)){
                        STATE = 1;
                    }else{
                        STATE = 2;
                    }
                    break;
                case 2:
                    a_a--;
                    My_Lex = getLex();
                    a_i = a_a;
                    return "num";
                case 3:
                    c = read_char();
                    if(is_letter(c)){
                        STATE = 4;
                    }else{
                        STATE = DIAGRAM();
                    }
                    break;
                case 4:
                    c = read_char();
                    if(is_letter(c)){
                        STATE = 4;
                    }else{
                        STATE = 5;
                    }
                    break;
                case 5:
                    a_a--;
                    My_Lex = getLex();
                    a_i = a_a;
                    if(is_reserved(My_Lex)){
                        return (My_Lex);
                    }else{
                        error();
                    }
                case 6:
                    c = read_char();
                    if(c == '('){
                        STATE = 7;
                    }else if(c == ')'){
                        STATE = 8;
                    }else if(c == ','){
                        STATE = 9;
                    }else{
                        STATE = DIAGRAM();
                    }
                    break;
                case 7:
                    My_Lex = getLex();
                    a_i = a_a;
                    return ("(");
                case 8:
                    My_Lex = getLex();
                    a_i = a_a;
                    return (")");
                case 9:
                    My_Lex = getLex();
                    a_i = a_a;
                    return (",");
                case 10:
                    c = read_char();
                    if(is_delim(c)){
                        STATE = 11;
                    }else{
                        STATE = DIAGRAM();
                    }
                    break;
                case 11:
                    c = read_char();
                    if(is_delim(c)){
                        STATE = 11;
                    }else{
                        STATE = 12;
                    }
                    break;
                case 12:
                    a_a--;
                    My_Lex = getLex();
                    a_i = a_a;
                    return ("NoSirve");
                case 13:
                    c = read_char();
                    if(c == 255){
                        STATE = 14;
                    }else{
                        STATE = DIAGRAM();
                    }
                    break;
                case 14:
					My_Lex = getLex();
					a_i = a_a;
					return ("NoSirve");
            }
        }while(true);
    }

    public static void main(String[] arguments) {
        clearScreen();
        
        if(arguments.length == 0){
            System.out.println("\7ERROR: Falta proporcionar el archivo de entrada");
			System.exit(4);
		}
        
        inArgs = arguments[0] + ".fte";
        outArgs = arguments[0] + ".al";
        
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
                cwFile(xFile(outArgs), My_Token);
                cwFile(xFile(outArgs), My_Lex);
                cwFile(xFile(outArgs), row + "");
            }
        } while (!end_file);
		cwFile(xFile(outArgs), "eof");
        cwFile(xFile(outArgs), "eof");
        cwFile(xFile(outArgs), "666");
        System.out.println("Analizador Lexicográfico terminado\n\n\tPresione cualquier tecla para terminar...");
		pause();
    }
}