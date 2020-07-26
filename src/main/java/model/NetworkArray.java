package model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import controller.Database;

import java.util.ArrayList;
import java.util.Iterator;

public class NetworkArray<T extends BaseModel> extends ArrayList<T> {
    private ArrayList<String> elementData = new ArrayList<>();
    private Class tClass;

    public NetworkArray(Class objectClass) {
        tClass = objectClass;
    }

    @Override
    public int size() {
        return elementData.size();
    }

    @Override
    public boolean contains(Object o) {
        BaseModel b;
        try {
            b = (BaseModel) o;
        } catch (Exception ignore) {
            return false;
        }
        return elementData.contains(b.getId());
    }

    @Override
    public int indexOf(Object o) {
        BaseModel b;
        try {
            b = (BaseModel) o;
        } catch (Exception ignore) {
            return -1;
        }
        return elementData.indexOf(b.getId());
    }

    @Override
    public boolean add(T t) {
        return elementData.add(t.getId());
    }

    @Override
    public boolean remove(Object o) {
        BaseModel b;
        try {
            b = (BaseModel) o;
        } catch (Exception ignore) {
            return false;
        }
        return elementData.remove(b.getId());
    }

    @Override
    public Iterator<T> iterator() {
        try {
            load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Iterator<T>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < elementData.size();
            }

            @Override
            public T next() {
                return get(index++);
            }

            @Override
            public void remove() {

            }
        };
    }

    @Override
    public T get(int index) {
        try {
            return (T) Database.getObjectFromNetwork(elementData.get(index), tClass);
        } catch (Exception e) {
            throw new NullPointerException("coudn't get the object");
        }
    }

    public void load() throws Exception {
        JsonObject jsonObject = Database.sendGETall(tClass);
        elementData.clear();
        elementData.addAll(new Gson().fromJson(jsonObject.get("list"), elementData.getClass()));
    }
}
