package me.murilin;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class Module {

    private Method[] methods;
    private MSEssentials main;
    private URLClassLoader classLoader;
    private Class<?> moduleClass;

    public Module(MSEssentials main, String path, String moduleFile, String mainClassLocation) throws NoSuchMethodException{
        methods = new Method[2];
        this.main = main;
        try{
            classLoader = new URLClassLoader(new URL[]{new File(path, moduleFile).toURI().toURL()}, getClass().getClassLoader());
            moduleClass = Class.forName(mainClassLocation, true, classLoader);
            methods[0] = moduleClass.getDeclaredMethod("load", MSEssentials.class);
            methods[1] = moduleClass.getDeclaredMethod("unload", MSEssentials.class);
            methods[0].invoke(main);

        } catch (MalformedURLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public Method getLoad(){
        return methods[0];
    }

    public Method getUnload(){
        return methods[1];
    }

    public void unload(){
        try{
            getUnload().invoke(main);
            this.classLoader = null;
            this.moduleClass = null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
