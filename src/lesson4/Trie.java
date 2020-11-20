package lesson4;

import java.util.*;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Префиксное дерево для строк
 */
public class Trie extends AbstractSet<String> implements Set<String> {

    private static class Node {
        Map<Character, Node> children = new LinkedHashMap<>();
    }

    private Node root = new Node();

    private int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root.children.clear();
        size = 0;
    }

    private String withZero(String initial) {
        return initial + (char) 0;
    }

    @Nullable
    private Node findNode(String element) {
        Node current = root;
        for (char character : element.toCharArray()) {
            if (current == null) return null;
            current = current.children.get(character);
        }
        return current;
    }

    @Override
    public boolean contains(Object o) {
        String element = (String) o;
        return findNode(withZero(element)) != null;
    }

    @Override
    public boolean add(String element) {
        Node current = root;
        boolean modified = false;
        for (char character : withZero(element).toCharArray()) {
            Node child = current.children.get(character);
            if (child != null) {
                current = child;
            } else {
                modified = true;
                Node newChild = new Node();
                current.children.put(character, newChild);
                current = newChild;
            }
        }
        if (modified) {
            size++;
        }
        return modified;
    }

    @Override
    public boolean remove(Object o) {
        String element = (String) o;
        Node current = findNode(element);
        if (current == null) return false;
        if (current.children.remove((char) 0) != null) {
            size--;
            return true;
        }
        return false;
    }

    /**
     * Итератор для префиксного дерева
     * <p>
     * Спецификация: {@link Iterator} (Ctrl+Click по Iterator)
     * <p>
     * Сложная
     */
    @NotNull
    @Override
    public Iterator<String> iterator() {
        return new TrieIterator();
    }

    private class TrieIterator implements Iterator<String> {
        String removeWord = "";
        private final ArrayDeque<String> queue = new ArrayDeque<>();

        private TrieIterator() {
            if (root == null) return;
            wordToPush(root, "");
        }

        private void wordToPush(Node node, String str) {
            if (node.children == null) return;
            for (Map.Entry<Character, Node> entry : node.children.entrySet()) {
                if (entry.getKey() == (char) 0) {
                    queue.push(str);
                }
                wordToPush(entry.getValue(), str + entry.getKey());
            }
        }

        @Override
        public boolean hasNext() {
            return queue.peek() != null;
        }

        // Трудоемкость O(1)
        //Ресурсоемкость - О(1)
        @Override
        public String next() {
            if (!hasNext()) throw new IllegalStateException();
            removeWord = queue.pop();
            return removeWord;
        }


        // Трудоемкость: O(logN)
        //Ресурсоемкость: O(logN*N)
        @Override
        public void remove() {
            if (removeWord.equals("")) throw new IllegalStateException();
            Trie.this.remove(removeWord);
            removeWord = "";
        }
    }

}