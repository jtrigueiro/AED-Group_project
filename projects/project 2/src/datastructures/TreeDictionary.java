package datastructures;

import datastructures.exception.ElementAlreadyExists;
import datastructures.exception.ElementDoesNotExists;

public class TreeDictionary<K extends Comparable<K>, V> implements SortedDictionary<K, V> {

    private static class TreeMapIterator<V> implements Iterator<V> {
        enum NodeState {
            CHECKING_LEFT,
            CHECKING_NODE,
            CHECKING_RIGHT
        }
        private static class StackedNode<V> {
            final Node<?, V> node;
            NodeState state;
            StackedNode<V> parentElement;

            StackedNode(Node<?, V> node, NodeState state, StackedNode<V> parentElement) {
                this.node = node;
                this.state = state;
                this.parentElement = parentElement;
            }
        }
        private StackedNode<V> stackedNode;
        private TreeMapIterator(Node<?, V> root) {
            if (root != null) {
                stackedNode = new StackedNode<>(root, NodeState.CHECKING_LEFT, null);
                findNext();
            } else {
                stackedNode = null;
            }
        }

        /**
         * Busca recursiva
         * A interface das estruturas de dados especificada estabelece que para percorrer os elementos, os iteradores
         * devem ser usados. O Java nao possui geradores, e a api stream, que substituiria isto, nao foi abordada, o
         * que obriga a gerenciar manualmente o estado de execucao da funcao. Por isto, em analogia ao Stack de execucao,
         * foi criado um stack que representa os vertices, juntamente com o estado da função recursiva.
         */
        private void findNext() {
            switch (stackedNode.state) {
                case CHECKING_LEFT:
                    stackedNode.state = NodeState.CHECKING_NODE;
                    if (stackedNode.node.left != null) {
                        stackedNode = new StackedNode<>(stackedNode.node.left, NodeState.CHECKING_LEFT, stackedNode);
                        findNext();
                        break;
                    }
                case CHECKING_NODE:
                    stackedNode.state = NodeState.CHECKING_RIGHT;
                    break;
                case CHECKING_RIGHT:
                    if (stackedNode.node.right != null) {
                        stackedNode = new StackedNode<>(stackedNode.node.right, NodeState.CHECKING_LEFT, stackedNode);
                        findNext();
                    } else {
                        while(stackedNode != null && stackedNode.state == NodeState.CHECKING_RIGHT) {
                            stackedNode = stackedNode.parentElement;
                        }
                        if (stackedNode != null) {
                            findNext();
                        }
                    }
            }
        }

        @Override
        public boolean hasNext() {
            return stackedNode != null;
        }

        @Override
        public V next() {
            V result = stackedNode.node.value;
            findNext();
            return result;
        }
    }
    private static class Node<K extends Comparable<K>, V>  {
        private K key;
        private V value;
        private Node<K, V> left;
        private Node<K, V> right;
        private int leftDepth;
        private int rightDepth;
        Node(K key, V value) {
            this.key = key;
            this.value = value;
            leftDepth = rightDepth = 0;
        }

        Node<K, V> find(K key) {
            if (key.compareTo(this.key) < 0 && left != null) {
                return left.find(key);
            } else if (this.key.compareTo(key) == 0) {
                return this;
            } else if (right != null){
                return right.find(key);
            }
            throw new ElementDoesNotExists();
        }

        void balance() {
            if (getBalance() > 0) {
                switchRootWithRightNode();
            } else {
                switchRootWithLeftNode();
            }
        }

        void balanceIfNecessary() {
            if (getBalance() * getBalance() == 4) {
                if (getBalance() == -2 && left != null && left.getBalance() > 0) {
                    left.balance();
                } else if (right != null && right.getBalance() < 0) {
                    right.balance();
                }
                balance();
            }
        }

        void switchRootWithLeftNode() {
            assert left != null;
            Node<K, V> leftSubTree = left.left;
            Node<K, V> centerSubtree = left.right;
            Node<K, V> rightSubTree = right;
            Node<K, V> newThis = new Node<K, V>(key, value);
            key = left.key;
            value = left.value;
            newThis.right = rightSubTree;
            newThis.left = centerSubtree;
            newThis.updateLeftDepth();
            newThis.updateRightDepth();
            left = leftSubTree;
            right = newThis;
            updateLeftDepth();
            updateRightDepth();
        }

