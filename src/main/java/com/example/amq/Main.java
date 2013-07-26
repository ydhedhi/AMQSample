package com.example.amq;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *@author Yogesh yogeshdhedhi@gmail.com
 * Main class
 *
 */
public class Main {
    public static void main( String[] args )
    {
       new ClassPathXmlApplicationContext("messaging.xml");
    }
}
