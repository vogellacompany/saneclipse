saneclipse
==========

Improved settings and code templates for Eclipse

This project provides a set of plug-ins which:

* Registers several default file endings if the Eclipse text editor, because opening the system editor is typically not the desired behavior
* Adds more JDT templates for JFace, Android and Java programmer
* Configures the preference at startup of Eclipse to defaults we think have proven to be reasonable
* Disable the p2 automatic update action has this delays the startup of the Eclipse IDE by 500ms (https://bugs.eclipse.org/bugs/show_bug.cgi?id=443809)

Homepage
--------

You find its homepage at http://saneclipse.vogella.com/. The source is free available under the EPL at: https://github.com/vogellacompany/com.vogella.saneclipse 

Installation
-----------
The p2 update site is hosted at bintray under http://dl.bintray.com/vogellacompany/saneclipse/.

Use the Eclipse Help -> Install new Software to install the tools from it: 

    http://dl.bintray.com/vogellacompany/saneclipse/ 

Building saneclipse
-------------------

Use Maven to build saneclipse

    mvn clean verify

The p2 update site can be found in GIT_REPO/com.vogella.saneclipse/com.vogella.saneclipse.repository/target/repository/

