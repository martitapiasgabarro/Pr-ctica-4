import Alumnes.Alumnes_SEC;
import Alumnes.Assignatura;
import EstructuraArbre.AcbEnll;
import EstructuraArbre.ArbreException;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Beca {
    private static final Scanner scanner = new Scanner(System.in);
    private AcbEnll<Alumnes_SEC> arbreACB;
    private Queue<Alumnes_SEC> llistaDescendent;

    // Constructor
    public Beca() {
        arbreACB = new AcbEnll<>();
        try {
            // Initialize with example students
            arbreACB.inserir(exempleRosa());
            arbreACB.inserir(exempleEnric());
            arbreACB.inserir(exempleRandom("Maria"));
            arbreACB.inserir(exempleRandom("Josep"));
            arbreACB.inserir(exempleRandom("Anna"));
        } catch (ArbreException e) {
            System.out.println("Error initializing students: " + e.getMessage());
        }
        // Populate descending order list
        llistaDescendent = arbreACB.getDescendentList();
    }

    // Example students
    private Alumnes_SEC exempleRosa() {
        Alumnes_SEC rosa = new Alumnes_SEC("Rosa");
        rosa.addAssignatura(new Assignatura("Fonaments de la Programació", 6, 7, false));
        rosa.addAssignatura(new Assignatura("Programació Orientada a l'objecte", 6, 5, false));
        rosa.addAssignatura(new Assignatura("Estructura de Dades i Algorismes", 4, 9, false));
        rosa.addAssignatura(new Assignatura("Programació Avançada", 4, 5, false));
        return rosa;
    }

    private Alumnes_SEC exempleEnric() {
        Alumnes_SEC enric = new Alumnes_SEC("Enric");
        enric.addAssignatura(new Assignatura("Fonaments de la Programació", 6, 8, false));
        enric.addAssignatura(new Assignatura("Programació Orientada a l'objecte", 6, 6, false));
        enric.addAssignatura(new Assignatura("Estructura de Dades i Algorismes", 4, 9, true));
        enric.addAssignatura(new Assignatura("Programació Avançada", 4, 3, false));
        return enric;
    }

    private Alumnes_SEC exempleRandom(String nom) {
        Alumnes_SEC randomStudent = new Alumnes_SEC(nom);
        randomStudent.addAssignatura(new Assignatura("Fonaments de la Programació", 6, Math.random() * 10, Math.random() > 0.5));
        randomStudent.addAssignatura(new Assignatura("Programació Orientada a l'objecte", 6, Math.random() * 10, Math.random() > 0.5));
        randomStudent.addAssignatura(new Assignatura("Estructura de Dades i Algorismes", 4, Math.random() * 10, Math.random() > 0.5));
        randomStudent.addAssignatura(new Assignatura("Programació Avançada", 4, Math.random() * 10, Math.random() > 0.5));
        return randomStudent;
    }

    // Method to delete students without honors
    public void esborraAlumnesSenseMatricula() {
        Queue<Alumnes_SEC> llistaTemporal = new LinkedList<>();
        for (Alumnes_SEC alumne : llistaDescendent) {
            if (alumne.hiHa(4)) {
                llistaTemporal.add(alumne);
            } else {
                try {
                    arbreACB.esborrar(alumne);
                } catch (ArbreException e) {
                    System.out.println("Error deleting student: " + e.getMessage());
                }
            }
        }
        llistaDescendent = llistaTemporal;
    }

    // Method to add a new student
    public void afegirAlumne() {
        System.out.print("Nom de l'alumne: ");
        String nom = scanner.nextLine().trim();
        Alumnes_SEC alumne = new Alumnes_SEC(nom);

        while (true) {
            try {
                System.out.print("Assignatura: ");
                String assignaturaNom = scanner.nextLine().trim();
                System.out.print("Crèdits: ");
                int credits = Integer.parseInt(scanner.nextLine().trim());
                System.out.print("Nota: ");
                double nota = Double.parseDouble(scanner.nextLine().trim());
                System.out.print("Matrícula d'Honor (true/false): ");
                boolean mHonor = Boolean.parseBoolean(scanner.nextLine().trim());

                Assignatura assignatura = new Assignatura(assignaturaNom, credits, nota, mHonor);
                alumne.addAssignatura(assignatura);

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


    // toString method to display the list of students in descending order
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Alumnes_SEC alumne : llistaDescendent) {
            result.append(alumne.toString()).append("\n");
        }
        return result.toString();
    }

    // Main method to display menu and perform actions
    public static void main(String[] args) {
        Beca beca = new Beca();
        while (true) {
            System.out.println("1: Afegir un nou alumne");
            System.out.println("2: Esborrar un alumne");
            System.out.println("3: Esborrar tots els alumnes");
            System.out.println("4: Mostrar tots els alumnes en ordre descendent");
            System.out.println("5: Esborrar alumnes sense matrícula d'honor");
            System.out.println("6: Sortir");

            System.out.print("Selecciona una opció: ");
            int opcio = Integer.parseInt(scanner.nextLine());

            switch (opcio) {
                case 1 -> beca.afegirAlumne();
                case 2 -> {
                    System.out.print("Nom de l'alumne a esborrar: ");
                    String nom = scanner.nextLine();
                    try {
                        Alumnes_SEC alumne = new Alumnes_SEC(nom);
                        beca.arbreACB.esborrar(alumne);
                        beca.llistaDescendent = beca.arbreACB.getDescendentList();
                    } catch (ArbreException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
                case 3 -> {
                    beca.arbreACB.buidar();
                    beca.llistaDescendent.clear(); // També buidem la llista descendent
                    System.out.println("Tots els alumnes han estat eliminats.");
                }
                case 4 -> {
                    if (beca.arbreACB.arbreBuit()) {
                        System.out.println("No hi ha cap alumne registrat.");
                    } else {
                        System.out.println(beca);
                    }
                }
                case 5 -> beca.esborraAlumnesSenseMatricula();
                case 6 -> {
                    System.out.println("Sortint del programa.");
                    scanner.close(); // Tanquem el Scanner aquí
                    return; // Sortim del programa
                }
                default -> System.out.println("Opció no vàlida. Torna-ho a intentar.");
            }
        }
    }
}
