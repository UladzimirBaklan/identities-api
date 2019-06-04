name := "identities"
 
version := "1.0" 
      
lazy val `identities` = (project in file(".")).enablePlugins(PlayScala)
resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"
scalaVersion := "2.12.2"
unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

libraryDependencies ++= Seq(jdbc , ehcache , ws , specs2 % Test , guice)
libraryDependencies += "com.typesafe.play" %% "play-slick" % "5.0.0-M2"
libraryDependencies += "org.postgresql" % "postgresql" % "42.2.5"
