package src.EstructuraArbre;

public interface Acb<E extends Comparable<E>> {
    void inserir(E element) throws ArbreException;
    void esborrar(E element) throws ArbreException;
    boolean membre(E element);
    E arrel() throws ArbreException;
    Acb<E> fillEsquerre() throws CloneNotSupportedException;
    Acb<E> fillDret() throws CloneNotSupportedException;
    boolean arbreBuit();
    void buidar();
}
