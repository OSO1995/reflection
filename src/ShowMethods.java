import java.lang.reflect.Method;
import java.util.Arrays;

public class ShowMethods {
  static void showMethods(Class c) {
    Method[] arrMethods = c.getMethods();

    Arrays.stream(arrMethods).forEach(x -> {
          Arrays.stream(x.getAnnotations()).forEach(z-> {
            if (z.annotationType().getName().equals("SomeAnnotation")){
              System.out.println("*");
            }
          });
          System.out.println(x);
        }
    );
  }
}
