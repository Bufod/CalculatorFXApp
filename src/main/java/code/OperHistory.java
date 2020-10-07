package code;

import java.util.ArrayList;

public class OperHistory {

    private ArrayList<String> historyList = new ArrayList<>();

    private static OperHistory ourInstance = new OperHistory();

    public static OperHistory getInstance() {
        return ourInstance;
    }

    private OperHistory() {
    }

    public void addHistory(String expression){
        historyList.add(expression);
    }

    public void clearList(){
        historyList.clear();
    }

    public ArrayList<String> getHistoryList(){
        return historyList;
    }
}
