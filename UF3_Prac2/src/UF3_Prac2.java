
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Utils;

public class UF3_Prac2 {

    static Scanner scan = new Scanner(System.in);

    private static final int CODI = 6;
    private static final int NOM = 20;
    private static final int COGNOMS = 30;
    private static final int DATA_NAIXEMENT = 8;
    private static final int ADRECA_POSTAL = 40;
    private static final int EMAIL = 30;
    
    public static final String NOM_FITXER = "clients.txt";
    public static final String NOM_TEMPORAL = "temporal.txt";

    public static void main(String[] args) {
        File clients = new File("clients.txt");
        if(!clients.exists()){
            try {
                clients.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(UF3_Prac2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        int opcio = mostrarMenu();
        gestionarOpcions(opcio);
        System.out.println("Fi del programa");
    }

    private static int mostrarMenu() {
        System.out.println("Menu");
        System.out.println("0. Sortir");
        System.out.println("1. Alta d'un client");
        System.out.println("2. Consulta d'un client per posició");
        System.out.println("3. Consulta d'un client per codi");
        System.out.println("4. Modificar un client");
        System.out.println("5. Esborrar un client");
        System.out.println("6. Llistar tots els clients");
        
        System.out.print("Tria una opció: ");
        return scan.nextInt();
    }
    
    private static void gestionarOpcions(int opcio){
        while (opcio !=0) {
            switch (opcio) {
                //Els scan.nextLine són per menjar-nos el salt de línia
                case 1:
                    scan.nextLine();
                    altaClient(NOM_FITXER);
                    break;
                case 2:
                    consultaClientPosicio();
                    break;
                case 3:
                    scan.nextLine();
                    consultaClientCodi();
                    break;
                case 4:
                    scan.nextLine();
                    modificarClient();
                    break;
                case 5:
                    scan.nextLine();
                    esborrarClient();
                    break;
                case 6:
                    llistarClients();
                    break;
                default:
                    System.out.println("Opció no vàlida");
                    break;
            }
            opcio = mostrarMenu();
        }
    }
    
    private static void altaClient(String nomFitx) {
        String codi = demanarCodi();
        codi = String.format("%-6s", codi);
        if (existeixClient(codi)) {
            System.out.println("El client ja existeix");
        }
        else{
            String nom = demanarNom();
            String cognoms = demanarCognoms();
            String dataNaixement = demanarDataNaixement();
            String adrecaPostal = demanarAdreçaPostal();
            String email = demanarEmail();
            escriureClient(codi, nom, cognoms, dataNaixement, adrecaPostal, email, nomFitx);
            System.out.println("Client registrat");
        }
    }

    private static String demanarCodi() {
        System.out.print("Introdueix el codi: ");
        String codi = scan.nextLine();
        codi = comprovarLlargada(codi, CODI);
        return codi;
    }

    private static boolean existeixClient(String codi) {
        boolean existeix = false;
        BufferedReader br = Utils.AbrirFicheroLectura(NOM_FITXER, existeix);
        String linea = Utils.LeerLinea(br);
        while (linea != null && !existeix) {
            if (linea.substring(1, CODI+1).equals(codi)) {
                existeix = true;
            }
            else{
                linea = Utils.LeerLinea(br);
            }
        }
        return existeix;
    }
    
    private static String comprovarLlargada(String entrada, int valorMaxim){
        boolean valid = false;
        while(!valid){
            if(entrada.length() <= valorMaxim){
                valid = true;
            }
            else{
                System.out.print("ERROR, la longitud és massa gran, torna a introduir les dades: ");
                entrada = scan.nextLine();
            }
        }
        return entrada;
    }

    private static String demanarNom() {
        System.out.print("Introdueix el nom: ");
        String nom = scan.nextLine();
        nom = comprovarLlargada(nom, NOM);
        return nom;
    }

    private static String demanarCognoms() {
        System.out.print("Introdueix els cognoms: ");
        String cognoms = scan.nextLine();
        cognoms = comprovarLlargada(cognoms, COGNOMS);
        return cognoms;
    }

    private static String demanarDataNaixement() {
        System.out.print("Introdueix la data de naixement (DDMMYYYY): ");
        String dataNaix = scan.nextLine();
        dataNaix = comprovarLlargada(dataNaix, DATA_NAIXEMENT);
        return dataNaix;
    }

    private static String demanarAdreçaPostal() {
        System.out.print("Introdueix l'adreça postal: ");
        String adrecaPostal = scan.nextLine();
        adrecaPostal = comprovarLlargada(adrecaPostal, ADRECA_POSTAL);
        return adrecaPostal;
    }

    private static String demanarEmail() {
        System.out.print("Introdueix l'email: ");
        String email = scan.nextLine();
        email = comprovarLlargada(email, EMAIL);
        return email;
    }

    private static void escriureClient(String codi, String nom, String cognoms, String dataNaixement, String adrecaPostal, String email, String nomFitx) {
        PrintWriter pw = Utils.AbrirFicheroEscritura(nomFitx, true, true);
        //Posició 0 segons el fitxer és buida, ja que el codi comença a la posició 1
        String result = " ";
        result += String.format("%-6s", codi);
        result += String.format("%-20s", nom);
        result += String.format("%-30s", cognoms);
        result += String.format("%-8s", dataNaixement);
        result += String.format("%-40s", adrecaPostal);
        result += String.format("%-30s", email);
        pw.println(result);
        Utils.CerrarFichero(pw);
    }

    private static void consultaClientPosicio() {
        System.out.print("Introdueix la posició del client: ");
        int posicio = Utils.LlegirInt();
        BufferedReader br = Utils.AbrirFicheroLectura(NOM_FITXER, true);
        int comptador = 0;
        String linea = Utils.LeerLinea(br);
        boolean trobat = false;
        while (linea != null && !trobat) {
            if (comptador == posicio) {
                mostrarDades(linea);
                trobat = true;
            }
            comptador++;
            linea = Utils.LeerLinea(br);
        }
    }

    private static void consultaClientCodi() {
        System.out.print("Introdueix el codi del client: ");
        String codi = scan.nextLine();
        codi = String.format("%-6s", codi);
        BufferedReader br = Utils.AbrirFicheroLectura(NOM_FITXER, true);
        String linea = Utils.LeerLinea(br);
        boolean trobat = false;
        while (linea != null && !trobat) {
            if (linea.substring(1, CODI+1).equals(codi)) {
                mostrarDades(linea);
                trobat = true;
            }
            else{
                linea = Utils.LeerLinea(br);
            }
        }
    }
    
    private static void mostrarDades(String linea){
        System.out.println("Codi: " + linea.substring(1, CODI+1));
        System.out.println("Nom: " + linea.substring(CODI+1, CODI + NOM+1));
        System.out.println("Cognoms: " + linea.substring(CODI + NOM + 1, CODI + NOM + COGNOMS + 1));
        System.out.println("Data de naixement: " + linea.substring(CODI + NOM + COGNOMS + 1, CODI + NOM + COGNOMS + DATA_NAIXEMENT + 1));
        System.out.println("Adreça postal: " + linea.substring(CODI + NOM + COGNOMS + DATA_NAIXEMENT + 1, CODI + NOM + COGNOMS + DATA_NAIXEMENT + ADRECA_POSTAL + 1));
        System.out.println("Email: " + linea.substring(CODI + NOM + COGNOMS + DATA_NAIXEMENT + ADRECA_POSTAL + 1, linea.length()));
    }
    
    private static void modificarClient() {
        System.out.print("Introdueix el codi del client que vols modificar: ");
        String codi = scan.nextLine();
        codi = String.format("%-6s", codi);
        PrintWriter pw = Utils.AbrirFicheroEscritura(NOM_TEMPORAL, true, true);
        BufferedReader br = Utils.AbrirFicheroLectura(NOM_FITXER, true);
        String linea = Utils.LeerLinea(br);
        while (linea != null) {
            if (linea.substring(1, CODI+1).equals(codi)) {
                mostrarDades(linea);
                altaClient(NOM_TEMPORAL);
            }
            else{
                pw.println(linea);
            }
            linea = Utils.LeerLinea(br);
        }
        
        Utils.CerrarFichero(br);
        Utils.CerrarFichero(pw);
        Utils.BorrarFichero(NOM_FITXER);
        Utils.RenombrarFichero(NOM_TEMPORAL, NOM_FITXER);
        Utils.BorrarFichero(NOM_TEMPORAL);
    }
    
    private static void esborrarClient() {
        
        System.out.print("Introdueix el codi del client que vols esborrar: ");
        String codi = scan.nextLine();
        codi = String.format("%-6s", codi);
        BufferedReader br = Utils.AbrirFicheroLectura(NOM_FITXER, true);
        PrintWriter pw = Utils.AbrirFicheroEscritura(NOM_TEMPORAL, true, true);
        String linea = Utils.LeerLinea(br);
        while (linea != null) {
            if (!linea.substring(1, CODI+1).equals(codi)) {
                pw.println(linea);
            }
            linea = Utils.LeerLinea(br);
        }
        Utils.CerrarFichero(pw);
        Utils.CerrarFichero(br);
        Utils.BorrarFichero(NOM_FITXER);
        Utils.RenombrarFichero(NOM_TEMPORAL, NOM_FITXER);
        Utils.BorrarFichero(NOM_TEMPORAL);
    }
    
    private static void llistarClients(){
        BufferedReader br = Utils.AbrirFicheroLectura(NOM_FITXER, true);
        String linea = Utils.LeerLinea(br);
        int contador = 1;
        while (linea != null) {
            System.out.println("Client " + contador);
            mostrarDades(linea);
            ++contador;
            linea = Utils.LeerLinea(br);
        }
    }

}
