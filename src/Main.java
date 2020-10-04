import javafx.util.Pair;
import java.util.PriorityQueue;
import java.util.Queue;

public class Main {

    public static void main(String[] args) {

        System.out.println(longestDiverseString(1,1,7));

    }

    public static String longestDiverseString(int a, int b, int c) {
        StringBuilder sb = new StringBuilder();

        //max pq which sorts characters based on largest occurrence
        Queue<Pair<Character, Integer>> pq = new PriorityQueue<>((c1, c2) -> (c2.getValue() - c1.getValue()));

        if (a>0)
            pq.add(new Pair<Character, Integer>('a', a));
        if (b>0)
            pq.add(new Pair<Character, Integer>('b', b));
        if (c>0)
            pq.add(new Pair<Character, Integer>('c', c));

        //one while loop and if-else solves the problem!
        while (!pq.isEmpty()) {

            //poll char with max occurrence, and check if last added character to sb [if any]
            //is the same as polled char wuith max occurrence, in which
            //case we want to add the second largest occurrence char, but only ONCE.
            Pair<Character, Integer> first = pq.poll();
            if (sb.length()!=0 && sb.charAt(sb.length()-1) == first.getKey()) {

                //interesting edge case: of there is no second char to add after polling
                //first, just return without adding anything, we already have
                //longest happy string
                if (pq.isEmpty())
                    return sb.toString();

                Pair<Character, Integer> second = pq.poll();
                sb.append(second.getKey());
                if (second.getValue()-1>0) {
                    //just deduce 1 from second count and put back if remaining count is greater than 0
                    pq.add(new Pair<Character, Integer>(second.getKey(), second.getValue()-1));
                }
                //put back first that we polled initially, but we didn't use.
                pq.add(first);
            //string empty or last char was not the same as the char with max occurrence
            } else {

                int limit = Math.min(2, first.getValue());
                int counter = 0;

                // e.g. if we have count 7 for this char [char with max occurrence],
                // then we only need 2.
                // if we have 1, we gonna use only 1
                while (counter++<limit) {
                    sb.append(first.getKey());
                }

                //put first back after deducting limit from it's occurrence
                if (first.getValue()-limit>0)
                    pq.add(new Pair<Character, Integer>(first.getKey(), first.getValue()-limit));
            }
        }
        return sb.toString();
    }

}
