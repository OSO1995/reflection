import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Set;
import java.util.stream.Collectors;

public class ShowMethods {
  private static Deque<Set<Method>> stackMethods = new ArrayDeque<>();

  public static void showMethods(Class c) {
    fillStack(c);
    showMethodsHelper(c);
  }

  private static void fillStack(Class c) {
    Set<Method> tmp = Arrays.stream(c.getMethods()).collect(Collectors.toSet());
    pushNecessaryMethods(tmp);
    Class superClass = c.getSuperclass();

    while (superClass != null) {
      fillStack(superClass);
      break;
    }
  }

  private static void pushNecessaryMethods(Set<Method> methods) {
    if (stackMethods.peekFirst() != null) {
      stackMethods.push(stackMethods.pop().stream()
          .filter(x -> !methods.contains(x))
          .collect(Collectors.toSet()));
    }
    stackMethods.push(methods);
  }

  private static void showMethodsHelper(Class c) {
    System.out.println(c.getName());
    showAnnotation(stackMethods.pollLast());
    Class superClass = c.getSuperclass();

    while (superClass != null) {
      showMethodsHelper(superClass);
      break;
    }
  }

  private static void showAnnotation(Set<Method> arrMethods){
    arrMethods.forEach(x -> {
          Arrays.stream(x.getAnnotations()).forEach(z-> {
            if (z.annotationType().getName().equals("SomeAnnotation")){
              System.out.print("*");
            }
          });
          System.out.println(x);
        }
    );
  }
}
