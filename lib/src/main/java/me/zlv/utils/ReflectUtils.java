package me.zlv.utils;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import dalvik.system.DexFile;

/**
 * Android 平台下的反射工具类
 * Created by jeremyhe on 2017/5/6.
 */

public class ReflectUtils {
    public static final String TAG = ReflectUtils.class.getSimpleName();

    private ReflectUtils() {
        // do nothing
    }

    /**
     * 获取所有类
     */
    public static List<Class> listAllClass(Context context) {
        return listClass(context, "");
    }

    /**
     * 获取指定包名下的所有类
     * 包名注意混淆情况
     */
    public static List<Class> listClass(Context context, String pkgName) {
        List<Class> classList = new ArrayList<>();

        Context appContext = context.getApplicationContext();
        ClassLoader classLoader = appContext.getClassLoader();

        String entry = "";
        try {
            Field pathListField = findField(classLoader, "pathList");
            Object dexPathList = pathListField.get(classLoader);

            Field dexElementsField = findField(dexPathList, "dexElements");
            Object[] dexElements = (Object[]) dexElementsField.get(dexPathList);
            for (Object dexElement : dexElements) {
                Field dexFileField = findField(dexElement, "dexFile");
                DexFile dexFile = (DexFile) dexFileField.get(dexElement);

                Enumeration<String> entries = dexFile.entries();
                while (entries.hasMoreElements()) {
                    entry = entries.nextElement();
                    // 只考虑放在impl中的 presenter 实现类
                    if (entry.startsWith(pkgName)) {
                        Class<?> clazz = dexFile.loadClass(entry, classLoader);
                        classList.add(clazz);
                    }
                }
            }
        } catch (Exception | Error e) {
            Log.e(TAG, "class = " + entry + " Exception: " + e.getMessage());
        }

        return classList;
    }

    /**
     * 获取指定字段
     */
    public static Field findField(Object instance, String name) throws NoSuchFieldException {
        Class clazz = instance.getClass();

        while(clazz != null) {
            try {
                Field e = clazz.getDeclaredField(name);
                if(!e.isAccessible()) {
                    e.setAccessible(true);
                }

                return e;
            } catch (NoSuchFieldException var4) {
                clazz = clazz.getSuperclass();
            }
        }

        throw new NoSuchFieldException("Field " + name + " not found in " + instance.getClass());
    }
}
