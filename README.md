<!--~ Do not edit this derived file! See gatein-portal-quickstarts-parent/src/main/freemarker/jsf2-hello-world-portlet/README.md.ftl ~-->


What is it?
-----------

Projeto para Migrar o sisper de Delphi p Java Portlet -  JavaServer Faces 2.1 
and Portlet Bridge 3.1.0.Final.

An introduction and some background information to this quickstart can be found in the following chapters 
of GateIn Developer Guide:
* [Starting a Portlet Project](https://docs.jboss.org/author/display/GTNPORTAL35/Starting+a+Portlet+Project)
* [JSF2 Portlet Development](https://docs.jboss.org/author/display/GTNPORTAL35/JSF2+Portlet+Development)
* [Basic JSF Portlet Development](https://docs.jboss.org/author/display/GTNPORTAL35/Basic+JSF+Portlet+Development)


<!--~ Included from gatein-portal-quickstarts-parent/src/main/freemarker/include/portlet-general.md.ftl ~-->
<!--~ Included from gatein-portal-quickstarts-parent/src/main/freemarker/include/system-requirements.md.ftl ~-->
System Requirements
-------------------

All you need to build this example project is Java 6.0 (Java SDK 1.6) or newer and Maven 3.0 or newer.

The project is designed to be deployed on GateIn Portal 3.5 running on either
JBoss AS or JBoss EAP. There is no support for JBoss Enterprise Portal Platform (EPP) yet, 
but this example projects will evolve to support the upcoming EPP version 6.


<!--~ Included from gatein-portal-quickstarts-parent/src/main/freemarker/include/configure-maven.md.ftl ~-->
Configure Maven
---------------

You do not need to touch your settings.xml because of this quickstart. All necessary artifacts are available in public
repositories.


<!--~ Included from gatein-portal-quickstarts-parent/src/main/freemarker/include/start-the-portal.md.ftl ~-->
Start the Portal
----------------

1. Open a command line and navigate to the root of the JBoss server directory.
2. The following shows the command line to start the server with the web profile:

        For Linux:   JBOSS_HOME/bin/standalone.sh
        For Windows: JBOSS_HOME\bin\standalone.bat


Build and Deploy the Quickstart
-------------------------------

1. Make sure you have started the Portal as described above.
2. Open a command line and navigate to the root directory of this quickstart.
3. Type this command to build and deploy the archive:

        mvn clean package jboss-as:deploy

   To deploy to other than default localhost:9999 JBoss instance, copy the following configuration 
   just after `<version>${jboss.as.plugin.version}</version>` in the pom.xml file and adjust it to suit your needs.
   `username` and `password` elements can be omitted sometimes, depending on your JBoss security settings.
                 
        <configuration>
            <hostname>127.0.0.1</hostname>
            <port>9999</port>
            <username>admin</username>
            <password>secret</password>
        </configuration>

   This will deploy `target/jsf2-hello-world-portlet.war` to the running instance of the portal.


Access the deployed Portlet
---------------------------



Undeploy the Archive
--------------------

1. Make sure you have started the JBoss Server as described above.
2. Open a command line and navigate to the root directory of this quickstart.
3. When you are finished testing, type this command to undeploy the archive:

        mvn jboss-as:undeploy


Use JBoss Developer Studio or Eclipse with JBoss Tools to Run this Quickstart
-----------------------------------------------------------------------------
You can also deploy the quickstarts from Eclipse using JBoss Tools. For more information on how to set up Maven and JBoss Tools,
refer to the 
[JBoss Enterprise Application Platform 6 Development Guide](https://access.redhat.com/knowledge/docs/JBoss_Enterprise_Application_Platform/) 
or [Get Started Developing Applications](http://www.jboss.org/jdf/quickstarts/jboss-as-quickstart/guide/Introduction/ "Get Started Developing Applications").


<!--~ Included from gatein-portal-quickstarts-parent/src/main/freemarker/include/debug.md.ftl ~-->
Debug the Application
---------------------

If you want to debug the source code or look at the Javadocs of any library in the project, run either of the following 
commands to pull them into your local repository. The IDE should then detect them.

        mvn dependency:sources
        mvn dependency:resolve -Dclassifier=javadoc


<!--~ Included from gatein-portal-quickstarts-parent/src/main/freemarker/include/feedback.md.ftl ~-->
Feedback
--------
- modificado pelo linux na linha de commando em 26.05.2014
Please post feedback on this quickstart or GateIn on [User Forum](https://community.jboss.org/en/gatein?view=discussions).
