//package com.company;
//
//import java.util.ArrayList;
//
//import static com.company.Color.RUBRO;
//import static com.company.Color.NEGRO;
//
//public class RuboNegra<T extends Comparable<T>> {
//    protected Node<T> raiz;
//
//    public RuboNegra() {
//        this.raiz = new Node<T>();
//    }
//
//    public boolean insere(T valor) {
//        Node<T> node = raiz;
//        Node<T> anterior = null;
//        while (node.info != null) {
//            anterior = node;
//            if (node.info.compareTo(valor) < 0) {
//                node = node.dir;
//            } else if (node.info.compareTo(valor) > 0) {
//                node = node.esq;
//            } else {
//                return false;
//            }
//        }
//        node.pai = anterior;
//        node.info = valor;
//        node.esq = new Node<T>();
//        node.dir = new Node<T>();
//        node.color = RUBRO;
//
//        if (anterior != null) {
//            verificarBalanceamentoInsercao(node);
//        }
//        return true;
//    }
//
//    public void verificarBalanceamentoInsercao(Node<T> novoNo) {
//        Node<T> avo = novoNo.pai.pai;
//
//        while (novoNo.color == RUBRO && novoNo.pai != null && novoNo.pai.color == RUBRO) {
//            if (novoNo.pai.pai == null) {
//                break;
//            }
//            if (novoNo.pai.equals(novoNo.pai.pai.esq)) {
//                Node<T> tio = novoNo.pai.pai.dir;
//                if (tio.color == RUBRO) {
//                    novoNo.pai.color = NEGRO;
//                    tio.color = NEGRO;
//                    avo.color = RUBRO;
//                    novoNo = avo;
//                } else {
//                    if (novoNo.equals(novoNo.pai.pai.dir)) {
//                        rotacionaEsquerda(novoNo.pai);
//                        novoNo = novoNo.esq;
//                    }
//                    rotacionaDireita(avo);
//                    avo.color = RUBRO;
//                    novoNo.pai.color = NEGRO;
//                    novoNo = novoNo.pai;
//                }
//            } else {
//                if (novoNo.pai.equals(novoNo.pai.pai.dir)) {
//                    Node<T> tio = novoNo.pai.pai.esq;
//                    if (tio.color == RUBRO) {
//                        novoNo.pai.color = NEGRO;
//                        tio.color = NEGRO;
//                        novoNo.pai.pai.color = RUBRO;
//                        novoNo = novoNo.pai.pai;
//                    } else {
//                        if (novoNo.equals(novoNo.pai.esq)) {
//                            rotacionaDireita(novoNo.pai);
//                            novoNo = novoNo.dir;
//                        }
//                        rotacionaEsquerda(novoNo.pai.pai);
//                        novoNo.pai.dir.color = RUBRO;
//                        novoNo.pai.color = NEGRO;
//                        novoNo = novoNo.pai;
//                    }
//                }
//            }
//        }
//        raiz.color = NEGRO;
//
//    }
//
//    public Node verificaBalanceamentoAposRemocao(Node<T> node) {
//        while (node != raiz && node.color == NEGRO) {
//            if (node.equals(node.pai.esq)) {  // caso 1
//                Node irmao = node.pai.dir;
//                if (irmao.color == RUBRO) {
//                    irmao.color = NEGRO;
//                    node.pai.color = RUBRO;
//                    rotacionaEsquerda(irmao);
//                    irmao = node.pai.dir;
//                }
//                if (irmao.esq.color == NEGRO && irmao.dir.color == NEGRO) { // CASO 2
//                    irmao.color = RUBRO;
//                    node = node.pai;
//                } else {
//                    if (irmao.dir.color == NEGRO) {
//                        irmao.esq.color = NEGRO;
//                        irmao.color = RUBRO;
//                        rotacionaDireita(irmao);
//                        irmao = node.pai.dir;
//                    }
//                    irmao.color = node.pai.color;
//                    node.pai.color = NEGRO;
//                    irmao.dir.color = NEGRO;
//                    rotacionaEsquerda(irmao.pai);
//                    node = this.raiz;
//
//                }
//            } else {
//                if (node.equals(node.pai.dir)) {
//                    Node irmao = node.pai.esq;
//                    if (irmao.color == RUBRO) {
//                        irmao.color = NEGRO;
//                        node.pai.color = RUBRO;
//                        rotacionaDireita(node.pai);
//                        irmao = node.pai.esq;
//                    }
//                    irmao.color = node.pai.color;
//                    node.pai.color = NEGRO;
//                    irmao.esq.color = NEGRO;
//                    rotacionaDireita(node.pai);
//                    node = this.raiz;
//                    if (raiz.color == RUBRO) {
//                        raiz.color = NEGRO;
//                    }
//                }
//            }
//            node.color = NEGRO;
//            return node;
//        }
//        return node;
//    }
//
//    public Node remove(T valor) {
//        Node<T> aux = null;
//        Node<T> ptr = this.raiz;
//        while (ptr.info != null) {
//            if (ptr.info.compareTo(valor) < 0) {
//                ptr = ptr.dir;
//            } else if (ptr.info.compareTo(valor) > 0) {
//                ptr = ptr.esq;
//            } else {
//                aux = ptr;
//                if (ptr.esq.info != null && ptr.dir.info != null) {
//                    aux = ptr.esq;
//                    ptr.info = aux.info;
//                    removeMaiorEsquerda(ptr);
//                    verificaBalanceamentoAposRemocao(aux);
//                } else if (ptr.dir.info == null) {
//                    ptr.info = ptr.esq.info;
//                    ptr.dir = ptr.esq.dir;
//                    ptr.esq = ptr.esq.esq;
//                    verificaBalanceamentoAposRemocao(ptr);
//                    return aux;
//                }
//            }
//        }
//        return aux;
//    }
//
//    public Node removeMaiorEsquerda(Node<T> ptr) {
//        ptr = ptr.esq;
//        while (ptr.dir.info != null) {
//            ptr = ptr.dir;
//        }
//        ptr.info = ptr.esq.info;
//        ptr.dir = ptr.esq.dir;
//        ptr.esq = ptr.esq.esq;
//        return ptr;
//    }
//
//    private void rotacionaDireita(Node<T> node) {
//        Node filho = node.esq;
//        Node neto = filho.dir;
//        node.esq = neto;
//        filho.dir = node;
//        if (node.pai != null) {
//            if (node.pai.esq == node) {
//                node.pai.esq = filho;
//            } else {
//                node.pai.dir = filho;
//            }
//
//        } else {
//            raiz = filho;
//        }
//        filho.pai = node.pai;
//        node.pai = filho;
//        neto.pai = node;
//    }
//
//    private void rotacionaEsquerda(Node<T> node) {
//        Node filho = node.dir;
//        Node neto = filho.esq;
//        node.dir = neto;
//        filho.esq = node;
//        if (node.pai != null) {
//            if (node.pai.dir == node) {
//                node.pai.dir = filho;
//            } else {
//                node.pai.esq = filho;
//            }
//        } else {
//            raiz = filho;
//        }
//        filho.pai = node.pai;
//        node.pai = filho;
//        neto.pai = node;
//    }
//
//    public ArrayList<Node<T>> impressaoEmOrdem() {
//        ArrayList<Node<T>> ret = new ArrayList<Node<T>>();
//        impressaoEmOrdem(raiz, ret);
//        return ret;
//
//    }
//
//    private void impressaoEmOrdem(Node<T> node, ArrayList<Node<T>> list) {
//        if (node != null) {
//            impressaoEmOrdem(node.esq, list);
//            list.add(node);
//            impressaoEmOrdem(node.dir, list);
//        }
//
//    }
//
//}
//
