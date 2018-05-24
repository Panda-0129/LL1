import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

class AnalyzeStack {

    private String inputStr;
    private Stack<String> stProduction = new Stack<>();
    private Stack<String> stInputStr = new Stack<>();
    private boolean stop = false;
    private String currentPro;

//    获取输入串，初始化栈
    private void init() {
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNext()) {
            inputStr = scanner.next();
            System.out.println(inputStr);
        }

        scanner.close();
        int index = inputStr.length();
        while ( index > 0 ) {
            stInputStr.push(String.valueOf(inputStr.charAt(index - 1)));
            index--;
        }
        System.out.println("Stack\t\tInput\t\tProduction");
        stProduction.push("#");
        stProduction.push(Config.productions.get(0).head);
        outputStack();
    }

    void stack (AnalysisTable analysisTable) {
        init();
//        判断当前分析工作是否完成
        while ( !stProduction.peek().equals("#") ) {
            if (stProduction.peek().equals(stInputStr.peek())) {
                stProduction.pop();
                stInputStr.pop();
                currentPro = "Pop, Next";
            } else {
                nextProduction(analysisTable);
            }
            if (stop) {
                System.out.println("error");
                break;
            }
            outputStack();
        }

//        当分析栈栈顶与输入串栈顶均为#时，分析成功
        if (stProduction.peek().equals(stInputStr.peek()))
            System.out.println("Success!");
    }

    private void nextProduction(AnalysisTable analysisTable) {
        analysisTable.generate();
//        ch为当前输入串栈栈顶
        String ch = stInputStr.peek();
//        head为当前分析栈栈顶
        String head = stProduction.peek();
//        获取M[head, ch]在表中对应的位置
        ArrayList<Integer> pos = analysisTable.getPos(head,ch);
//        将当前分析栈栈顶的非终结符用合适产生式替换
        if (pos.size() == 2) {
            stProduction.pop();
            String production = analysisTable.table[pos.get(0)][pos.get(1)];
            currentPro = head + "->" + production;
            if (!production.equals("#")) {
                int index = production.length();
                while ( index > 0 ) {
                    stProduction.push(String.valueOf(production.charAt(index - 1)));
                    index--;
                }
            }
        } else if (FirstSet.getFirst(head).contains(Config.nullstr)) {
            stProduction.pop();
            currentPro = head + "->~";
        } else {
            stop = true;
        }

    }

    private void outputStack() {
        for (String aStProduction : stProduction) {
            System.out.print(aStProduction);
        }

        if (stProduction.size() < 4)
            System.out.print("\t");

        System.out.print("\t\t");

        for (int i = stInputStr.size(); i > 0; i-- ) {
            System.out.print(stInputStr.get(i - 1));
        }

        if (stInputStr.size() < 4)
            System.out.print("\t");

        System.out.print("\t\t" + currentPro);

        System.out.println();
    }

}