        void switchRootWithRightNode() {
            assert right != null;
            Node<K, V> leftSubTree = left;
            Node<K, V> centerSubtree = right.left;
            Node<K, V> rightSubTree = right.right;
            Node<K, V> newThis = new Node<K, V>(key, value);
            key = right.key;
            value = right.value;
            newThis.left = leftSubTree;
            newThis.right = centerSubtree;
            newThis.updateLeftDepth();
            newThis.updateRightDepth();
            left = newThis;
            right = rightSubTree;
            updateLeftDepth();
            updateRightDepth();
        }

        private V remove(K key, Node<K, V> parent) {
            assert parent != null || key != this.key;
            if (this.key == key) {
                V result = this.value;
                if (this.left == null) {
                    parent.replaceChild(this, this.right);
                } else if (this.right == null) {
                    parent.replaceChild(this, this.left);
                } else {
                    final Node<K, V> removedMinimal = this.right.removeMinimal(this);
                    this.value = removedMinimal.value;
                    this.key = removedMinimal.key;
                    updateLeftDepth();
                    updateRightDepth();
                }
                balanceIfNecessary();
                return result;
            } else if (key.compareTo(this.key) < 0) {
                if (this.left == null) {
                    throw new ElementDoesNotExists();
                } else {
                    final V remove = this.left.remove(key, this);
                    updateLeftDepth();
                    balanceIfNecessary();
                    return remove;
                }
            } else {
                if (this.right == null) {
                    throw new ElementDoesNotExists();
                } else {
                    final V remove = this.right.remove(key, this);
                    updateRightDepth();
                    balanceIfNecessary();
                    return remove;
                }
            }

        }

        private Node<K, V> removeMinimal(Node<K, V> parent) {
            if (left == null) {
                if (parent != null) {
                    parent.replaceChild(this, this.right);
                }
                return this;
            } else {
                return left.removeMinimal(this);
            }
        }

        private void replaceChild(Node<K, V> toReplace, Node<K, V> replacement) {
            assert right == toReplace || left == toReplace;
            if (right == toReplace) {
                right = replacement;
                updateRightDepth();
            } else {
                left = replacement;
                updateLeftDepth();
            }
        }

        private void updateRightDepth() {
            rightDepth = right != null ? this.right.getHeight() + 1 : 0;
        }

        private void updateLeftDepth() {
            leftDepth = left != null ? this.left.getHeight() + 1 : 0;
        }

        V add(Node<K, V> node, int depth) {
            V result = null;
            if (node.key.compareTo(key) < 0) {
                if (left != null) {
                    result = left.add(node, depth + 1);
                } else {
                    left = node;
                }
                updateLeftDepth();
            } else if (node.key.compareTo(key) > 0) {
                if (right != null) {
                    result = right.add(node, depth + 1);
                } else {
                    right = node;
                }
                updateRightDepth();
            } else {
                result = value;
                this.value = node.value;
            }
            balanceIfNecessary();
            return result;
        }

        int getHeight() {
            return Math.max(this.rightDepth, this.leftDepth);
        }

        int getBalance() {
            return this.rightDepth - this.leftDepth;
        }
    }
    private int getHeight() {
        return root == null ? -1 : root.getHeight();
    }
    private Node<K, V> root = null;

    public V put(K key, V value) {
        Node<K,V> node = new Node<>(key, value);
        if (root == null) {
            root = node;
            return null;
        } else {
            return root.add(node, 0);
        }
    }

    @Override
    public V find(K key) {
        if (root == null) {
            throw new ElementDoesNotExists();
        }
        return root.find(key).value;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }


    @Override
    public V remove(K key) {
        if (root == null) {
            throw new ElementDoesNotExists();
        } else if (root.key.compareTo(key) == 0) {
            return removeRoot();
        } else {
            return root.remove(key, null);
        }
    }

    private V removeRoot() {
        V result = root.value;
        if (root.left == null) {
            root = root.right;
        } else if(root.right == null) {
            root = root.left;
        } else {
            final Node<K, V> minimal = root.right.removeMinimal(null);
            if (minimal == root.right) {
                minimal.left = root.left;
                root = minimal;
            } else {
                root.key = minimal.key;
                root.value = minimal.value;
            }
            root.updateLeftDepth();
            root.updateRightDepth();
        }
        return result;
    }

