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
        reservWords.add("datos");
        reservWords.add("fin_datos");
        reservWords.add("entero");
        reservWords.add("decimal");
        reservWords.add("cierto");
        reservWords.add("haz");
        reservWords.add("falso");
        reservWords.add("fin_cond");
        reservWords.add("mientras");
        reservWords.add("fin_mientras");
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

    public static File xFile(String xName){ // Save the file in a var
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

    public static boolean is_letter(int x){ //Preguntar si se puede hacer con mayusculas y minusculas
        return (Character.toUpperCase(x) >= 'A' && Character.toUpperCase(x) <= 'Z');
	}

    public static boolean is_any(int x){
		return x != 10 && x != 13 && x != 255;
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
                START = 7;
                break;
            case 7:
                START = 12;
                break;
            case 12:
                START = 15;
                break;
            case 15:
                START = 19;
                break;
            case 19:
                START = 30;
                break;
            case 30:
                START = 47;
                break;
            case 47:
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
                    if(is_delim(c)){
                        STATE = 1;
                    }else{
                        STATE = DIAGRAM();
                    }
                    break;
                case 1:
                    c = read_char();
                    if(is_delim(c)){
                        STATE = 1;
                    }else{
                        STATE = 2;
                    }
                    break;
                case 2:
                    a_a--;
                    My_Lex = getLex();
                    a_i = a_a;
                    return ("nosirve");
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
                    if(is_letter(c) || is_num(c)){
                        STATE = 4;
                    }else if(c == '_'){
                        STATE = 5;
                    }else{
                        STATE = 6;
                    }
                    break;
                case 5:
                    c = read_char();
                    if(is_letter(c) || is_num(c)){
                        STATE = 4;
                    }else{
                        STATE = DIAGRAM();
                    }
                    break;
                case 6:
                    a_a--;
                    My_Lex = getLex();
                    a_i = a_a;
                    return ("id");
                case 7:
                    c = read_char();
                    if(is_num(c)){
                        STATE = 8;
                    }else{
                        STATE = DIAGRAM();
                    }
                    break;
                case 8:
                    c = read_char();
                    if(is_num(c)){
                        STATE = 8;
                    }else if(c == '.'){
                        STATE = 9;
                    }else{
                        STATE = DIAGRAM();
                    }
                    break;
                case 9:
                    c = read_char();
                    if(is_num(c)){
                        STATE = 10;
                    }else{
                        STATE = DIAGRAM();
                    }
                    break;
                case 10:
                    c = read_char();
                    if(is_num(c)){
                        STATE = 10;
                    }else{
                        STATE = 11;
                    }
                    break;
                case 11:
                    a_a--;
                    My_Lex = getLex();
                    a_i = a_a;
                    return ("dec");
                case 12:
                    c = read_char();
                    if(is_num(c)){
                        STATE = 13;
                    }else{
                        STATE = DIAGRAM();
                    }
                    break;
                case 13:
                    c = read_char();
                    if(is_num(c)){
                        STATE = 13;
                    }else{
                        STATE = 14;
                    }
                    break;
                case 14:
                    a_a--;
                    My_Lex = getLex();
                    a_i = a_a;
                    return ("ent");
                case 15:
                    c = read_char();
                    if(c == '/'){
                        STATE = 16;
                    }else{
                        STATE = DIAGRAM();
                    }
                    break;
                case 16:
                    c = read_char();
                    if(c == '/'){
                        STATE = 17;
                    }else{
                        STATE = DIAGRAM();
                    }
                    break;
                case 17:
                    c = read_char();
                    if(is_any(c)){
                        STATE = 17;
                    }else{
                        STATE = 18;
                    }
                    break;
                case 18:
                    a_a--;
                    My_Lex = getLex();
                    a_i = a_a;
                    return ("nosirve");
                case 19:
                    c = read_char();
                    if(c == '+'){
                        STATE = 20;
                    }else if(c == '-'){
                        STATE = 21;
                    }else if(c == '*'){
                        STATE = 22;
                    }else if(c == '/'){
                        STATE = 23;
                    }else if(c == '('){
                        STATE = 24;
                    }else if(c == ')'){
                        STATE = 25;
                    }else if(c == '{'){
                        STATE = 26;
                    }else if(c == '}'){
                        STATE = 27;
                    }else if(c == ':'){
                        STATE = 28;
                    }else if(c == ','){
                        STATE = 29;
                    }else{
                        STATE = DIAGRAM();
                    }
                    break;
                case 20:
                    My_Lex = getLex();
                    a_i = a_a;
                    return ("+");
                case 21:
                    My_Lex = getLex();
                    a_i = a_a;
                    return ("-");
                case 22:
                    My_Lex = getLex();
                    a_i = a_a;
                    return ("*");
                case 23:
                    My_Lex = getLex();
                    a_i = a_a;
                    return ("/");
                case 24:
                    My_Lex = getLex();
                    a_i = a_a;
                    return ("(");
                case 25:
                    My_Lex = getLex();
                    a_i = a_a;
                    return (")");
                case 26:
					My_Lex = getLex();
                    a_i = a_a;
                    return ("{");
                case 27:
					My_Lex = getLex();
					a_i = a_a;
					return ("}");
                case 28:
					My_Lex = getLex();
					a_i = a_a;
					return (":");
                case 29:
					My_Lex = getLex();
					a_i = a_a;
					return (",");
                case 30:
                    c = read_char();
                    if(c == '='){
                        STATE = 31;
                    }else if(c == '<'){
                        STATE = 37;
                    }else if(c == '>'){
                        STATE = 42;
                    }else if(c == '!'){
                        STATE = 45;
                    }else{
                        STATE = DIAGRAM();
                    }
                    break;
                case 31:
                    c = read_char();
                    if(c == '/'){
                        STATE = 32;
                    }else if(c == '>'){
                        STATE = 34;
                    }else if(c == '<'){
                        STATE = 35;
                    }else{
                        STATE = 36;
                    }
                    break;
                case 32:
                    c = read_char();
                    if(c == '='){
                        STATE = 33;
                    }else{
                        STATE = DIAGRAM();
                    }
                    break;
                case 33:
                    My_Lex = getLex();
                    a_i = a_a;
                    return ("dif");
                case 34:
                    My_Lex = getLex();
                    a_i = a_a;
                    return ("mayi");
                case 35:
                    My_Lex = getLex();
                    a_i = a_a;
                    return ("meni");
                case 36:
                    a_a--;
                    My_Lex = getLex();
                    a_i = a_a;
                    return ("igual");
                case 37:
                    c = read_char();
                    if(c == '>'){
                        STATE = 38;
                    }else if(c == '='){
                        STATE = 39;
                    }else if(c == '-'){
                        STATE = 40;
                    }else{
                        STATE = 41;
                    }
                    break;
                case 38:
                    My_Lex = getLex();
                    a_i = a_a;
                    return ("dif");
                case 39:
                    My_Lex = getLex();
                    a_i = a_a;
                    return ("meni");
                case 40:
                    My_Lex = getLex();
                    a_i = a_a;
                    return ("asig");
                case 41:
                    a_a--;
                    My_Lex = getLex();
                    a_i = a_a;
                    return ("men");
                case 42:
                    c = read_char();
                    if(c == '='){
                        STATE = 43;
                    }else{
                        STATE = 44;
                    }
                    break;
                case 43:
                    My_Lex = getLex();
                    a_i = a_a;
                    return ("mayi");
                case 44:
                    a_a--;
                    My_Lex = getLex();
                    a_i = a_a;
                    return ("may");
                case 45:
                    c = read_char();
                    if(c == '='){
                        STATE = 46;
                    }else{
                        STATE = DIAGRAM();
                    }
                    break;
                case 46:
                    My_Lex = getLex();
                    a_i = a_a;
                    return ("dif");
                case 47:
                    c = read_char();
                    if(c == 255){
                        STATE = 48;
                    }else{
                        STATE = DIAGRAM();
                    }
                    break;
                case 48:
                    My_Lex = getLex();
                    a_i = a_a;
                    return ("nosirve");
            }
        }while(true);
    }

    public static void main(String[] arguments) {
        clearScreen();
        
        if(arguments.length == 0){
            System.out.println("\7ERROR: Falta proporcionar el archivo de entrada");
			System.exit(4);
		}
        
        inArgs = arguments[0] + ".prg"; // Extension for incoming file
        outArgs = arguments[0] + ".sal"; // Extension for outgoing file
        
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
            if (!My_Token.equals("nosirve")) {
                // Verify if my token is a reserved word
                if (reservWords.contains(My_Lex.toLowerCase())) { // Upper the words in "My_Lex" for compare with the reserved words strings
                    // is a reserved word
                    cwFile(xFile(outArgs), My_Lex.toLowerCase()); // mark as reserved
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
        cwFile(xFile(outArgs), "eof");
        cwFile(xFile(outArgs), "eof");
        cwFile(xFile(outArgs), "666");
		System.out.println("Analizador Lexicográfico terminado\n\n\tPresione ENTER para terminar...");
		pause();
    }
}