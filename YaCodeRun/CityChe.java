package YaCodeRun;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class CityChe {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        reader.readLine();
        int[] metrs = Arrays.stream(reader.readLine().trim().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();

        int bestMetres = 4;
        int bestPos = 0;

        for(int i = 0; i< metrs.length;i++) {
            if((metrs[i+1]-metrs[i])>bestMetres) {
                bestPos++;
            }
        }

        System.out.println(bestPos);

        reader.close();
        writer.close();
    }
}
