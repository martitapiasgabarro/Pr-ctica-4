package src.EstructuraArbre;

import java.util.LinkedList;
import java.util.Queue;

public class AcbEnll<E extends Comparable<E>> implements Acb<E> {

    private NodeA arrel; // Node arrel de l'arbre

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
        protected NodeA clone() throws CloneNotSupportedException {
            NodeA copia = (NodeA) super.clone();
            if (esq != null) copia.esq = esq.clone();
            if (dret != null) copia.dret = dret.clone();
            return copia;
        }
    }

    // Constructor per defecte: arbre buit
    public AcbEnll() {
        this.arrel = null;
    }


    // Inserció d'un element
    @Override
    public void inserir(E element) throws ArbreException {
        if (membre(element)) {
            throw new ArbreException("Element ja existeix.");
        }
        arrel = inserirRecursive(arrel, element);
    }

    private NodeA inserirRecursive(NodeA node, E element) {
        if (node == null) return new NodeA(element);
        if (element.compareTo(node.info) < 0) {
            node.esq = inserirRecursive(node.esq, element);
        } else if (element.compareTo(node.info) > 0) {
            node.dret = inserirRecursive(node.dret, element);
        }
        return node;
    }

    // Esborrat d'un element
    public void esborrar(E element) throws ArbreException {
        if (arrel == null) {
            throw new ArbreException("L'arbre és buit.");
        }
        if (!membre(element)) {
            throw new ArbreException("Element no trobat.");
        }
        arrel = esborrarRecursive(arrel, element);
    }


    private NodeA esborrarRecursive(NodeA node, E element) {
        if (node == null) return null; // Node no trobat
        if (element.compareTo(node.info) < 0) {
            node.esq = esborrarRecursive(node.esq, element); // Cerca a l'esquerra
        } else if (element.compareTo(node.info) > 0) {
            node.dret = esborrarRecursive(node.dret, element); // Cerca a la dreta
        } else { // Node trobat
            // Node trobat
            if (node.esq == null) return node.dret;
            if (node.dret == null) return node.esq;
            // Node amb dos fills
            node.info = trobarMinim(node.dret).info;
            node.dret = esborrarRecursive(node.dret, node.info);
        }
        return node;
    }

    private NodeA trobarMinim(NodeA node) {
        while (node.esq != null) {
            node = node.esq;
        }
        return node;
    }

    @Override
    public boolean membre(E element) {
        return membreRecursive(arrel, element);
    }

    private boolean membreRecursive(NodeA node, E element) {
        if (node == null) return false;
        if (element.compareTo(node.info) == 0) return true;
        if (element.compareTo(node.info) < 0) return membreRecursive(node.esq, element);
        return membreRecursive(node.dret, element);
    }

    @Override
    public E arrel() throws ArbreException {
        if (arbreBuit()) {
            throw new ArbreException("L'arbre és buit.");
        }
        return arrel.info;
    }


    @Override
    public Acb<E> fillEsquerre() throws CloneNotSupportedException {
        if (arrel != null && arrel.esq != null) {
            AcbEnll<E> subarbre = new AcbEnll<>();
            subarbre.arrel = arrel.esq.clone();
            return subarbre;
        }
        return new AcbEnll<>();
    }


    @Override
    public Acb<E> fillDret() throws CloneNotSupportedException {
        if (arrel != null && arrel.dret != null) {
            AcbEnll<E> subarbre = new AcbEnll<>();
            subarbre.arrel = arrel.dret.clone();
            return subarbre;
        }
        return new AcbEnll<>();
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
        omplirReverseInOrdre(node.esq, queue); // Processa primer el subarbre dret
        queue.add(node.info);                   // Afegeix el node actual
        omplirReverseInOrdre(node.dret, queue); // Processa després el subarbre esquerre
    }


    public int cardinalitat() {
        return cardinalitatRecursive(arrel);
    }

    private int cardinalitatRecursive(NodeA node) {
        if (node == null) return 0;
        return 1 + cardinalitatRecursive(node.esq) + cardinalitatRecursive(node.dret);
    }
}
