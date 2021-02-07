import java.io.*;
import java.util.*;


public class AprioriAlgorithm {

    // --- String comparator
    public StringToIntSort stringToIntSort = new StringToIntSort();
    public LexicoArrayListSort arrayListSort = new LexicoArrayListSort();

    public static void main(String[] args) throws FileNotFoundException {

        // --- User help display
        if (args.length < 2) {
            System.out.println("Usage: java Main [FILE_PATH] [MIN_SUPPORTED]");
            System.out.println("[FILE_PATH]: the file containing the datasets");
            System.out.println("[MIN_SUPPORTED]: the minimum supported (>= 0)");
            System.out.println("\nexample: java AprioriAlgorithm ../DataSet/toy_example 2");
            System.exit(1);
        }

        AprioriAlgorithm AA = new AprioriAlgorithm();


        // --- File reading
        String filepath = args[0];
        System.out.println("[~]Loading file...");

        InputStream fileInputStream = new FileInputStream(filepath);
        BufferedReader file = new BufferedReader(new InputStreamReader(fileInputStream));
        System.out.println("[+]" + filepath + " has been loaded successfully");

        // --- Get all transactions
        Scanner sc = new Scanner(file);
        ArrayList<ArrayList<String>> transactions = new ArrayList<>();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] str = line.split(" ");
            ArrayList<String> newTransaction = new ArrayList<>(Arrays.asList(str));
            transactions.add(newTransaction);
        }


        // --- check minimum support
        sc = new Scanner(System.in);
        int minimumSupport = Integer.parseInt(args[1]);
        if (minimumSupport < 0) {
            do {
                System.out.print("\nEnter minimum support ? (>= 0) ");
                minimumSupport = Integer.parseInt(sc.nextLine());
            } while (minimumSupport < 0);
        }

        System.out.println("[+]The program has started successfully");


        // --- Get item set
        ArrayList<String> defaultItemSet = AA.getItemSet(transactions);
        Collections.sort(defaultItemSet);
        //AA.displayItemSet(defaultItemSet); // DEBUG

        // --- Get first frequent item
        //We could change HashMap as ArrayList<String> and only returning the keys
        //But it might be useful for debugging (or use HashMap everywhere to see each occurrences)
        HashMap<String, Integer> frequentItems = AA.getFirstFrequentItemSet(transactions, defaultItemSet, minimumSupport);
        AA.displayFrequentItemSet(frequentItems); // DEBUG

        // --- Get the item set satisfying the minimum support
        ArrayList<String> supportedItemSet = new ArrayList<>(frequentItems.keySet());


        System.out.println("\n------------------------------");
        System.out.println("[~] Apriori");
        ArrayList<ArrayList<String>> apriori = AA.apriori(transactions, supportedItemSet, minimumSupport);

        System.out.println("[+]Success, a result has been found:");
        AA.displayApriori(apriori);


        System.out.println("\n------------------------------");
        System.out.println("[~] AprioriChild");
        ArrayList<ArrayList<String>> aprioriChild = AA.aprioriChild(transactions, defaultItemSet, supportedItemSet, minimumSupport);

        System.out.println("[+]Success, a result has been found:");
        AA.displayApriori(aprioriChild);
    }

    /**
     * Get all item find in the read file
     * @param transactions Container of all lines get from the read file
     * @return Return the item set
     */
    public ArrayList<String> getItemSet(ArrayList<ArrayList<String>> transactions) {
        ArrayList<String> itemSet = new ArrayList<>();

        for (ArrayList<String> transaction : transactions) {
            for (String items : transaction) {

                String[] values = items.split(" ");
                for (String item : values) {
                    if (!itemSet.contains(item)) {
                        itemSet.add(item);
                    }
                }
            }
        }

        return itemSet;
    }


    /**
     * Get the first frequent item set satisfying the minimum supported give
     * @param transactions Container of all lines get from the read file
     * @param itemSet All item set found
     * @param minimumSupported The minimum supported give by the user
     * @return Return the first frequent item and their occurrences
     */
    public HashMap<String, Integer> getFirstFrequentItemSet(ArrayList<ArrayList<String>> transactions, ArrayList<String> itemSet, int minimumSupported) {
        HashMap<String, Integer> frequentItems = new HashMap<>();

        for (String item : itemSet) {
            int countItemOccurrence = 0;

            for (ArrayList<String> transaction : transactions) {
                if (transaction.contains(item)) {
                    countItemOccurrence++;
                }
            }

            if (countItemOccurrence >= minimumSupported) {
                frequentItems.put(item, countItemOccurrence);
            }
        }

        return frequentItems;
    }


    /**
     * Get the frequent candidates
     * @param transactions Container of all lines get from the read file
     * @param candidates The list of new candidates to be an item set
     * @param minimumSupported The minimum supported give by the user
     * @return Return the frequent candidates found
     */
    public ArrayList<ArrayList<String>> getFrequentCandidates(ArrayList<ArrayList<String>> transactions, ArrayList<ArrayList<String>> candidates, int minimumSupported) {
        ArrayList<ArrayList<String>> frequentCandidates = new ArrayList<>();

        for (ArrayList<String> candidate : candidates) {
            int countItemOccurrence = 0;

            for (ArrayList<String> transaction : transactions) {
                if (transaction.containsAll(candidate)) {
                    countItemOccurrence++;
                }
            }

            if (countItemOccurrence >= minimumSupported) {
                frequentCandidates.add(candidate);
            }
        }

        return frequentCandidates;
    }


    /**
     * Generate new candidates
     * @param itemSet All item set found
     * @param candidateLength Max length for the generate candidate
     * @return Return all new candidates generated
     */
    public ArrayList<ArrayList<String>> generateCandidates(ArrayList<ArrayList<String>> itemSet, int candidateLength) {
        ArrayList<ArrayList<String>> result = new ArrayList<>();

        for (int i = 0; i < itemSet.size(); i++) {
            for (int j = i + 1; j < itemSet.size(); j++) {
                ArrayList<String> newCandidate = new ArrayList<>(itemSet.get(i));
                for (String item : itemSet.get(j)) {
                    if (!newCandidate.contains(item)) {
                        newCandidate.add(item);
                    }
                }

                if (newCandidate.size() == candidateLength) {
                    newCandidate.sort(stringToIntSort);
                    if (!result.contains(newCandidate)) {
                        result.add(newCandidate);
                    }
                }
            }
        }

        return result;
    }


    /**
     * Generate all candidates by the child method in lexicographic order
     * @param defaultItemSet The first items set found
     * @param itemSet All item set found
     * @param candidateLength Max length for the generate candidate
     * @return Return all new candidates generated
     */
    public ArrayList<ArrayList<String>> generateCandidatesChild(ArrayList<String> defaultItemSet, ArrayList<ArrayList<String>> itemSet, int candidateLength) {

        ArrayList<ArrayList<String>> result = new ArrayList<>();


        for (String dItem : defaultItemSet) {
            for (ArrayList<String> item : itemSet) {
                if (!item.contains(dItem)) {
                    ArrayList<String> newCandidate = new ArrayList<>(item);
                    newCandidate.add(dItem);
                    newCandidate.sort(stringToIntSort);

                    if (!result.contains(newCandidate)) {
                        result.add(newCandidate);
                    }
                }
            }
        }

        return result;
    }


    /**
     * Apriori method
     * @param transactions Container of all lines get from the read file
     * @param itemSet All item set found
     * @param minimumSupported The minimum supported give by the user
     * @return Return result of apriori algorithm
     */
    public ArrayList<ArrayList<String>> apriori(ArrayList<ArrayList<String>> transactions, ArrayList<String> itemSet, int minimumSupported) {

        ArrayList<ArrayList<String>> itemSetCpy = new ArrayList<>();
        for (String s : itemSet) {
            ArrayList<String> newItem = new ArrayList<>();
            newItem.add(s);
            itemSetCpy.add(newItem);
        }

        ArrayList<ArrayList<String>> candidates;
        ArrayList<ArrayList<String>> result = itemSetCpy;

        // --- Start at 2 because we already have the first frequent item set
        int k = 2;

        while (itemSetCpy.size() != 0) {
            candidates = generateCandidates(itemSetCpy, k);
            k++;
            itemSetCpy = getFrequentCandidates(transactions, candidates, minimumSupported);

            // Display frequent candidates
            // System.out.println(itemSetCpy);

            result.addAll(itemSetCpy);
        }

        return result;
    }


    /**
     * Apriori child method
     * @param transactions Container of all lines get from the read file
     * @param defaultItemSet The first items set found
     * @param itemSet All item set found
     * @param minimumSupported The minimum supported give by the user
     * @return Return result of aprioriChild algorithm
     */
    public ArrayList<ArrayList<String>> aprioriChild(ArrayList<ArrayList<String>> transactions, ArrayList<String> defaultItemSet, ArrayList<String> itemSet, int minimumSupported) {

        ArrayList<ArrayList<String>> itemSetCpy = new ArrayList<>();
        for (String s : itemSet) {
            ArrayList<String> newItem = new ArrayList<>();
            newItem.add(s);
            itemSetCpy.add(newItem);
        }

        ArrayList<ArrayList<String>> candidates;
        ArrayList<ArrayList<String>> result = itemSetCpy;

        // --- Start at 2 because we already have the first frequent item set
        int k = 2;

        while (itemSetCpy.size() != 0) {
            candidates = generateCandidatesChild(defaultItemSet ,itemSetCpy, k);
            k++;
            itemSetCpy = getFrequentCandidates(transactions, candidates, minimumSupported);

            // Display frequent candidates
            // System.out.println(itemSetCpy);

            result.addAll(itemSetCpy);
        }

        result.sort(arrayListSort);
        return result;
    }
    

    /**
     * Display all item set
     * @param itemSet All item set found
     */
    public void displayItemSet(ArrayList<String> itemSet) {
        System.out.println("\n// --- Item set");
        for (String item : itemSet) {
            System.out.println(item);
        }
    }


    /**
     * Display the frequent item with their occurence
     * @param frequentItemSet All frequent item set and their occurrences
     */
    public void displayFrequentItemSet(HashMap<String, Integer> frequentItemSet) {
        System.out.println("\n// --- Frequent Item");
        for (String key : frequentItemSet.keySet()) {
            System.out.println("key: " + key + " value: " + frequentItemSet.get(key));
        }
    }


    /**
     * Display for candidates
     * @param candidates New candidates
     */
    public void displayCandidates(ArrayList<ArrayList<String>> candidates) {
        for (ArrayList<String> candidate : candidates) {
            System.out.println(candidate);
        }
    }


    /**
     * Display for all Apriori method
     * @param apriori Apriori method
     */
    public void displayApriori(ArrayList<ArrayList<String>> apriori) {
        System.out.print("[");
        int count = 0;
        for (ArrayList<String> item : apriori) {
            StringBuilder sb = new StringBuilder();
            for (String s : item) {
                sb.append(s);
            }
            count++;
            if (count == apriori.size()){
                System.out.print(sb);
            } else if (count%15 == 0){
                System.out.println(sb+",");
            } else {
                System.out.print(sb+",");
            }

        }
        System.out.println("]");
    }
}


/**
 * Transform string value into integer and compare them
 */
class StringToIntSort implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        Integer a = Integer.parseInt(o1);
        Integer b = Integer.parseInt(o2);
        return a.compareTo(b);
    }
}

/**
 * Transform string value into integer and compare them
 */
class LexicoArrayListSort implements Comparator<ArrayList<String>> {

    @Override
    public int compare(ArrayList<String> o1, ArrayList<String> o2) {
        String a = String.join("", o1);
        String b = String.join("", o2);
        return a.compareTo(b);
    }
}