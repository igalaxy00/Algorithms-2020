package lesson4;

import java.util.*;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Префиксное дерево для строк
 */
public class Trie extends AbstractSet<String> implements Set<String> {

    private static class Node {
        Map<Character, Node> children = new LinkedHashMap<>();
        Node parent;

        public boolean isEnd() {
            return this.children.containsKey('\u0000');
        }
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
        boolean modif = false;
        for (char character : withZero(element).toCharArray()) {
            Node child = current.children.get(character);
            if (child != null)
                current = child;
            else {
                Node newChild = new Node();
                newChild.parent = current;
                current.children.put(character, newChild);
                current = newChild;
                modif = true;
            }
        }
        if (modif)
            size++;

        return modif;
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

    public class TrieIterator implements Iterator<String> {
        Set<Node> iter = new HashSet<>();
        Node cursor = Trie.this.root;
        String last = "";
        String collected = last;
        int count = 0;

        @Override
        //Трудоемкость Ресурсоемкость O(1)
        public boolean hasNext() {
            if (Trie.this.size() == 0)
                return false;
            else
                return count < Trie.this.size();
        }

        @Override
        //Трудоемкость Ресурсоемкость O(N)
        public String next() {
            if (!hasNext()) throw new IllegalStateException();
            while (true) {
                Set<Character> key = cursor.children
                        .keySet().stream().filter(o -> !(iter
                                .contains(cursor.children.get(o))) && o != '\u0000')
                        .collect(Collectors.toSet());
                if (key.isEmpty()) {
                    String delayedReturn = "";
                    if (cursor.isEnd())
                        delayedReturn = collected;
                    for (Character child : cursor.children.keySet())
                        iter.remove(cursor.children.get(child));
                    collected = collected.substring(0, collected.length() - 1);
                    iter.add(cursor);
                    cursor = cursor.parent;
                    if (!delayedReturn.isEmpty()) {
                        count++;
                        last = delayedReturn;
                        return delayedReturn;
                    }
                } else {
                    for (Character character : key) {
                        Node node = cursor.children.get(character);
                        if (node.isEnd() && node.children.size() == 1) {
                            count++;
                            iter.add(node);
                            last = collected + character;
                            return collected + character;
                        }
                    }

                    Character next = key.stream()
                            .filter(o -> o != '\u0000')
                            .collect(Collectors.toSet()).iterator().next();
                    cursor = cursor.children.get(next);
                    collected += next;
                }
            }
        }

        @Override
        //Трудоемкость и ресурсоемкость O(N)
        public void remove() {
            if (iter.size() == 0)
                throw new IllegalStateException();
            else {
                Node remCur = cursor;
                String remin = last.substring(collected.length());
                String removingState = last;
                for (int i = 0; i < remin.length(); i++)
                    if (remCur.children.containsKey(remin.charAt(i)))
                        remCur = remCur.children.get(remin.charAt(i));
                    else
                        throw new IllegalStateException();
                if (remCur.isEnd()) {
                    remCur.children.remove('\u0000');
                    if (remCur.children.size() == 0) {
                        do {
                            remCur = remCur.parent;
                            remCur.children.remove(removingState.charAt(removingState.length() - 1));
                            removingState = removingState.substring(0, removingState.length() - 1);
                        } while (remCur.children.size() == 0);
                    }
                } else
                    throw new IllegalStateException();
                size--;
                count--;
            }
        }
    }
}