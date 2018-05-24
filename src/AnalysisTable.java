import java.util.ArrayList;

class AnalysisTable {

    String[][] table = new String[Config.VN.size() + 1][Config.VT.size() + 1];

    void generate() {

//        将分析表初始化为空
        for (int i = 0; i < Config.VN.size() + 1; i++) {
            for (int j = 0; j < Config.VT.size() + 1; j++) {
                table[i][j] = "";
            }
        }

//        初始化分析表的第一行为终结符，第一列为非终结符
        for (int i = 1; i < Config.VT.size() + 1; i++) {
            table[0][i] = Config.VT.get(i - 1);
        }
        for (int i = 1; i < Config.VN.size() + 1; i++) {
            table[i][0] = Config.VN.get(i - 1);
        }

//        对每一个产生式，分别进行分析
        for (Production production : Config.productions) {
            for (int i = 0; i < production.body.length; i++) {
                String head = production.head;
                String pro = production.body[i];
                Set tmpFirst = FirstSet.getFirst(head);
                Set tmpFollow = FollowSet.getFollow(head);

//                若该产生式右部第一项为终结符且该产生式不为空串，则直接将对应表格置为该产生式
                if (Config.VT.contains(String.valueOf(production.body[i].charAt(0))) && !production.body[i].equals(Config.nullstr)) {
                    String str = String.valueOf(production.body[i].charAt(0));
                    ArrayList<Integer> tmp = getPos(head, str);
                    table[tmp.get(0)][tmp.get(1)] = pro;
                    continue;
                }
//                若该产生式为空串，直接将表格对应项置为该产生式
                if (!production.body[i].equals("~")) {
                    for (String str : tmpFirst.body) {
                        ArrayList<Integer> tmp = getPos(head, str);
                        table[tmp.get(0)][tmp.get(1)] = pro;
                    }
                }

//                当前非终结符的First集含空串
                if (tmpFirst.contains(Config.nullstr)) {
//                    对于任一个b∈FOLLOW(A) b∈Vt或#，将A→ε规则填入M[A, b]
                    pro = "#";
                    for (String str : tmpFollow.body) {
                        if (str.equals("#"))
                            str = "~";
                        ArrayList<Integer> tmp = getPos(head, str);
                        table[tmp.get(0)][tmp.get(1)] = pro;
                    }
                }

            }
        }
    }

//    获取M[head, VT]的位置
    ArrayList<Integer> getPos (String head, String VT) {
        ArrayList<Integer> pos = new ArrayList<>();

        for (int i = 0; i < Config.VN.size() + 1; i++) {
            if(table[i][0].equals(head)) {
                pos.add(i);
                break;
            }
        }

        for (int i = 0; i < Config.VT.size() + 1; i++) {
            if (table[0][i].equals(VT)) {
                pos.add(i);
                break;
            }
        }

        return pos;
    }
}
