import java.util.*;

class NodeUCS {
    String word;
    List<String> path;
    int cost;

    public NodeUCS(String word, int cost) {
        this.word = word;
        this.cost = cost;
        this.path = new ArrayList<>();
    }

    public NodeUCS(String word, int cost, List<String> path) {
        this.word = word;
        this.cost = cost;
        this.path = new ArrayList<>(path);
    }
}

public class UCS {
    private static int nodesVisited = 0;
    static public List<String> algo(String beginWord, String endWord, Map<String, Boolean> wordList) {
        NodeUCS beginNode = new NodeUCS(beginWord, 0);
        beginNode.path.add(beginWord);

        PriorityQueue<NodeUCS> todo = new PriorityQueue<>((a, b) -> Integer.compare(a.cost, b.cost));
        todo.add(beginNode);

        while (!todo.isEmpty()) {
            NodeUCS current = todo.poll();
            nodesVisited++;
            if (current.word.equals(endWord)) {
                return current.path;
            }
            wordList.remove(current.word);
            char[] charArray = current.word.toCharArray();
            for (int j = 0; j < charArray.length; j++) {
                char originalChar = charArray[j];
                for (char c = 'a'; c <= 'z'; c++) {
                    charArray[j] = c;
                    String newWord = new String(charArray);
                    if (wordList.containsKey(newWord)) {
                        List<String> tempPath = new ArrayList<>(current.path);
                        tempPath.add(newWord);
                        int newCost = current.cost + 1; // Biaya untuk UCS adalah 1
                        NodeUCS newNode = new NodeUCS(newWord, newCost, tempPath);
                        todo.add(newNode);
                        wordList.remove(newWord);
                    }
                }
                charArray[j] = originalChar;
            }
        }
        return new ArrayList<>();
    }
    static public int getNodesVisited() {
        return nodesVisited;
    }
}
