package graphics;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Product;
import model.Property;

import java.util.HashSet;
import java.util.Set;

public class ComparePage {
    public VBox specSection;
    Product p1, p2;
    public HBox productBox;
    ObservableList<TripleProperty> items = FXCollections.observableArrayList();

    public void setProducts(Product p1, Product p2) {
        this.p1 = p1;
        this.p2 = p2;
        productBox.getChildren().addAll(new ProductPane(p1), new ProductPane(p2));

        showSpecs();
    }


    private void showSpecs() {
        TableView<TripleProperty> tableView = new TableView<>();

        TableColumn<TripleProperty, String> nameColumn = new TableColumn<>("Property");
        TableColumn<TripleProperty, String> valueColumn1 = new TableColumn<>("Value1");
        TableColumn<TripleProperty, String> valueColumn2 = new TableColumn<>("Value2");

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        valueColumn1.setCellValueFactory(new PropertyValueFactory<>("value1"));
        valueColumn2.setCellValueFactory(new PropertyValueFactory<>("value2"));


        Set<String> nameSet = new HashSet<>();

        for (Property property : p1.getAllSpecialProperties()) {
            items.add(new TripleProperty(
                    property.getName(),
                    property.getValue(),
                    p2.getPropertyValue(property.getName())));
            nameSet.add(property.getName());
        }

        for (Property property : p2.getAllSpecialProperties()) {
            if (duplicate(nameSet, property.getName()))
                continue;
            items.add(new TripleProperty(
                    property.getName(),
                    p1.getPropertyValue(property.getName()),
                    property.getValue()));
            nameSet.add(property.getName());
        }
        tableView.setItems(items);

        tableView.setMaxWidth(175 * 3);
        nameColumn.setPrefWidth(170);
        valueColumn1.setPrefWidth(170);
        valueColumn2.setPrefWidth(170);

        tableView.setFixedCellSize(50);
        tableView.setMaxHeight(tableView.getFixedCellSize() * (tableView.getItems().size()));
        tableView.getColumns().addAll(nameColumn, valueColumn1, valueColumn2);
        tableView.setPlaceholder(new Label("No specification available"));

        specSection.getChildren().add(tableView);
    }

    private boolean duplicate(Set<String> stringSet, String anotherString) {
        for (String string : stringSet) {
            if (string.equals(anotherString))
                return true;
        }
        return false;
    }

    public static class TripleProperty {
        private String name;
        private String value1;
        private String value2;

        public TripleProperty(String name, String value1, String value2) {
            this.setName(name);
            this.setValue1(value1);
            this.setValue2(value2);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue1() {
            return value1;
        }

        public void setValue1(String value1) {
            this.value1 = value1;
        }

        public String getValue2() {
            return value2;
        }

        public void setValue2(String value2) {
            this.value2 = value2;
        }
    }
}
