import java.util.*;

public class GBFS {
    private static int nodesVisited = 0;

    static class Node implements Comparable<Node> {
        String word;
        Node parent;
        int cost;

        public Node(String word, Node parent, int cost) {
            this.word = word;
            this.parent = parent;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.cost, other.cost);
        }
    }

    public static List<String> algo(String startWord, String endWord, Set<String> validWords) {
        nodesVisited = 0; // Reset the counter before running the algorithm
        PriorityQueue<Node> queue = new PriorityQueue<>();
        Set<String> visited = new HashSet<>();
        queue.add(new Node(startWord, null, 0));
        visited.add(startWord);

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            nodesVisited++; // Increment the counter for each visited node
            String word = currentNode.word;

            if (word.equals(endWord)) {
                List<String> ladder = new ArrayList<>();
                Node node = currentNode;
                while (node != null) {
                    ladder.add(0, node.word);
                    node = node.parent;
                }
                return ladder;
            }

            for (int i = 0; i < word.length(); i++) {
                char[] wordArray = word.toCharArray();
                for (char c = 'a'; c <= 'z'; c++) {
                    wordArray[i] = c;
                    String nextWord = new String(wordArray);

                    if (validWords.contains(nextWord) && visited.add(nextWord)) {
                        int cost = heuristic(nextWord, endWord); // Greedy approach, cost is heuristic
                        queue.add(new Node(nextWord, currentNode, cost));
                        
                    }
                }
            }
        }
        return Collections.emptyList();
    }

    public static int getNodesVisited() {
        return nodesVisited;
    }

    private static int heuristic(String word, String target) {
        int diff = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != target.charAt(i)) {
                diff++;
            }
        }
        return diff;
    }
}
