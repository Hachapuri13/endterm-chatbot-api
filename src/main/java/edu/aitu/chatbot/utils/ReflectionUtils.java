package utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ReflectionUtils {

    public static void inspectClass(Object obj) {
        if (obj == null) {
            System.out.println("Object is null.");
            return;
        }

        Class<?> clazz = obj.getClass();
        System.out.println("\n=== 🕵️ REFLECTION INSPECTOR ===");
        System.out.println("Class Name: " + clazz.getSimpleName());
        System.out.println("Package: " + clazz.getPackage().getName());

        System.out.println("\n--- FIELDS (Private included) ---");
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                System.out.println(" • " + field.getName() + " [" + field.getType().getSimpleName() + "] = " + field.get(obj));
            } catch (IllegalAccessException e) {
                System.out.println(" • " + field.getName() + " (Access Denied)");
            }
        }

        System.out.println("\n--- METHODS ---");
        Method[] methods = clazz.getDeclaredMethods();
        Arrays.stream(methods)
                .map(Method::getName)
                .filter(name -> !name.startsWith("lambda"))
                .limit(10)
                .forEach(name -> System.out.print(name + "()  "));
        System.out.println("\n================================\n");
    }
}