import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;

public class aroSLR {

    static String[] stack = new String[1000];
    static int maxStack = -1;
    static String a;
    static String S;
    static String[] term = new String[13];
    static String[] nt = new String[5];
    static String[] pi = new String[13];
    static int[] lpd = new int[13];
    static int[][] M = new int [25][18];
    static String row, LEX;
    static int position = 0;
    static String in;

    static double[] M_v = new double[200];
    static int tMv = -1;
    static double[] PC_x = new double[200];
    static int tPCx = -1;
    static double[] PC_y = new double[200];
    static int tPCy = -1;
    static double[] PC_d = new double[200];
    static int tPCd = -1;
    static double[] A_x = new double[200];
    static int tAx = -1;
    static double[] A_y = new double[200];
    static int tAy = -1;
    static double[] A_d = new double[200];
    static int tAd = -1;
    static double[] T_x = new double[200];
    static int tTx = -1;
    static double[] T_y = new double[200];
    static int tTy = -1;
    static double[] T_d = new double[200];
    static int tTd = -1;
    static double[] TP_x = new double[200];
    static int tTPx = -1;
    static double[] TP_y = new double[200];
    static int tTPy = -1;
    static double[] TP_d = new double[200];
    static int tTPd = -1;

    static double tempX, tempY, tempD;

    static double temp;


    public static void printPila(){
        System.out.print("\nP -> ");
        for(int i=0; i<=maxStack; i++)
            System.out.print(stack[i]+" ");
    }

    public static void push(String X){
        if(maxStack==999){
            System.out.println("Pila llena.");
            System.exit(4);
        }
        if(!X.equals("epsilon"))
            stack[++maxStack]=X;
    }

    public static String pop(){
        if(maxStack==-1){
            System.out.println("Pila vacia.");
            System.exit(4);
        }
        return(stack[maxStack--]);
    }

    public static int es_terminal(String X){
        //System.out.println(X);
        for(int i=0;i<=12;i++){
            if(X.equals(term[i]))
                return(i);
        }
        return(-1);
    }

    public static int no_terminal(String X){
        for(int i=0;i<=7;i++){
            if(X.equals(nt[i]))
                return(i+13);
        }
        return(-1);
    }

    public static File xFile(String xName){
        return new File(xName);
    }

