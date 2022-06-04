package unsw.graph;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;
import java.util.Collections;

public class DepthFirstGraphIterator<N extends Comparable<N>> implements Iterator<N> {
    private Graph<N> g;
    private N currentNode;
    Stack<N> stack = new Stack<N>();
    List<N> visited = new ArrayList<N>();

    public DepthFirstGraphIterator(Graph<N> g, N startingNode) {
        this.g = g;
        currentNode = startingNode;
        stack.push(startingNode);
    }   

    @Override
    public boolean hasNext() {
        if (visited.containsAll(g.getAdjacentNodes(currentNode))) return false;
        return !g.getAdjacentNodes(currentNode).isEmpty();
    }

    @Override
    public N next() {
        if(!stack.isEmpty()) {
            N vertex = stack.pop();
            currentNode = vertex;
            visited.add(vertex);
            if (this.hasNext()) {
                List<N> nextVertices = g.getAdjacentNodes(vertex);
                Collections.reverse(nextVertices);
                nextVertices.removeAll(visited);
                nextVertices.removeAll(stack);
                for (N node : nextVertices) {
                    stack.push(node);
                }
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