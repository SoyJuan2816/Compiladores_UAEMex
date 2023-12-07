import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class aro_PRD{
    static String CAB, LEX, RENGLON;
    static int Posicion = 0;

	static String entrada;

	static int jump = 0;

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

	public static void error(){
		System.out.println("Error en\nTOKEN: [" + CAB + "]\nRENGLÓN: "+ RENGLON);
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

	public static void sig_cab(File xFile){
		jump = jumps();
		try{
			FileReader fr = new FileReader(xFile);
			BufferedReader br = new BufferedReader(fr);
			long NoSirve = br.skip(Posicion);
			String linea = br.readLine();
			Posicion += linea.length() + jump;
			CAB = linea;
			linea = br.readLine();
			Posicion += linea.length() + jump;
			LEX = linea;
			linea = br.readLine();
			Posicion += linea.length() + jump;
			RENGLON = linea;
			fr.close();
		}catch(IOException e){
			System.out.println("No Manches Un Errorsote");
		}
	}

	static void aso(String t){
		if(CAB.equals(t)){
			sig_cab(xArchivo(entrada));
		}else{
			error();
		}
	}

	static void IF(){
		if(CAB.equals("si")){
			aso("si");
			aso("pa");
			EXP();
			aso("pc");
			aso("entonces");
			INS();
			SIG();
		}else{
			error();
		}
	}

	static void EXP(){
		if(CAB.equals("num") || CAB.equals("id")){
			E();
			OP();
			E();
		}else{
			error();
		}
	}

	static void E(){
		if(CAB.equals("num")){
			aso("num");
		}else if(CAB.equals("id")){
			aso("id");
		}else{
			error();
		}
	}

	static void OP(){
		if(CAB.equals(">")){
			aso(">");
		}else if(CAB.equals("<")){
			aso("<");
		}else if(CAB.equals("=")){
			aso("=");
		}else{
			error();
		}
	}

	static void INS(){
		if(CAB.equals("read") || CAB.equals("si")){
			I();
			INS();
		}
	}

	static void I(){
		if(CAB.equals("read")){
			aso("read");
		}else if(CAB.equals("si")){
			IF();
		}else{
			error();
		}
	}

	static void SIG(){
		if(CAB.equals("otro")){
			aso("otro");
			INS();
			aso("finsi");
		}else if(CAB.equals("finsi")){
			aso("finsi");
		}else{
			error();
		}
	}

	public static void main(String[] args) {
		entrada = args[0] + ".al";

		if(!xArchivo(entrada).exists()){
			limpiarPantalla();
			System.out.println("\t\t* * * * * * * * * * ERROR * * * * * * * * * *");
			System.out.println("\n\tEl archivo '" + entrada + "' no existe");
			System.out.println("Presiona enter para terminar...");
			pausa();
			System.exit(1);
		}

		limpiarPantalla();

		sig_cab(xArchivo(entrada));
		IF();
		if(!CAB.equals("eof")){
			error();
		}
		System.out.println("Analisis sintactico terminado con exito\n");
		System.out.println("Presiona enter para terminar...");
		pausa();
	}
}