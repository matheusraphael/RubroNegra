package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Main {
    //testes
    public static void main(String[] args) throws IOException {
        BufferedReader ler = new BufferedReader(new InputStreamReader(System.in));
        RubroNegra<String> arvAssassino = new RubroNegra<>();
        RubroNegra<String> arvVitima = new RubroNegra<>();
        String entrada;

        do {
            entrada = ler.readLine();
            if (entrada.length() == 0) {
                entrada = null;
            } else {
                String[] vet = entrada.split(" ");
                arvAssassino.insere(vet[0]);
                arvVitima.insere(vet[1]);
            }

        } while (ler.ready());

        System.out.println("HALL OF MURDERERS");
        for (var aux : arvVitima.impressaoEmOrdem()) {
            if (arvAssassino.buscar(String.valueOf(aux.info))) {
                arvAssassino.remove(String.valueOf(aux.info));
            }
        }
        

    }

    //estrutura
    static class RubroNegra<T extends Comparable<T>> {
        public Node<T> raiz;

        public RubroNegra() {
            this.raiz = new Node<T>();
        }


        public boolean insere(T valor) {
            Node<T> node = raiz;
            Node<T> anterior = null;
            while (node.info != null) {
                anterior = node;
                if (node.info.compareTo(valor) < 0) {
                    node = node.dir;
                } else if (node.info.compareTo(valor) > 0) {
                    node = node.esq;
                } else {
                    return false;
                }
            }
            node.pai = anterior;
            node.info = valor;
            node.esq = new Node<T>();
            node.dir = new Node<T>();
            node.color = Color.RUBRO;

            if (anterior != null) {
                verificarBalanceamentoInsercao(node);
            }
            return true;
        }


        public void verificarBalanceamentoInsercao(Node<T> novoNo) {
            Node<T> avo = novoNo.pai.pai;

            while (novoNo.color == Color.RUBRO && novoNo.pai != null && novoNo.pai.color == Color.RUBRO) {
                if (novoNo.pai.pai == null) {
                    break;
                }
                if (novoNo.pai.equals(novoNo.pai.pai.esq)) {
                    Node<T> tio = novoNo.pai.pai.dir;
                    if (tio.color == Color.RUBRO) {
                        novoNo.pai.color = Color.NEGRO;
                        tio.color = Color.NEGRO;
                        avo.color = Color.RUBRO;
                        novoNo = avo;
                    } else {
                        if (novoNo.equals(novoNo.pai.pai.dir)) {
                            rotacionaEsquerda(novoNo.pai);
                            novoNo = novoNo.esq;
                        }
                        rotacionaDireita(avo);
                        avo.color = Color.RUBRO;
                        novoNo.pai.color = Color.NEGRO;
                        novoNo = novoNo.pai;
                    }
                } else {
                    if (novoNo.pai.equals(novoNo.pai.pai.dir)) {
                        Node<T> tio = novoNo.pai.pai.esq;
                        if (tio.color == Color.RUBRO) {
                            novoNo.pai.color = Color.NEGRO;
                            tio.color = Color.NEGRO;
                            novoNo.pai.pai.color = Color.RUBRO;
                            novoNo = novoNo.pai.pai;
                        } else {
                            if (novoNo.equals(novoNo.pai.esq)) {
                                rotacionaDireita(novoNo.pai);
                                novoNo = novoNo.dir;
                            }
                            rotacionaEsquerda(novoNo.pai.pai);
                            novoNo.pai.dir.color = Color.RUBRO;
                            novoNo.pai.color = Color.NEGRO;
                            novoNo = novoNo.pai;
                        }
                    }
                }
            }
            raiz.color = Color.NEGRO;

        }

        public Node verificaBalanceamentoAposRemocao(Node<T> node) {
            while (node != raiz && node.color == Color.NEGRO) {
                if (node.equals(node.pai.esq)) {  // caso 1
                    Node irmao = node.pai.dir;
                    if (irmao.color == Color.RUBRO) {
                        irmao.color = Color.NEGRO;
                        node.pai.color = Color.RUBRO;
                        rotacionaEsquerda(irmao);
                        irmao = node.pai.dir;
                    }
                    if (irmao.esq.color == Color.NEGRO && irmao.dir.color == Color.NEGRO) { // CASO 2
                        irmao.color = Color.RUBRO;
                        node = node.pai;
                    } else {
                        if (irmao.dir.color == Color.NEGRO) {
                            irmao.esq.color = Color.NEGRO;
                            irmao.color = Color.RUBRO;
                            rotacionaDireita(irmao);
                            irmao = node.pai.dir;
                        }
                        irmao.color = node.pai.color;
                        node.pai.color = Color.NEGRO;
                        irmao.dir.color = Color.NEGRO;
                        rotacionaEsquerda(irmao.pai);
                        node = this.raiz;

                    }
                } else {
                    if (node.equals(node.pai.dir)) {
                        Node irmao = node.pai.esq;
                        if (irmao.color == Color.RUBRO) {
                            irmao.color = Color.NEGRO;
                            node.pai.color = Color.RUBRO;
                            rotacionaDireita(node.pai);
                            irmao = node.pai.esq;
                        }
                        irmao.color = node.pai.color;
                        node.pai.color = Color.NEGRO;
                        irmao.esq.color = Color.NEGRO;
                        rotacionaDireita(node.pai);
                        node = this.raiz;
                        if (raiz.color == Color.RUBRO) {
                            raiz.color = Color.NEGRO;
                        }
                    }
                }
                node.color = Color.NEGRO;
                return node;
            }
            return node;
        }

        public Node remove(T valor) {
            Node<T> aux = null;
            Node<T> ptr = this.raiz;
            while (ptr.info != null) {
                if (ptr.info.compareTo(valor) < 0) {
                    ptr = ptr.dir;
                } else if (ptr.info.compareTo(valor) > 0) {
                    ptr = ptr.esq;
                } else {
                    aux = ptr;
                    if (ptr.esq.info != null && ptr.dir.info != null) {
                        aux = ptr.esq;
                        ptr.info = aux.info;
                        removeMaiorEsquerda(ptr);
                        verificaBalanceamentoAposRemocao(aux);
                    } else if (ptr.dir.info == null) {
                        ptr.info = ptr.esq.info;
                        ptr.dir = ptr.esq.dir;
                        ptr.esq = ptr.esq.esq;
                        verificaBalanceamentoAposRemocao(ptr);
                        return aux;
                    }
                }
            }
            return aux;
        }

        public Node removeMaiorEsquerda(Node<T> ptr) {
            ptr = ptr.esq;
            while (ptr.dir.info != null) {
                ptr = ptr.dir;
            }
            ptr.info = ptr.esq.info;
            ptr.dir = ptr.esq.dir;
            ptr.esq = ptr.esq.esq;
            return ptr;
        }

        private void rotacionaDireita(Node<T> node) {
            Node filho = node.esq;
            Node neto = filho.dir;
            node.esq = neto;
            filho.dir = node;
            if (node.pai != null) {
                if (node.pai.esq == node) {
                    node.pai.esq = filho;
                } else {
                    node.pai.dir = filho;
                }

            } else {
                raiz = filho;
            }
            filho.pai = node.pai;
            node.pai = filho;
            neto.pai = node;
        }

        private void rotacionaEsquerda(Node<T> node) {
            Node filho = node.dir;
            Node neto = filho.esq;
            node.dir = neto;
            filho.esq = node;
            if (node.pai != null) {
                if (node.pai.dir == node) {
                    node.pai.dir = filho;
                } else {
                    node.pai.esq = filho;
                }
            } else {
                raiz = filho;
            }
            filho.pai = node.pai;
            node.pai = filho;
            neto.pai = node;
        }


        public ArrayList<Node<T>> impressaoEmOrdem() {
            ArrayList<Node<T>> ret = new ArrayList<Node<T>>();
            impressaoEmOrdem(raiz, ret);
            return ret;

        }

        private void impressaoEmOrdem(Node<T> node, ArrayList<Node<T>> list) {
            if (node != null) {
                impressaoEmOrdem(node.esq, list);
                list.add(node);
                impressaoEmOrdem(node.dir, list);
            }

        }



    }

    static class Node<T extends Comparable<T>> {
        protected T info;
        public Node<T> esq, dir, pai;
        public Color color;

        public Node() {
            this.info = null;
            this.esq = null;
            this.dir = null;
            this.pai = null;
            this.color = Color.NEGRO;
        }

        @Override
        public String toString() {
            return (String) info;
        }
    }

    public enum Color {
        RUBRO, NEGRO;
    }


}


