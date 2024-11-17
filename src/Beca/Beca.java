package src.Beca;

import src.Alumnes.Alumnes_SEC;
import src.Alumnes.Assignatura;
import src.EstructuraArbre.AcbEnll;
import src.EstructuraArbre.ArbreException;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Beca {
    private static final Scanner scanner = new Scanner(System.in);
    private AcbEnll<Alumnes_SEC> arbreACB;
    private Queue<Alumnes_SEC> llistaDescendent;

    // Constructor per defecte: inicialitza els exemples i la llista descendent
    public Beca() {
        arbreACB = new AcbEnll<>();
        try {
            arbreACB.inserir(exempleRosa());
            arbreACB.inserir(exempleEnric());
            arbreACB.inserir(exempleRandom("Maria"));
            arbreACB.inserir(exempleRandom("Josep"));
            arbreACB.inserir(exempleRandom("Anna"));
            System.out.println("Llista ascendent: " + arbreACB.getAscendentList());
            System.out.println("Llista descendent: " + arbreACB.getDescendentList());

        } catch (ArbreException e) {
            System.out.println("Error inicialitzant alumnes: " + e.getMessage());
        }
        llistaDescendent = arbreACB.getDescendentList();
    }

    // Exemple predefinit per a Rosa
    private Alumnes_SEC exempleRosa() {
        Alumnes_SEC rosa = new Alumnes_SEC("Rosa");
        rosa.addAssignatura(new Assignatura("Fonaments de la Programació", 6, 7.0, false));
        rosa.addAssignatura(new Assignatura("Programació Orientada a l'Objecte", 6, 5.0, false));
        rosa.addAssignatura(new Assignatura("Estructura de Dades i Algorismes", 4, 9.0, false));
        rosa.addAssignatura(new Assignatura("Programació Avançada", 4, 5.0, false));
        return rosa;
    }

    // Exemple predefinit per a Enric
    private Alumnes_SEC exempleEnric() {
        Alumnes_SEC enric = new Alumnes_SEC("Enric");
        enric.addAssignatura(new Assignatura("Fonaments de la Programació", 6, 8, false));
        enric.addAssignatura(new Assignatura("Programació Orientada a l'objecte", 6, 6, false));
        enric.addAssignatura(new Assignatura("Estructura de Dades i Algorismes", 4, 9, true));
        enric.addAssignatura(new Assignatura("Programació Avançada", 4, 3, false));
        return enric;
    }

    // Genera un alumne amb dades aleatòries
    private Alumnes_SEC exempleRandom(String nom) {
        Alumnes_SEC randomStudent = new Alumnes_SEC(nom);
        randomStudent.addAssignatura(new Assignatura("Fonaments de la Programació", 6, Math.random() * 10, Math.random() > 0.5));
        randomStudent.addAssignatura(new Assignatura("Programació Orientada a l'objecte", 6, Math.random() * 10, Math.random() > 0.5));
        randomStudent.addAssignatura(new Assignatura("Estructura de Dades i Algorismes", 4, Math.random() * 10, Math.random() > 0.5));
        randomStudent.addAssignatura(new Assignatura("Programació Avançada", 4, Math.random() * 10, Math.random() > 0.5));
        return randomStudent;
    }

    // Mètode per mostrar la llista d'alumnes en ordre descendent
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Llista d'alumnes en ordre descendent:\n");

        // Crea una còpia temporal de la cua per no modificar l'original
        Queue<Alumnes_SEC> copia = new LinkedList<>(llistaDescendent);
        while (!copia.isEmpty()) {
            Alumnes_SEC alumne = copia.poll(); // Extreu l'alumne de la còpia
            sb.append(alumne).append("\n");
        }

        return sb.toString(); // Retorna el resultat
    }


// Method to delete students without honors
    public void esborraAlumnesSenseMatricula() {
        Queue<Alumnes_SEC> alumnes = arbreACB.getAscendentList();
        try {
            while (!alumnes.isEmpty()) {
                Alumnes_SEC alumne = alumnes.poll();
                if (!alumne.hiHa(4)) { // Cap assignatura amb matrícula d'honor
                    arbreACB.esborrar(alumne);
                }
            }
            llistaDescendent = arbreACB.getDescendentList();
        } catch (ArbreException e) {
            System.err.println("Error esborrant alumnes: " + e.getMessage());
        }
    }

    // Method to add a new student
    public void afegirAlumne() {
        System.out.print("Nom de l'alumne: ");
        String nom = scanner.nextLine().trim();
        Alumnes_SEC alumne = new Alumnes_SEC(nom);
        System.out.println("Afegeix assignatures (nom buit per finalitzar):");
        while (true) {
            try {
                System.out.print("Nom de l'assignatura: ");
                String nomAssignatura = scanner.nextLine();
                if (nomAssignatura.isEmpty()) break;
                System.out.print("Crèdits: ");
                int credits = scanner.nextInt();
                System.out.print("Nota: ");
                double nota = scanner.nextDouble();
                boolean mhonor = nota >= Assignatura.EXCELLENT;
                alumne.addAssignatura(new Assignatura(nomAssignatura, credits, nota, mhonor));
                scanner.nextLine(); // Netejar buffer

                System.out.print("Afegir una altra assignatura? (yes/no): ");
                if (!scanner.nextLine().trim().equalsIgnoreCase("yes")) break;

            } catch (NumberFormatException e) {
                System.out.println("Entrada no vàlida. Torna-ho a intentar.");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        try {
            arbreACB.inserir(alumne);
            llistaDescendent = arbreACB.getDescendentList();
        } catch (ArbreException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }



    // Main
    public static void main(String[] args) {
        Beca beca = new Beca();
        boolean sortir = false;
        while (!sortir) {
            System.out.println("""
                1. Afegir un nou alumne
                2. Esborrar un alumne a partir del seu nom
                3. Mostrar tots els alumnes en ordre descendent
                4. Esborrar alumnes sense matrícula d’honor
                5. Sortir del programa
            """);
            System.out.print("Selecciona una opció: ");
            int opcio = scanner.nextInt();
            scanner.nextLine(); // Netejar buffer
            switch (opcio) {
                case 1 -> beca.afegirAlumne();
                case 2 -> {
                    System.out.print("Nom de l'alumne a esborrar: ");
                    String nom = scanner.nextLine();
                    try {
                        beca.arbreACB.esborrar(new Alumnes_SEC(nom));
                    } catch (ArbreException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
                case 3 -> System.out.println(beca);
                case 4 -> beca.esborraAlumnesSenseMatricula();
                case 5 -> sortir = true;
                default -> System.out.println("Opció no vàlida. Torna-ho a intentar.");
            }
        }
    }
}
