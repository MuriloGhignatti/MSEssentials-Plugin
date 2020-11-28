package me.murilin;

import org.bukkit.Bukkit;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipFile;

public class Modules {

    private HashMap<String, Module> loadedModules;
    private MSEssentials main;
    private ArrayList<String> moduleNames;

    public Modules(MSEssentials main){
        this.main = main;
        this.loadedModules = new HashMap<>();
        this.moduleNames = new ArrayList<>();
    }

    public void loadModules(){
        try{
            String path = Bukkit.getServer().getPluginManager().getPlugin("MSEssentials").getDataFolder().getCanonicalPath() + '\\' + "modules";
            File file = new File(path);
            if (!file.exists())
                file.mkdirs();
            String[] files = file.list();
            for (String s : files) {
                if (s.endsWith(".jar")) {
                    JarFile currentModule = new JarFile(path + '\\' + s);
                    for (Enumeration<JarEntry> entries = currentModule.entries(); entries.hasMoreElements(); ) {
                        JarEntry entry = entries.nextElement();
                        String currentFile = entry.getName();
                        if (currentFile.equals("module.yml")) {
                            InputStream in = new ZipFile(path + '\\' + s).getInputStream(entry);
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                            String line;
                            String name = null;
                            while((line = bufferedReader.readLine()) != null){
                                if(line.contains("main:")){
                                    if(line.contains(": "))
                                        line = line.substring(line.indexOf(": ") + 2);
                                    else
                                        line = line.substring(line.indexOf(":") + 2);
                                    break;
                                }
                                else if(line.contains("name:")){
                                    if(line.contains(": "))
                                        name = line.substring(line.indexOf(": ") + 2);
                                    else
                                        name = line.substring(line.indexOf(":") + 2);
                                }
                            }
                            try {
                                loadedModules.put(name, new Module(main, path, s, line));
                                moduleNames.add(name);
                                main.getServer().getConsoleSender().sendMessage("[MSEssentials] Loaded module: " + name);
                            }
                            catch (NoSuchMethodException e){
                                continue;
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Module getModule(String moduleName){
        return loadedModules.get(moduleName);
    }

    public String[] getModuleNames(){
        return moduleNames.toArray(String[]::new);
    }
}
