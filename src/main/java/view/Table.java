package view;

import java.util.List;

public class Table {
    private List<String> header;
    private List<String> rows;

    public Table(List<String> header, List<String> rows) {
        this.header = header;
        this.rows = rows;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (String element : header) {
            res.append(element).append("\t");
        }
        res.append("\n");
        for (String row : rows) {
            res.append(row).append("\n");
        }
        return res.toString();
    }
}
