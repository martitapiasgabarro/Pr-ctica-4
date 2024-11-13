package Alumnes;

public class Alumnes_SEC implements Comparable<Alumnes_SEC> {
    private class Node {
        Assignatura info;
        Node next;

        Node(Assignatura info) {
            this.info = info;
            this.next = null;
        }

        Node(String nom) {
            this(new Assignatura(nom));
        }
    }

    private Node cap;

    public Alumnes_SEC(String nom) {
        this.cap = new Node(nom);
    }

    public void addAssignatura(Assignatura nova) {
        Node current = cap;
        double total = 0;
        int totalCredits = 0;

        while (current.next != null) {
            if (current.next.info.equals(nova)) {
                current.next.info = nova;
                break;
            }
            current = current.next;
        }
        if (current.next == null) current.next = new Node(nova);

        current = cap.next;
        while (current != null) {
            total += current.info.getPunts() * current.info.getCredits();
            totalCredits += current.info.getCredits();
            current = current.next;
        }
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

    @Override
    public int compareTo(Alumnes_SEC other) {
        return Double.compare(cap.info.getNota(), other.cap.info.getNota());
    }

    @Override
    public String toString() {
        return cap.info.toString();
    }
}
