import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestionareEchipamente {
    private static List<Echipament> echipamente = new ArrayList<>();

    public static void main(String[] args) {
        citireFisier("electronice.txt");
        Scanner scanner = new Scanner(System.in);
        int optiune;

        do {
            System.out.println("\nMeniu:");
            System.out.println("1. Afișarea tuturor echipamentelor");
            System.out.println("2. Afișarea imprimantelor");
            System.out.println("3. Afișarea copiatoarelor");
            System.out.println("4. Afișarea sistemelor de calcul");
            System.out.println("5. Modificarea stării unui echipament");
            System.out.println("6. Setarea modului de tipărire pentru o imprimantă");
            System.out.println("7. Setarea formatului de copiere pentru copiatoare");
            System.out.println("8. Instalarea unui sistem de operare pe un sistem de calcul");
            System.out.println("9. Afișarea echipamentelor vândute");
            System.out.println("0. Ieșire");
            System.out.print("Alegeți o opțiune: ");
            optiune = scanner.nextInt();
            scanner.nextLine(); // Consum newline

            switch (optiune) {
                case 1 -> afisareToate();
                case 2 -> afisareImprimante();
                case 3 -> afisareCopiatoare();
                case 4 -> afisareSistemeCalcul();
                case 5 -> modificaStare(scanner);
                case 6 -> seteazaModTiparire(scanner);
                case 7 -> seteazaFormatCopiere(scanner);
                case 8 -> instaleazaSistemOperare(scanner);
                case 9 -> afisareVandute();
                case 0 -> System.out.println("La revedere!");
                default -> System.out.println("Opțiune invalidă!");
            }
        } while (optiune != 0);

        scanner.close();
    }

    private static void citireFisier(String fisier) {
        try (BufferedReader br = new BufferedReader(new FileReader(fisier))) {
            String linie;
            while ((linie = br.readLine()) != null) {
                String[] date = linie.split(",");
                String tip = date[0].trim();
                String denumire = date[1].trim();
                int nrInventar = Integer.parseInt(date[2].trim());
                double pret = Double.parseDouble(date[3].trim());
                String zonaMag = date[4].trim();
                Stare stare = Stare.valueOf(date[5].trim().toUpperCase());

                switch (tip.toLowerCase()) {
                    case "imprimanta" -> {
                        int ppm = Integer.parseInt(date[6].trim());
                        int dpi = Integer.parseInt(date[7].trim());
                        int paginiCartus = Integer.parseInt(date[8].trim());
                        ModTiparire modTiparire = ModTiparire.valueOf(date[9].trim().toUpperCase());
                        echipamente.add(new Imprimanta(denumire, nrInventar, pret, zonaMag, stare, ppm, dpi, paginiCartus, modTiparire));
                    }
                    case "copiator" -> {
                        int paginiToner = Integer.parseInt(date[6].trim());
                        FormatCopiere formatCopiere = FormatCopiere.valueOf(date[7].trim().toUpperCase());
                        echipamente.add(new Copiator(denumire, nrInventar, pret, zonaMag, stare, paginiToner, formatCopiere));
                    }
                    case "sistem_calcul" -> {
                        String tipMonitor = date[6].trim();
                        double vitezaProcesor = Double.parseDouble(date[7].trim());
                        int capacitateHDD = Integer.parseInt(date[8].trim());
                        SistemOperare sistemOperare = SistemOperare.valueOf(date[9].trim().toUpperCase());
                        echipamente.add(new SistemCalcul(denumire, nrInventar, pret, zonaMag, stare, tipMonitor, vitezaProcesor, capacitateHDD, sistemOperare));
                    }
                    default -> System.out.println("Tip necunoscut: " + tip);
                }
            }
        } catch (IOException e) {
            System.err.println("Eroare la citirea fișierului: " + e.getMessage());
        }
    }

    private static void afisareToate() {
        echipamente.forEach(System.out::println);
    }

    private static void afisareImprimante() {
        echipamente.stream()
                .filter(e -> e instanceof Imprimanta)
                .forEach(System.out::println);
    }

    private static void afisareCopiatoare() {
        echipamente.stream()
                .filter(e -> e instanceof Copiator)
                .forEach(System.out::println);
    }

    private static void afisareSistemeCalcul() {
        echipamente.stream()
                .filter(e -> e instanceof SistemCalcul)
                .forEach(System.out::println);
    }

    private static void modificaStare(Scanner scanner) {
        System.out.print("Introduceți numărul de inventar: ");
        int nrInventar = scanner.nextInt();
        scanner.nextLine(); // Consum newline
        System.out.print("Introduceți noua stare (ACHIZITIONAT, EXPUS, VANDUT): ");
        String nouaStare = scanner.nextLine().toUpperCase();
        echipamente.stream()
                .filter(e -> e.nrInventar == nrInventar)
                .findFirst()
                .ifPresentOrElse(
                        e -> e.setStare(Stare.valueOf(nouaStare)),
                        () -> System.out.println("Echipament inexistent.")
                );
    }

    private static void seteazaModTiparire(Scanner scanner) {
        System.out.print("Introduceți numărul de inventar al imprimantei: ");
        int nrInventar = scanner.nextInt();
        scanner.nextLine(); // Consum newline
        System.out.print("Introduceți modul de tipărire (COLOR, ALB_NEGRU): ");
        String mod = scanner.nextLine().toUpperCase();
        echipamente.stream()
                .filter(e -> e instanceof Imprimanta && e.nrInventar == nrInventar)
                .map(e -> (Imprimanta) e)
                .findFirst()
                .ifPresentOrElse(
                        imprimanta -> imprimanta.setModTiparire(ModTiparire.valueOf(mod)),
                        () -> System.out.println("Imprimantă inexistentă.")
                );
    }

    private static void seteazaFormatCopiere(Scanner scanner) {
        System.out.print("Introduceți numărul de inventar al copiatorului: ");
        int nrInventar = scanner.nextInt();
        scanner.nextLine(); // Consum newline
        System.out.print("Introduceți formatul de copiere (A3, A4): ");
        String format = scanner.nextLine().toUpperCase();
        echipamente.stream()
                .filter(e -> e instanceof Copiator && e.nrInventar == nrInventar)
                .map(e -> (Copiator) e)
                .findFirst()
                .ifPresentOrElse(
                        copiator -> copiator.setFormatCopiere(FormatCopiere.valueOf(format)),
                        () -> System.out.println("Copiator inexistent.")
                );
    }

    private static void instaleazaSistemOperare(Scanner scanner) {
        System.out.print("Introduceți numărul de inventar al sistemului de calcul: ");
        int nrInventar = scanner.nextInt();
        scanner.nextLine(); // Consum newline
        System.out.print("Introduceți sistemul de operare (WINDOWS, LINUX): ");
        String sistem = scanner.nextLine().toUpperCase();
        echipamente.stream()
                .filter(e -> e instanceof SistemCalcul && e.nrInventar == nrInventar)
                .map(e -> (SistemCalcul) e)
                .findFirst()
                .ifPresentOrElse(
                        sistemCalcul -> sistemCalcul.setSistemOperare(SistemOperare.valueOf(sistem)),
                        () -> System.out.println("Sistem de calcul inexistent.")
                );
    }

    private static void afisareVandute() {
        echipamente.stream()
                .filter(e -> e.getStare() == Stare.VANDUT)
                .forEach(System.out::println);
    }
}