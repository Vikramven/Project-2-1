package dice_chess.Logic.Hybrid;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HashMapDB {
    HashMap<String, Double> map = new HashMap<String ,Double>();


    public static void main(String[] args) throws IOException {
        HashMapDB obj = new HashMapDB();
        obj.txtIntoHash("database.txt");
        obj.checkForSAPair("state1","moveking");
        System.out.println(obj.getprobability("state1","moveking"));
        obj.updateMap("state3","movepawn2",0.124);
        obj.updateTxt();
    }

    public void txtIntoHash(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        while ((line = br.readLine()) != null) {
            String[] arr = line.split(",");
            map.put(arr[0],Double.parseDouble(arr[1]));
        }

        //System.out.print(map);
    }

    public boolean checkForSAPair(String state, String action){
        boolean check = false;

        String pair = state.concat("-").concat(action);
        //System.out.println(pair);

        if(map.containsKey(pair))
            check = true;

        return check;
    }

    //with (s,a) get prob
    public double getprobability(String state, String action){
        double prob =0.0;
        if (checkForSAPair(state, action )){
            prob = map.get(state.concat("-").concat(action));
        }
        return prob;
    }

    public void updateMap(String state, String action, double p){
        map.put(state.concat("-").concat(action),p);
    }

    public void updateTxt() throws IOException {
        File file = new File("C:/demo/test.txt");
        file.getParentFile().mkdir();
        FileWriter myWriter = new FileWriter(file);

        for (Map.Entry<String, Double> entry : map.entrySet()) {
            myWriter.write(entry.getKey()+","+entry.getValue());
            myWriter.write("\n");
        }

        myWriter.close();
    }

    public void printHashTable(){
        System.out.println(map);
    }

    //in the db its action state prob reward.
}
