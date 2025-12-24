package core.spring.springcoremoduleproject;

import core.spring.springcoremoduleproject.Services.OperationsConsoleListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {

        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext("core.spring.springcoremoduleproject");

        OperationsConsoleListener operationsConsoleListener = applicationContext.getBean(OperationsConsoleListener.class);

        operationsConsoleListener.start();

    }

}
