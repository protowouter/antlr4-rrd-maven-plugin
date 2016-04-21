# antlr4-rrd-maven-plugin

antlr4-rrd-maven-plugin is a maven plugin that can be used to generate syntax
diagrams for antlr grammars (railroad diagrams).


### Installation

This plugin is not hosted in the Central maven repository but instead only on github.
Therefore you need to add the following element to the pom to allow this plugin to be used.

    <pluginRepositories>
        <pluginRepository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </pluginRepository>
    </pluginRepositories>

The following has to be added to the pom to add the plugin to your maven project.

    <plugin>
        <groupId>com.github.protowouter</groupId>
        <artifactId>antlr4-rrd-maven-plugin</artifactId>
        <version>1.2</version>
        <executions>
            <execution>
                <id>antlr</id>
                <goals>
                    <goal>railroad</goal>
                </goals>
            </execution>
        </executions>
    </plugin>



### Usage

The plugin assumes that the default maven directory structure is used. This means that grammar files are in (subdirectories of)
src/main/antlr4

If you want to deviate from this default you can set the configuration option sourceDirectory

When you build your maven project the generated diagrams will be put in target/doc/railroad.


### Example pom.xml

This is the pom i use for the course Programming Paradigms.

    <?xml version="1.0" encoding="UTF-8"?>
    <project xmlns="http://maven.apache.org/POM/4.0.0"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
        <modelVersion>4.0.0</modelVersion>

        <groupId>com.lucwo</groupId>
        <artifactId>prog-dimes</artifactId>
        <version>0.1-SNAPSHOT</version>

        <pluginRepositories>
            <pluginRepository>
                <id>jitpack.io</id>
                <url>https://jitpack.io</url>
            </pluginRepository>
        </pluginRepositories>

        <build>
            <plugins>
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-compiler-plugin</artifactId>
                    <version>3.5.1</version>
                    <configuration>
                        <source>1.8</source>
                        <target>1.8</target>
                    </configuration>
                </plugin>
                <plugin>
                    <groupId>org.antlr</groupId>
                    <artifactId>antlr4-maven-plugin</artifactId>
                    <version>4.5.3</version>
                    <executions>
                        <execution>
                            <id>antlr</id>
                            <goals>
                                <goal>antlr4</goal>
                            </goals>
                        </execution>
                    </executions>
                </plugin>
                <plugin>
                    <groupId>com.github.protowouter</groupId>
                    <artifactId>antlr4-rrd-maven-plugin</artifactId>
                    <version>1.2</version>
                    <executions>
                        <execution>
                            <id>antlr</id>
                            <goals>
                                <goal>railroad</goal>
                            </goals>
                        </execution>
                    </executions>
                </plugin>
            </plugins>
        </build>

        <dependencies>
            <dependency>
                <groupId>org.antlr</groupId>
                <artifactId>antlr4</artifactId>
                <version>4.5.3</version>
            </dependency>
            <dependency>
                <groupId>junit</groupId>
                <artifactId>junit</artifactId>
                <version>4.12</version>
            </dependency>
            <dependency>
                <groupId>net.jcip</groupId>
                <artifactId>jcip-annotations</artifactId>
                <version>1.0</version>
            </dependency>
        </dependencies>


    </project>