    public static String pause(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String nada = null;
        try{
            nada = br.readLine();
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
        System.out.println("\nERROR en el token [" + a + "] cerca del renglon " + row + ".\n");
        System.exit(4);
    }

    public static String lee_tok(File xFile){
        try{
            FileReader fr = new FileReader(xFile);
            BufferedReader br = new BufferedReader(fr);
            long NoSirve = br.skip(position);
            String linea = br.readLine();
            position = position + linea.length() + 2;
            String XX = linea;
            linea = br.readLine();
            position = position + linea.length() + 2;
            LEX = linea;
            linea = br.readLine();
            position = position + linea.length() + 2;
            row = linea;
            fr.close();
            //System.out.print(".");
            return(XX);
        }catch(IOException e) {
            System.out.println("ERROR.");
            return("errorsote");
        }
    }

    public static void gen_code_shift(int Shift){
        if (Shift == 23) {//System.out.println("En este momento el lexema leído es: " + LEX);
            temp = Double.parseDouble(LEX);
            //pause();
        }
    }

    public static void gen_code_reduce(int Reduce){
        switch(Reduce){
            case -1:
                tempX = PC_x[tPCx--] + A_x[tAx--];
                T_x[++tTx] = tempX;
                tempX = PC_y[tPCy--] + A_y[tAy--];
                T_y[++tTy] = tempX;
                tempX = PC_d[tPCd--] + A_d[tAd--];
                T_d[++tTd] = tempX;
                break;
            case -2:
                A_x[++tAx] = T_x[tTx--];
                A_y[++tAy] = T_y[tTy--];
                A_d[++tAd] = T_d[tTd--];
                break;
            case -3:
                A_x[++tAx] = 0;
                A_y[++tAy] = 0;
                A_d[++tAd] = 0;
                break;
            case -4:
                PC_x[++tPCx] = 0;
                PC_y[++tPCy] = M_v[tMv];
                PC_d[++tPCd] = M_v[tMv--];
                break;
            case -5:
                PC_x[++tPCx] = 0;
                PC_y[++tPCy] = -1 * M_v[tMv];
                PC_d[++tPCd] = M_v[tMv--];
                break;
            case -6:
                PC_x[++tPCx] = M_v[tMv];
                PC_y[++tPCy] = 0;
                PC_d[++tPCd] = M_v[tMv--];
                break;
            case -7:
                PC_x[++tPCx] = -1 * M_v[tMv];
                PC_y[++tPCy] = 0;
                PC_d[++tPCd] = M_v[tMv--];
                break;
            case -8:
                PC_x[++tPCx] = M_v[tMv];
                PC_y[++tPCy] = M_v[tMv];
                PC_d[++tPCd] = M_v[tMv--] * Math.sqrt(2);
                break;
            case -9:
                PC_x[++tPCx] = M_v[tMv];
                PC_y[++tPCy] = -1 * M_v[tMv];
                PC_d[++tPCd] = M_v[tMv--] * Math.sqrt(2);
                break;
            case -10:
                PC_x[++tPCx] = -1 * M_v[tMv];
                PC_y[++tPCy] = M_v[tMv];
                PC_d[++tPCd] = M_v[tMv--] * Math.sqrt(2);
                break;
            case -11:
                PC_x[++tPCx] = -1 * M_v[tMv];
                PC_y[++tPCy] = -1 * M_v[tMv];
                PC_d[++tPCd] = M_v[tMv--] * Math.sqrt(2);
                break;
            case -12:
                M_v[++tMv] = temp;
                break;
        }
    }
    
    public static String centerText(String text, int width) {
        int padding = width - text.length();
        int leftPadding = padding / 2;
        int rightPadding = padding - leftPadding;
        return " ".repeat(leftPadding) + text + " ".repeat(rightPadding);
    }
    
    public static void printTab(String coordsX, String coordsY, String distance) {
        Formatter formatt = new Formatter(System.out);
    
        int maxX = Math.max("Coordenadas X".length(), coordsX.length());
        int maxY = Math.max("Coordenadas Y".length(), coordsY.length());
        int maxD = Math.max("Coordenadas Y".length(), distance.length());
    
        String separator = " +-" + "-".repeat(maxX) + "-+-" + "-".repeat(maxY) + "-+-" + "-".repeat(maxD) + "-+";
    
        formatt.format("%s%n", separator);
        formatt.format(" | %-" + maxX + "s | %-" + maxY + "s | %-" + maxD + "s |%n", centerText("Coordenadas X", maxX), centerText("Coordenadas Y", maxY), centerText("Distancia", maxD));
        formatt.format("%s%n", separator);
    
        formatt.format(" | %-" + maxX + "s | %-" + maxY + "s | %-" + maxD + "s |%n", centerText(coordsX, maxX), centerText(coordsY, maxY), centerText(distance, maxD));
    
        formatt.format("%s%n", separator);
        formatt.flush();
    }
    

    public static void main(String[] args){
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
        nt[2] = "A";
        nt[3] = "PC";
        nt[4] = "M";

        pi[0] = "TP";
        pi[1] = "T";
        pi[2] = "A";
        pi[3] = "A";
        pi[4] = "PC";
        pi[5] = "PC";
        pi[6] = "PC";
        pi[7] = "PC";
        pi[8] = "PC";
        pi[9] = "PC";
        pi[10] = "PC";
        pi[11] = "PC";
        pi[12] = "M";

        lpd[0] = 1;
        lpd[1] = 2;
        lpd[2] = 2;
        lpd[3] = 0;
        lpd[4] = 2;
        lpd[5] = 2;
        lpd[6] = 2;
        lpd[7] = 2;
        lpd[8] = 2;
        lpd[9] = 2;
        lpd[10] = 2;
        lpd[11] = 2;
        lpd[12] = 3;

        for (int[] ints : M) {
            Arrays.fill(ints, 0);
        }

        // M[Fila][Columna]
        M[0][0] = 3;
        M[0][1] = 4;
        M[0][2] = 5;
        M[0][3] = 6;
        M[0][4] = 7;
        M[0][5] = 8;
        M[0][6] = 9;
        M[0][7] = 10;
        M[1][12] = 666;
        M[2][11] = 12;
        M[2][12] = -3;
        M[3][8] = 14;
        M[4][8] = 14;
        M[5][8] = 14;
        M[6][8] = 14;
        M[7][8] = 14;
        M[8][8] = 14;
        M[9][8] = 14;
        M[10][8] = 14;
        M[11][12] = -1;
        M[12][0] = 3;
        M[12][1] = 4;
        M[12][2] = 5;
        M[12][3] = 6;
        M[12][4] = 7;
        M[12][5] = 8;
        M[12][6] = 9;
        M[12][7] = 10;
        M[13][11] = -4;
        M[13][12] = -4;
        M[14][9] = 23;
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
        M[21][11] = -11;
        M[21][12] = -11;
        M[22][12] = -2;
        M[23][10] = 24;
        M[24][11] = -12;
        M[24][12] = -12;

        M[0][14] = 1;
        M[0][16] = 2;
        M[2][15] = 11;
        M[3][17] = 13;
        M[4][17] = 15;
        M[5][17] = 16;
        M[6][17] = 17;
        M[7][17] = 18;
        M[8][17] = 19;
        M[9][17] = 20;
        M[10][17] = 21;
        M[12][14] = 22;
        M[12][16] = 2;


        in = args[0]+".al";
        if(!xFile(in).exists()){
            System.out.println("\n\nERROR: El archivo " + in + " no existe.\n");
            System.exit(4);
        }

        push("0");
        a = lee_tok(xFile(in));
        do{
            S = stack[maxStack];
            int xxx=Integer.parseInt(S);

            if(M[Integer.parseInt(S)][es_terminal(a)]==666){
                cls();
                System.out.println("Analisis Sintactico terminado correctamente.\n");
                System.out.print("\n\nPresiona ENTER para continuar...");
                pause();
                cls();
                //System.out.println("Coordenadas  finales: (" + T_x[tTx] + ", " + T_y[tTy] + ")");
                //System.out.println("Distancia recorrida: " + T_d[tTd] + "\n");
                System.out.println("\t\t\tPOCISION FINAL:\n");
                printTab(T_x[tTx] + "", T_y[tTy] + "", T_d[tTd] + "");
                System.out.print("\n\nPresiona ENTER para salir...");
                pause();
                System.exit(0);
            } else {
                if(M[Integer.parseInt(S)][es_terminal(a)]>0){
                    push(a);
                    push(M[Integer.parseInt(S)][es_terminal(a)]+"");
                    gen_code_shift(M[Integer.parseInt(S)][es_terminal(a)]);
                    a=lee_tok(xFile(in));
                } else {
                    if(M[Integer.parseInt(S)][es_terminal(a)]<0){
                        for(int k=1; k<=lpd[M[Integer.parseInt(S)][es_terminal(a)]*-1]*2; k++){
                            pop();
                        }
                        gen_code_reduce(M[Integer.parseInt(S)][es_terminal(a)]);
                        int e = Integer.parseInt(stack[maxStack]);//----
                        push(pi[M[Integer.parseInt(S)][es_terminal(a)]*-1]);
                        
                        if(M[e][no_terminal(stack[maxStack])]==0){//----
                            error();
                        } else {
                            push(M[e][no_terminal(stack[maxStack])]+"");
                        }
                    } else {
                        error();
                    }
                }
            }
        }while(true);
    }
}