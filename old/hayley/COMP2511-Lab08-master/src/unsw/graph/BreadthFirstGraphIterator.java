package unsw.graph;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;

public class BreadthFirstGraphIterator<N extends Comparable<N>> implements Iterator<N> {
    private Graph<N> g;
    private N currentNode;
    LinkedList<N> queue = new LinkedList<N>();
    List<N> visited = new ArrayList<N>();

    public BreadthFirstGraphIterator(Graph<N> g, N startingNode) {
        this.g = g;
        currentNode = startingNode;
        queue.add(startingNode);
    }   

    @Override
    public boolean hasNext() {
        if (visited.containsAll(g.getAdjacentNodes(currentNode))) return false;
        return !g.getAdjacentNodes(currentNode).isEmpty();
    }

    @Override
    public N next() {
        if (queue.size() != 0) {
            N vertex = queue.poll();
            currentNode = vertex;
            visited.add(vertex);
            if (this.hasNext()) {
                List<N> nextVertices = g.getAdjacentNodes(vertex);
                nextVertices.removeAll(visited);
                nextVertices.removeAll(queue);
                queue.addAll(nextVertices);
            }
            return vertex;
        }
        return null;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
