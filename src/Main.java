import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // Baca dictionary dari file.txt
        Set<String> dictionary = readDictionaryFromFile("dictionary.txt");

        // Convert Set<String> to Map<String, Boolean>
        Map<String, Boolean> wordList = dictionary.stream()
            .collect(Collectors.toMap(Function.identity(), word -> Boolean.TRUE));

        // Input start word, end word, dan pilihan algoritma
        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan start word: ");
        String startWord = scanner.nextLine().trim().toLowerCase();
        System.out.print("Masukkan end word: ");
        String endWord = scanner.nextLine().trim().toLowerCase();
        System.out.print("Pilih algoritma:\n1. UCS (Uniform Cost Search)\n2. A* (A Star Search)\n3. GBFS\nMasukkan nomor algoritma : ");
        String algorithm = scanner.nextLine().trim().toUpperCase();
        scanner.close();

        // Jalankan algoritma sesuai pilihan
        List<String> ladder = new ArrayList<>();
        int nodesVisited = 0;
        long startTime = System.currentTimeMillis();
        if (algorithm.equals("1")) {
            ladder = UCS.algo(startWord, endWord, wordList);
            nodesVisited = UCS.getNodesVisited();
        } else if (algorithm.equals("2")) {
            ladder = AStar.algo(startWord, endWord, wordList);
            nodesVisited = AStar.getNodesVisited();
        } else if(algorithm.equals("3")) {
            Set<String> wordSet = wordList.keySet(); // Convert Map<String, Boolean> to Set<String>
            ladder = GBFS.algo(startWord, endWord, wordSet);
            nodesVisited = GBFS.getNodesVisited();
        } else {
            System.out.println("Pilihan algoritma tidak valid.");
            return;
        }
        long endTime = System.currentTimeMillis();

        // Cetak hasil
        if (!ladder.isEmpty()) {
            System.out.println("Path:");
            for (String word : ladder) {
                System.out.println(word);
            }
        } else {
            System.out.println("Tidak ditemukan path yang menghubungkan start word dan end word.");
        }
        System.out.println("Banyaknya node yang dikunjungi: " + nodesVisited);
        System.out.println("Waktu eksekusi program: " + (endTime - startTime) + " milidetik");
    }

    private static Set<String> readDictionaryFromFile(String fileName) {
        Set<String> dictionary = new HashSet<>();
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine().trim().toLowerCase();
                dictionary.add(word);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        }
        return dictionary;
    }
}