import java.util.ArrayList;
import java.util.Random;

public class GenerateArrays {
    ArrayList<Integer> arrayA = new ArrayList<>();
    ArrayList<Integer> arrayB = new ArrayList<>();
    int M = 4000;
    int N = 5000;

    public ArrayList<Integer> getArrayA(){
        Random random = new Random();
        for(int i = 0; i< M; i++){
            int randomInteger = random.nextInt(200);
            arrayA.add(randomInteger);
        }
        return arrayA;
    }

    public ArrayList<Integer> getArrayB(int D){
        arrayB.addAll(arrayA);
        Random random = new Random();
        int insertions = N - M + D;

        for(int i = 0; i < D; i++){
            int randomIndex = random.nextInt(arrayB.size()-1);
            arrayB.remove(randomIndex);
        }

        for(int i = 0; i < insertions; i++){
            int randomInteger = random.nextInt(insertions);
            int randomIndex = random.nextInt(200);
            arrayB.add(randomIndex, randomInteger);
        }
        return arrayB;
    }
}
