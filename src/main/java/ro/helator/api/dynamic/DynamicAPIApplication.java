package ro.helator.api.dynamic;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import javax.tools.ToolProvider;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = {"ro.helator.api.dynamic"})
public class DynamicAPIApplication {

    private static ConfigurableApplicationContext context;


    public static void main(String[] args) {
        replaceSysyemClassLoader();
        loadDynamicClasses();
        context = SpringApplication.run(DynamicAPIApplication.class, args);
    }

    public static void restart() {
        ApplicationArguments args = context.getBean(ApplicationArguments.class);

        Thread thread = new Thread(() -> {
            context.close();
            loadDynamicClasses();
            context = SpringApplication.run(DynamicAPIApplication.class, args.getSourceArgs());
        });

        thread.setDaemon(false);
        thread.start();
    }

    public static ConfigurableApplicationContext getContext() {
        return context;
    }

    private static final Class[] parameters = new Class[]{URL.class};

    private static void loadDynamicClasses() {
        File f = new File("DynamicAPI/Classes/");
        if (f.listFiles() != null && f.listFiles().length > 0) {
            try {
                Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
                method.setAccessible(true);
                method.invoke(ClassLoader.getSystemClassLoader(), f.toURI().toURL());
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    private static void replaceSysyemClassLoader(){
        try {
            Field scl = ClassLoader.class.getDeclaredField("scl");
            scl.setAccessible(true);
            scl.set(null, new URLClassLoader(new URL[0]));
            Thread.currentThread().setContextClassLoader(new URLClassLoader(new URL[0], ClassLoader.getSystemClassLoader()));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
