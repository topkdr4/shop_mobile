package ru.vetoshkin.shop_mobile.cache;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
public class Cache {
    private static final Map<Class<?>, Holder> globalCache = new ConcurrentHashMap<>();




    public static Map<String, Object> getCache(Class clazz) {
        Holder holder = globalCache.get(clazz);
        return holder == null ? null : holder.cache;
    }



    public static void insert(Class clazz, String key, Object data) {
        if (data != null && clazz != data.getClass())
            throw new IllegalArgumentException("Class data not equals");

        // api 24+. Позор
        // globalCache.computeIfAbsent(clazz, tClass -> new ConcurrentHashMap<>());

        Holder holder = globalCache.get(clazz);
        if (holder == null) {
            holder = new Holder();
            globalCache.put(clazz, holder);
        }

        holder.put(key, data);
    }




    private static class Holder {
        private final Map<String, Object> cache = new ConcurrentHashMap<>();


        private void put(String key, Object data) {
            cache.put(key, data);
        }


        private Object get(String key) {
            return cache.get(key);
        }
    }



    private Cache() {

    }


    /*public static void main(String[] args) {
        Product product = new Product();
        Category category = new Category("id", "title");

        Cache.insert(String.class,   "key", "Ebota String");
        Cache.insert(Integer.class,  "key", 65535);
        Cache.insert(Product.class,  "key", product);
        Cache.insert(Category.class, "key", category);


        Product  productFromCache  = (Product)  getCache(Product.class).get("key");
        Category categoryFromCache = (Category) getCache(Category.class).get("key");
        String stringFromCache = String.valueOf(getCache(String.class).get("key"));
        Integer integerFromCache = (Integer) getCache(Integer.class).get("key");
        Integer nullFromCache    = (Integer) getCache(Integer.class).get("key123");


        if (product == productFromCache) {
            System.out.println("product equals");
        } else {
            System.out.println("product not equals");
        }


        if (category == categoryFromCache) {
            System.out.println("category equals");
        } else {
            System.out.println("category not equals");
        }


        if ("Ebota String".equals(stringFromCache)) {
            System.out.println("string equals");
        } else {
            System.out.println("string not equals");
        }


        if (integerFromCache != null && integerFromCache == 65535) {
            System.out.println("integer equals");
        } else {
            System.out.println("integer not equals");
        }


        if (nullFromCache == null) {
            System.out.println("correct null");
        } else {
            System.out.println("incorrect null");
        }
    }*/

}
