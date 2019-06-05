package ro.helator.api.dynamic;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class JavaCompilerUtil {

    public static String rootPath= "DynamicAPI/Classes/ro/helator/api/dynamic/controller/";
    private static JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

    public static void compileClass(String service, String className, String classContent) throws Exception {
        File sourceClassFile = new File(rootPath + service + "/" + className + ".java");
        sourceClassFile.getParentFile().mkdirs();
        sourceClassFile.createNewFile();
        FileWriter fw = new FileWriter(sourceClassFile);
        fw.write(classContent);
        fw.flush();
        fw.close();

        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> compilationUnits1 =
            fileManager.getJavaFileObjectsFromFiles(Arrays.asList(sourceClassFile));
        compiler.getTask(null, fileManager, null, null, null, compilationUnits1).call();
    }

    public static void compileClasses(String service, Map<String, String> classes) throws Exception {
        Set<Entry<String,String>> entries = classes.entrySet();
        List<File> sourceClassFiles = new ArrayList<>();
        for( Entry<String, String> entry : entries) {
            File sourceClassFile = new File(rootPath + service + "/" + entry.getKey() + ".java");
            sourceClassFile.getParentFile().mkdirs();
            sourceClassFile.createNewFile();
            FileWriter fw = new FileWriter(sourceClassFile);
            fw.write(entry.getValue());
            fw.flush();
            fw.close();
            sourceClassFiles.add(sourceClassFile);
        }
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

        Iterable<? extends JavaFileObject> compilationUnits1 =
            fileManager.getJavaFileObjectsFromFiles(sourceClassFiles);
        compiler.getTask(null, fileManager, null, null, null, compilationUnits1).call();
    }

    public static List<File> getClasses(String path) throws Exception{

        File root = new File(path != null ? path : rootPath);
        List<File> classes = new ArrayList<>();
        for(File file : root.listFiles()){
            if(file.isDirectory()){
                classes.addAll(getClasses(file.getPath()));
            } else if (file.getName().endsWith(".class")){
                classes.add(file);
            }
        }
        return classes;
    }
}
