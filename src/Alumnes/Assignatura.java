package src.Alumnes;

public class Assignatura {
    public static final int APROVAT = 5;
    public static final int NOTABLE = 7;
    public static final int EXCELLENT = 9;

    private final String nom;
    private final int credits;
    private double nota;
    private final boolean mHonor;

    public Assignatura(String nom, int credits, double nota, boolean mHonor) {
        if (credits < 0 || nota < 0) throw new IllegalArgumentException("Invalid credits or grade.");
        this.nom = nom;
        this.credits = credits;
        this.nota = nota;
        //Chat
        this.mHonor = (nota >= EXCELLENT) && mHonor;
    }

    public Assignatura(String nom) {
        this(nom, 0, 0.0, false);
    }

    public void setNota(double nota) {
        if (nota < 0) throw new IllegalArgumentException("Grade cannot be negative.");
        this.nota = nota;
    }

    public double getNota() {
        return nota;
    }

    public int getPunts() {
        if (nota >= EXCELLENT) return mHonor ? 4 : 3;
        if (nota >= NOTABLE) return 2;
        return nota >= APROVAT ? 1 : 0;
    }

    public int getCredits() {
        return credits;
    }

    @Override
    public String toString() {
        return "Assignatura{" + "nom='" + nom + '\'' + ", nota=" + nota + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Assignatura)) return false;
        //Que fa aixo
        Assignatura other = (Assignatura) obj;
        return nom.equals(other.nom);
    }
}
