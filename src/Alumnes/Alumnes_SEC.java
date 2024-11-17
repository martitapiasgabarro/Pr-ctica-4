package src.Alumnes;

import java.lang.Comparable;

public class Alumnes_SEC implements Comparable<Alumnes_SEC> {

    // Definició de la classe Node, que emmagatzema una assignatura i un enllaç al següent node
    private class Node {
        Assignatura info; // Assignatura associada al node
        Node next; // Enllaç al següent node

        // Constructor que crea un node amb una assignatura donada
        Node(Assignatura info) {
            this.info = info;
            this.next = null;
        }

        // Constructor que crea un node amb només el nom de l'assignatura (assignatura buida)
        Node(String nom) {
            this(new Assignatura(nom));  // Si el nom és el nom de l'assignatura, crea una assignatura buida
        }
    }

    private Node cap; // Node de capçalera
    private String nom;  // Atribut per emmagatzemar el nom de l'alumne

    // Constructor per crear un alumne amb el seu nom
    public Alumnes_SEC(String nom) {
        this.nom = nom;  // Guardem el nom de l'alumne
        this.cap = new Node(nom);  // Assignem el nom al node de capçalera
    }

    // Mètode per obtenir el nom de l'alumne
    public String getNom() {
        return this.nom;
    }

    // Mètode per afegir una assignatura a l'alumne
    public void addAssignatura(Assignatura nova) {
        Node current = cap;
        double total = 0;
        int totalCredits = 0;

        // Busquem si ja existeix la mateixa assignatura
        while (current.next != null) {
            if (current.next.info.equals(nova)) {
                current.next.info = nova;  // Si existeix, actualitzem la informació
                break;
            }
            current = current.next;
        }

        // Si no existeix, afegim la nova assignatura
        if (current.next == null) current.next = new Node(nova);

        // Recalculem la nota mitjana de l'alumne
        current = cap.next;
        while (current != null) {
            total += current.info.getPunts() * current.info.getCredits(); // Suma de punts ponderats
            totalCredits += current.info.getCredits(); // Suma de crèdits
            current = current.next;
        }

        // Actualitzem la nota mitjana de l'alumne
        cap.info.setNota(total / totalCredits);
    }

    // Mètode per comprovar si l'alumne té una assignatura amb una determinada nota
    public boolean hiHa(int punts) {
        Node current = cap.next;
        while (current != null) {
            if (current.info.getPunts() == punts) return true;  // Si trobem la nota, retornem true
            current = current.next;
        }
        return false;  // Si no trobem la nota, retornem false
    }

    // Mètode per comparar els alumnes segons la seva nota
    @Override
    public int compareTo(Alumnes_SEC other) {
        // Compara les notes de forma ascendent
        int result = Double.compare(this.cap.info.getNota(), other.cap.info.getNota());
        if (result == 0) {
            result = this.nom.compareTo(other.nom);  // Si les notes són iguals, es compara per nom
        }
        return result;
    }

    // Mètode per mostrar la informació de l'alumne
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Nom: " + this.nom + "\nNota mitjana: " + cap.info.getNota() + "\n");
        Node current = cap.next;
        // Afegim les assignatures de l'alumne a la cadena de text
        while (current != null) {
            sb.append("  ").append(current.info).append("\n");
            current = current.next;
        }
        return sb.toString();  // Retorna la representació de l'alumne com una cadena de text
    }
}
