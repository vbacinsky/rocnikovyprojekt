package server;

import java.util.ArrayList;

public class Graph {
    /* Pre kazdy vrchol zoznam vrcholov, do ktorych z daneho vrcholu vedie hrana: */
    private ArrayList<ArrayList<Integer>> adjLists;

    /* Pocet hran v grafe: */
    private int numEdges;

    /* Konstruktor, ktory ako parameter dostane prirodzene cislo numVertices
       a vytvori graf o numVertices vrcholoch a bez jedinej hrany: */
    public Graph(int numVertices) {
        adjLists = new ArrayList<>();
        for (int i = 0; i < numVertices; i++) {
            adjLists.add(new ArrayList<>());
        }
        numEdges = 0;
    }

    public int getNumberOfVertices() {
        return adjLists.size();
    }

    public int getNumberOfEdges() {
        return numEdges;
    }

    public void addEdge(int from, int to) {
        adjLists.get(from).add(to);
        numEdges++;
    }

    public boolean existsEdge(int from, int to) {
        return adjLists.get(from).contains(to);
    }

    public ArrayList<ArrayList<Integer>> getAdjLists() {
        return this.adjLists;
    }
}
