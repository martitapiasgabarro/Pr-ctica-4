package src.Alumnes;

public class Alumnes_SEC implements Comparable<Alumnes_SEC> {
    private class Node {
        Assignatura info;
        Node next;

        Node(Assignatura info) {
            this.info = info;
            this.next = null;
        }

        Node(String nom) {
            this(new Assignatura(nom));  // Si nom es el nombre de la asignatura, crea una asignatura vacía
        }
    }

    private Node cap;
    private String nom;  // Atributo para almacenar el nombre del alumno

    public Alumnes_SEC(String nom) {
        this.nom = nom;  // Guardamos el nombre del alumno
        this.cap = new Node(nom);  // Asignamos el nombre en el nodo de cabecera
    }

    public String getNom() {
        return this.nom;
    }

    public void addAssignatura(Assignatura nova) {
        Node current = cap;
        double total = 0;
        int totalCredits = 0;

        // Buscamos si ya existe la asignatura
        while (current.next != null) {
            if (current.next.info.equals(nova)) {
                current.next.info = nova;
                break;
            }
            current = current.next;
        }

        // Si no existe, agregamos la nueva asignatura
        if (current.next == null) current.next = new Node(nova);

        // Recalculamos la nota media
        current = cap.next;
        while (current != null) {
            total += current.info.getPunts() * current.info.getCredits();
            totalCredits += current.info.getCredits();
            current = current.next;
        }

        // Actualizamos la nota media del alumno
        cap.info.setNota(total / totalCredits);
    }

    public boolean hiHa(int punts) {
        Node current = cap.next;
        while (current != null) {
            if (current.info.getPunts() == punts) return true;
            current = current.next;
        }
        return false;
    }

    // Método para comparar a los alumnos por nota
    @Override
    public int compareTo(Alumnes_SEC other) {
        int result = Double.compare(other.cap.info.getNota(), this.cap.info.getNota());  // Primero comparamos por nota
        if (result == 0) {
            // Si las notas son iguales, comparamos por el nombre
            result = this.nom.compareTo(other.nom);
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Nom: " + this.nom + "\nNota mitjana: " + cap.info.getNota() + "\n");
        Node current = cap.next;
        while (current != null) {
            sb.append("  ").append(current.info).append("\n");
            current = current.next;
        }
        return sb.toString();
    }
}
