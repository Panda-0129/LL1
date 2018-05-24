import java.io.*;
import java.util.*;

public class LL1 {

    private static String VT[] = new String[64];
    private static String VN[] = new String[64];

    public static void main(String args[]) throws IOException {

        readFromFile();
        addVTandVN();
        AnalysisTable analysisTable = new AnalysisTable();
        AnalyzeStack analyzeStack = new AnalyzeStack();
        System.out.println(Config.VN);
        System.out.println(Config.VT);
        Config.productions.forEach(Production::outputPro);

        for (String aVN : VN) {
            System.out.print("First{" + aVN + "}:");
            FirstSet.getFirst(aVN).outputSet();

        }
        for (String aVN: VN) {
            System.out.print("Follow{" + aVN + "}:");
            FollowSet.getFollow(aVN).outputSet();
        }
        System.out.println("analyze table:");
        analysisTable.generate();
        for (int i = 0; i < Config.VN.size() + 1; i++) {
            for (int j = 0; j < Config.VT.size() + 1; j++) {
                if (analysisTable.table[i][j].equals("")) {
                    System.out.print("\t");
                } else {
                    System.out.print(analysisTable.table[i][j] + "\t");
                }
            }
            System.out.println();
        }
        analyzeStack.stack(analysisTable);
        System.out.println();

    }

    private static void readFromFile() throws IOException {
        int i = 0;
        String line;
        String pathname = "test2.txt";
        File fp = new File(pathname);
        InputStreamReader reader = new InputStreamReader(new FileInputStream(fp));
        BufferedReader br = new BufferedReader(reader);
        while ((line = br.readLine()) != null) {
            if(i == 0) {
                VN = line.split(",");
            } else if (i == 1) {
                VT = line.split(",");
            } else {
                Config.productions.add(new Production(line.replace(" ", "")));
            }
            i++;
        }
    }

    private static void addVTandVN() {
        Collections.addAll(Config.VN, VN);
        Collections.addAll(Config.VT, VT);
    }
}
