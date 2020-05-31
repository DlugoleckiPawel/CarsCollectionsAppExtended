# CarsCollectionsAppExtended
 An extended version of the console application that allows you to conveniently search for a car collection according to the selected criterion and view statistics

# Technologies

## General
* Java 13 (using Switch Expressions )
* Apache Maven 3.6.2

## Dependencies
* Gson 2.8.6
* Eclipse-Collections 9.2.0
* Lombok 1.18.12

## Plugins
Plugin that allows using the SWITCH CASE statement

        <plugins>
            <plugin>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <release>13</release>
                    <compilerArgs>
                        --enable-preview
                    </compilerArgs>
                </configuration>
            </plugin>
            <plugin>
                <artifactId>maven-surefire-plugin</artifactId>
                <configuration>
                    <argLine>--enable-preview</argLine>
                </configuration>
            </plugin>

## Setup
Download the project:

https://github.com/DlugoleckiPawel/CarsCollectionsAppExtended.git

and then run the App.main method.

NOTE: There might be some issues with files directory relative path pattern which highly depends on your Operating System. Originally the program is designed to be run on the windows platform.

## Features
* Unit tests 
* Convert to multi-module app
* Development of service methods
