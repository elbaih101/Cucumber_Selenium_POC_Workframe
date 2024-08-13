In fact WebDriverManager can help with the setup of Selenium Grid. The process would be as follows:

1.Start hub and nodes. You can do it using the command line (java -jar selenium-server-standalone-<version>.jar standalone), or use a Java class with the help of WebDriverManager to download the proper drivers. Here you can find an example using Chrome and Firefox as browsers. 
https://github.com/bonigarcia/webdrivermanager-examples/blob/master/src/main/java/io/github/bonigarcia/wdm/StartHub.java
2.Run your test, connecting to the URL's hub. Here another example.
https://github.com/bonigarcia/webdrivermanager-examples/blob/master/src/test/java/io/github/bonigarcia/wdm/test/remote/RemoteTest.java
