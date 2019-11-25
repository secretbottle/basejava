package ru.javawebinar;

import ru.javawebinar.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume r = new Resume();
        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(r));
        field.set(r, "new_uuid");
        System.out.println(r);

        Method methodToStr = r.getClass().getMethod("toString");
        System.out.println("Method name: " + methodToStr);
        System.out.println("Method value: " + methodToStr.getDefaultValue());

        methodToStr.invoke(r);
        System.out.println(methodToStr.invoke(r));
    }
}
