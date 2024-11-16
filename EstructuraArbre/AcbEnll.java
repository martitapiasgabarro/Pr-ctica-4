package EstructuraArbre;

import java.util.LinkedList;
import java.util.Queue;

public class AcbEnll<E extends Comparable<E>> implements Acb<E> {
    private NodeA arrel;

    private class NodeA implements Cloneable {
        E info;
        NodeA esq;
        NodeA dret;

        NodeA(E info) {
            this.info = info;
            this.esq = null;
            this.dret = null;
        }

        @Override
        protected NodeA clone() {
            try {
                NodeA copia = (NodeA) super.clone();
                if (esq != null) copia.esq = esq.clone();
                if (dret != null) copia.dret = dret.clone();
                return copia;
            } catch (CloneNotSupportedException e) {
                return null; // Confiança que això no passarà
            }
        }
    }

    public AcbEnll() {
        this.arrel = null;
    }

    @Override
    public void inserir(E element) throws ArbreException {
        if (membre(element)) {
            throw new ArbreException("Element already exists.");
        }
        arrel = inserirRecursive(arrel, element);
    }


    private NodeA inserirRecursive(NodeA node, E element) throws ArbreException {
        if (node == null) return new NodeA(element);
        int cmp = element.compareTo(node.info);
        if (cmp < 0) node.esq = inserirRecursive(node.esq, element);
        else if (cmp > 0) node.dret = inserirRecursive(node.dret, element);
        else throw new ArbreException("Element already exists.");
        return node;
    }

    @Override
    public void esborrar(E element) throws ArbreException {
        arrel = esborrarRecursive(arrel, element);
    }

    private NodeA esborrarRecursive(NodeA node, E element) throws ArbreException {
        if (node == null) throw new ArbreException("Element not found.");
        int cmp = element.compareTo(node.info);
        if (cmp < 0) node.esq = esborrarRecursive(node.esq, element);
        else if (cmp > 0) node.dret = esborrarRecursive(node.dret, element);
        else {
            if (node.esq == null) return node.dret;
            if (node.dret == null) return node.esq;
            NodeA minNode = findMin(node.dret);
            node.info = minNode.info;
            node.dret = esborrarMinim(node.dret);
        }
        return node;
    }

    private NodeA findMin(NodeA node) {
        while (node.esq != null) {
            node = node.esq;
        }
        return node;
    }

    private NodeA esborrarMinim(NodeA node) {
        if (node.esq == null) return node.dret;
        node.esq = esborrarMinim(node.esq);
        return node;
    }

    @Override
    public boolean membre(E element) {
        return membreRecursive(arrel, element);
    }

    private boolean membreRecursive(NodeA node, E element) {
        if (node == null) return false;
        int cmp = element.compareTo(node.info);
        if (cmp < 0) return membreRecursive(node.esq, element);
        else if (cmp > 0) return membreRecursive(node.dret, element);
        return true;
    }

    @Override
    public E arrel() throws ArbreException {
        if (arrel == null) throw new ArbreException("Tree is empty.");
        return arrel.info;
    }

    @Override
    public Acb<E> fillEsquerre() throws CloneNotSupportedException {
        AcbEnll<E> subArbreEsquerre = new AcbEnll<>();
        if (arrel != null && arrel.esq != null) {
            subArbreEsquerre.arrel = arrel.esq.clone();
        }
        return subArbreEsquerre;
    }

    @Override
    public Acb<E> fillDret() throws CloneNotSupportedException {
        AcbEnll<E> subArbreDret = new AcbEnll<>();
        if (arrel != null && arrel.dret != null) {
            subArbreDret.arrel = arrel.dret.clone();
        }
        return subArbreDret;
    }

    @Override
    public boolean arbreBuit() {
        return arrel == null;
    }

    @Override
    public void buidar() {
        arrel = null;
    }

    public Queue<E> getAscendentList() {
        Queue<E> queue = new LinkedList<>();
        omplirInOrdre(arrel, queue);
        return queue;
    }

    private void omplirInOrdre(NodeA node, Queue<E> queue) {
        if (node == null) return;
        omplirInOrdre(node.esq, queue);
        queue.add(node.info);
        omplirInOrdre(node.dret, queue);
    }

    public Queue<E> getDescendentList() {
        Queue<E> queue = new LinkedList<>();
        omplirReverseInOrdre(arrel, queue);
        return queue;
    }

    private void omplirReverseInOrdre(NodeA node, Queue<E> queue) {
        if (node == null) return;
        omplirReverseInOrdre(node.dret, queue);
        queue.add(node.info);
        omplirReverseInOrdre(node.esq, queue);
    }

    public int cardinalitat() {
        return cardinalitatRecursive(arrel);
    }

    private int cardinalitatRecursive(NodeA node) {
        if (node == null) return 0;
        return 1 + cardinalitatRecursive(node.esq) + cardinalitatRecursive(node.dret);
    }
}
