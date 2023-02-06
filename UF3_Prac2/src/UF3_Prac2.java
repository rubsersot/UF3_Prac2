
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class UF3_Prac2 {

    private static final int CODI = 6;
    private static final int NOM = 20;
    private static final int COGNOMS = 30;
    private static final int DATA_NAIXEMENT = 8;
    private static final int ADRECA_POSTAL = 40;
    private static final int EMAIL = 30;
    private static final int TOTAL = CODI + NOM + COGNOMS + DATA_NAIXEMENT + ADRECA_POSTAL + EMAIL;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int opcio = mostrarMenu(scan);
        while (opcio != 4) {
            switch (opcio) {
                case 1:
                    altaClient(scan);
                case 2:
                    consultaClientPosicio(scan);
                case 3:
                    consultaClientCodi(scan);
                default:
                    System.out.println("Opció no vàlida");
            }
            opcio = mostrarMenu(scan);
        }
        System.out.println("Fi del programa");
    }

    private static int mostrarMenu(Scanner scan) {
        System.out.println("Menu");
        System.out.println("1. Alta d'un client");
        System.out.println("2. Consulta d'un client per posició");
        System.out.println("3. Consulta d'un client per codi");
        System.out.println("4. Sortir");
        System.out.print("Tria una opció: ");
        return scan.nextInt();
    }

    private static void altaClient(Scanner scan) {
        String codi = demanarCodi(scan);
        if (existeixClient(codi)) {
            System.out.println("El client ja existeix");
            return;
        }
        String nom = demanarNom(scan);
        String cognoms = demanarCognoms(scan);
        String dataNaixement = demanarDataNaixement(scan);
        String adreçaPostal = demanarAdreçaPostal(scan);
        String email = demanarEmail(scan);
        escriureClient(codi, nom, cognoms, dataNaixement, adreçaPostal, email);
        System.out.println("Client registrat");
    }

    private static String demanarCodi(Scanner scan) {
        System.out.print("Introdueix el codi: ");
        return scan.nextLine();
    }

    private static boolean existeixClient(String codi) {
        try ( BufferedReader br = new BufferedReader(new FileReader("clients.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.substring(0, CODI).equals(codi)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String demanarNom(Scanner scan) {
        System.out.print("Introdueix el nom: ");
        return scan.nextLine();
    }

    private static String demanarCognoms(Scanner scan) {
        System.out.print("Introdueix els cognoms: ");
        return scan.nextLine();
    }

    private static String demanarDataNaixement(Scanner scan) {
        System.out.print("Introdueix la data de naixement (DDMMYYYY): ");
        return scan.nextLine();
    }

    private static String demanarAdreçaPostal(Scanner scan) {
        System.out.print("Introdueix l'adreça postal: ");
        return scan.nextLine();
    }

    private static String demanarEmail(Scanner scan) {
        System.out.print("Introdueix el email: ");
        return scan.nextLine();
    }

    private static void escriureClient(String codi, String nom, String cognoms, String dataNaixement, String adreçaPostal, String email) {
        try ( FileWriter fw = new FileWriter("clients.txt", true)) {
            fw.write(codi + nom + cognoms + dataNaixement + adreçaPostal + email + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void consultaClientPosicio(Scanner scan) {
        System.out.print("Introdueix la posició del client: ");
        int posicio = scan.nextInt();
        try ( BufferedReader br = new BufferedReader(new FileReader("clients.txt"))) {
            int comptador = 1;
            String linea;
            while ((linea = br.readLine()) != null) {
                if (comptador == posicio) {
                    System.out.println("Codi: " + linea.substring(0, CODI));
                    System.out.println("Nom: " + linea.substring(CODI, CODI + NOM));
                    System.out.println("Cognoms: " + linea.substring(CODI + NOM, CODI + NOM + COGNOMS));
                    System.out.println("Data de naixement: " + linea.substring(CODI + NOM + COGNOMS, CODI + NOM + COGNOMS + DATA_NAIXEMENT));
                    System.out.println("Adreça postal: " + linea.substring(CODI + NOM + COGNOMS + DATA_NAIXEMENT, CODI + NOM + COGNOMS + DATA_NAIXEMENT + ADRECA_POSTAL));
                    System.out.println("Email: " + linea.substring(CODI + NOM + COGNOMS + DATA_NAIXEMENT + ADRECA_POSTAL, linea.length()));
                }
                comptador++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void consultaClientCodi(Scanner scan) {
        System.out.print("Introdueix el codi del client: ");
        String codi = scan.nextLine();
        try ( BufferedReader br = new BufferedReader(new FileReader("clients.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.substring(0, CODI).equals(codi)) {
                    System.out.println("Codi: " + linea.substring(0, CODI));
                    System.out.println("Nom: " + linea.substring(CODI, CODI + NOM));
                    System.out.println("Cognoms: " + linea.substring(CODI + NOM, CODI + NOM + COGNOMS));
                    System.out.println("Data de naixement: " + linea.substring(CODI + NOM + COGNOMS, CODI + NOM + COGNOMS + DATA_NAIXEMENT));
                    System.out.println("Adreça postal: " + linea.substring(CODI + NOM + COGNOMS + DATA_NAIXEMENT, CODI + NOM + COGNOMS + DATA_NAIXEMENT + ADRECA_POSTAL));
                    System.out.println("Email: " + linea.substring(CODI + NOM + COGNOMS + DATA_NAIXEMENT + ADRECA_POSTAL));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
