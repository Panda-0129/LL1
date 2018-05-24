import java.util.ArrayList;

class Production {
    String head;
    String body[];
//    private String [] production;

    Production(String str) {
        String tmp[] = str.split("->");
        head = tmp[0];
        body = tmp[1].split("\\|");
//        production = new String[body.length];
    }

    ArrayList<String> getBody(String str) {
        ArrayList<String> tmp = new ArrayList<>();
        for (String aBody : body) {
            if (aBody.contains(str)) {
                tmp.add(aBody);
            }
        }
        return tmp;
    }

//    String [] getProduction() {
//        for (int i = 0; i < body.length; i++) {
//            production[i] = head +"->" + body[i];
//        }
//
//        return production;
//    }

    boolean contains(String str) {
        for (String aBody : body) {
            if (aBody.contains(str)) {
                return true;
            }
        }
        return false;
    }

    void outputPro() {
        int i = 0;
        System.out.print(head + "->");
        for(String str : body) {
            if (i == 0) {
                System.out.print(str);
            } else {
                System.out.print("|" + str);
            }
            i++;
        }
        System.out.println();
    }
}
