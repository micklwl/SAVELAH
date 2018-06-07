import java.util.ArrayList;
import java.util.HashMap;

class Recipe {
    private String listName;
    private HashMap<String, String> ingredients;
    private ArrayList<String> users;

    protected void addIngredient(String name) {
        ingredients.put(this.listName,name);
    }

    protected void addUser() {

    }
}