    public String toString(int valueSize) {
        V[] array = asArray();
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (int depth = 0; depth <= getHeight(); depth++) {
            builder.append(depth);
            int lineMarginSpace = (int) (Math.pow(2, getHeight()-depth)) - 1;
            int spaceBetweenValues = (int) (Math.pow(2, getHeight()-depth+1)) - 1;
            appendSpacesToBuilder(builder, lineMarginSpace * valueSize);
            for (int pos = 0; pos < Math.pow(2, depth); pos++) {
                final V value = array[i++];
                if (value != null) {
                    builder.append(value.toString());
                } else {
                    appendSpacesToBuilder(builder, valueSize);
                }
                appendSpacesToBuilder(builder, spaceBetweenValues * valueSize);
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    private void appendSpacesToBuilder(StringBuilder builder, int numberOfSpaces) {
        for (int j = 0; j < numberOfSpaces; j++) {
            builder.append(" ");
        }
    }

    public static void main(String[] args) {
        final int numberOfTrees = 30;
        final int numberOfElements = 30;
        for (int i = 0; i < numberOfTrees; i++) {
            TreeDictionary<Integer, Integer> treeDictionary = new TreeDictionary<>();
            for (int j = 0; j < numberOfElements; j++) {
                int num;
                do {
                    num = (int) (Math.random() * 89 + 10);
                } while (treeDictionary.hasKey(num));
                treeDictionary.put(num, num);
                assert treeDictionary.root.getBalance() * treeDictionary.root.getBalance() < 4;
                final Node<Integer, Integer> left = treeDictionary.root.left;
                final Node<Integer, Integer> right = treeDictionary.root.right;
                assert left == null || left.getBalance() * left.getBalance() < 4;
                assert right == null || right.getBalance() * right.getBalance() < 4;
                Iterator<Integer> it = treeDictionary.values();
                int last = it.next();
                while (it.hasNext()) {
                    int current = it.next();
                    assert current >= last;
                }
            }
            System.out.println(treeDictionary.toString(2));
            for (int j = 0; j < numberOfElements-1; j++) {
                Iterator<Integer> it = treeDictionary.values();
                int key = it.next();
                int value = treeDictionary.remove(key);
                assert key == value;
                assert treeDictionary.root.getBalance() * treeDictionary.root.getBalance() < 4;
                final Node<Integer, Integer> left = treeDictionary.root.left;
                final Node<Integer, Integer> right = treeDictionary.root.right;
                assert left == null || left.getBalance() * left.getBalance() < 4;
                assert right == null || right.getBalance() * right.getBalance() < 4;
                it = treeDictionary.values();
                int last = it.next();
                while (it.hasNext()) {
                    int current = it.next();
                    assert current >= last;
                }
            }
        }
    }
    public boolean hasKey(K key) {
        try {
            this.find(key);
            return true;
        } catch (ElementDoesNotExists ignore) {
            return false;
        }
    }


    /**
     * Armazena numa estrutura similar a uma matriz simetrica
     * tomando o de um vértice indice como binario, a posicao do algarismo significativo do seu sucessor corresponde
     * a profundidade do vertice, e o numero formado pelos outros algarismos corresponde a posicao, da esquerda
     * para direita.
     *
     * exemplo: índice = 0 -> sucessor = 1 -> em binário = 1 (algarismo mais significante em posicao 0 -> profundidade = 0, resto 0) -> vertice raiz
     * exemplo: indice = 36 -> sucessor = 37 -> em binário = 100101 (algarismo mais signficante em posicao 5 -> profundidade = 5, resto=5) -> quinto vertice de profundidade 5.
     *
     * Essa estrutura poderá ser mais ou menos esparca de acordo do quao balanceado esta a arvore
     **/
    @SuppressWarnings("unchecked")
    private V[] asArray() {
        V[] result = (V[]) new Object[(int) Math.pow(2,(getHeight() + 1))];
        fillArrayNode(0, result, root);
        return result;
    }

    private void fillArrayNode(int index, V[] array, Node<K, V> node) {
        if (node != null) {
            array[index] = node.value;
//            array[index] = (V) new Integer(node.getHeight());
            fillArrayNode(index * 2 + 1, array, node.left);
            fillArrayNode(index * 2 + 2, array, node.right);
        }
    }
    public Iterator<V> values() {
        return new TreeMapIterator<V>(root);
    }
}
