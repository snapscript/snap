SnapScript
==============

Snap is an open source, optionally typed, object oriented scripting language for the Java platform. The learning curve is small for anyone with experience of Java, JavaScript, TypeScript, or Scala, amongst others. It has excellent integration with the host platform, and can do whatever can be done with Java and much more.

The language is ideal for embedding in to an existing application, and is a fraction of the size of similar languages for the Java platform. In addition to embedding it can be run as a standalone interpreter and has an development environment which allows scripts to be debugged and profiled.

[http://www.snapscript.org/](http://www.snapscript.org/)

#### Development Environment

Snap comes with a development environment that is available over HTTP on any web browser that supports Web Sockets. To develop and debug scripts the devlopment environment can be started with the command shown below. All that is required is a web browser and Java 1.5 or greater. The development environment is a single JAR file that can be downloaded from [here](http://www.snapscript.org/download.html). In addition an embeddable version is available which can be integrated in to any Java application.

*java -jar snapd.jar --directory=work --port=4457*

The development environment can use hot stand-by agents to improve responsiveness, the agent pool can be configured on the command line. In addition an agent can connect through the HTTP port using the HTTP CONNECT request and begin a debug session. An example configuration is shown below.

*java -jar snapd.jar --directory=work --port=4457 --agent-pool=4 --server-only=true*

Videos can be viewed here

#### Debug Android Game
[![Debug Android Game](http://img.youtube.com/vi/w-baBQbZ5dI/0.jpg)](https://www.youtube.com/watch?v=w-baBQbZ5dI)
#### Debug Desktop Game
[![Debug Desktop Game](http://img.youtube.com/vi/6vo2y83unG0/0.jpg)](https://www.youtube.com/watch?v=6vo2y83unG0)

Below are some screenshots of the development environment. 

##### Breakpoints
![Developer Breakpoints](https://raw.githubusercontent.com/snapscript/snap-site/master/images/debugger_breakpoints.png)
##### Console
![Developer Console](https://raw.githubusercontent.com/snapscript/snap-site/master/images/debugger_console.png)
##### Variables
![Developer Variables](https://raw.githubusercontent.com/snapscript/snap-site/master/images/debugger_variables.png)
##### Threads
![Developer Threads](https://raw.githubusercontent.com/snapscript/snap-site/master/images/debugger_threads.png)
##### Process View
![Developer Debug](https://raw.githubusercontent.com/snapscript/snap-site/master/images/debugger_agents.png)
##### Profiler
![Developer Profiler](https://raw.githubusercontent.com/snapscript/snap-site/master/images/debugger_profiler.png)
##### Debug Perspective
![Developer Debug Perspective](https://raw.githubusercontent.com/snapscript/snap-site/master/images/debugger_changelayout.png)
##### Full Screen
![Developer Full Screen](https://raw.githubusercontent.com/snapscript/snap-site/master/images/debugger_fullscreen.png)
##### Dark Theme
![Developer Dark Theme](https://raw.githubusercontent.com/snapscript/snap-site/master/images/debugger_darktheme.png)
##### Search Types
![Developer Search Types](https://raw.githubusercontent.com/snapscript/snap-site/master/images/debugger_searchtypes.png)
##### Search Files
![Developer Search Files](https://raw.githubusercontent.com/snapscript/snap-site/master/images/debugger_searchfiles.png)
##### File Files
![Developer Find Files](https://raw.githubusercontent.com/snapscript/snap-site/master/images/debugger_findfiles.png)
##### Save Resources
![Developer Debug](https://raw.githubusercontent.com/snapscript/snap-site/master/images/debugger_save.png)
##### Open Projects
![Developer Debug](https://raw.githubusercontent.com/snapscript/snap-site/master/images/debugger_open.png)


