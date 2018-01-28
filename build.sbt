organization := "org.red"

name := "eve-sde-slick"

version := "0.1-SNAPSHOT"

scalaVersion := "2.12.4"

scalacOptions ++= Seq("-deprecation", "-feature")

publishTo := Some("Artifactory Realm" at "http://maven.red.greg2010.me/artifactory/sbt-local")
credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")
val slickVersion = "3.2.1"

libraryDependencies ++= Seq(
  "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
  "ch.qos.logback" % "logback-classic" % "1.1.2",
  "com.typesafe.slick" %% "slick" % slickVersion,
  "com.typesafe.slick" %% "slick-codegen" % slickVersion,
  "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
  "com.github.tminglei" %% "slick-pg" % "0.15.0",
  "org.postgresql" % "postgresql" % "42.2.1"
)


slick := { slickCodeGenTask.value } // register manual sbt command
//sourceGenerators in Compile <+= slickCodeGenTask // register automatic code generation on every compile, remove for only manual use


// code generation task
lazy val slick = TaskKey[Seq[File]]("gen-tables")
lazy val slickCodeGenTask = Def.task {
  val dir = managedDirectory.value
  val cp = (fullClasspath in Compile).value
  val r = (runner in Compile).value
  val s = streams.value
  val outputDir = dir.getPath // place generated files in sbt's managed sources folder
  r.run("org.red.SlickCodegen.CustomCodeGen", cp.files, Array(outputDir), s.log).failed foreach(sys error _.getMessage)
  val fname = outputDir
  Seq(file(fname))
}
/*
lazy val slickCodeGenTask = (sourceDirectory, fullClasspath in Compile, runner in Compile, streams) map { (dir, cp, r, s) =>
  val outputDir = (dir / "main/scala").getPath
  val fileName = outputDir + "/org/red/db/models/KillmailScraper.scala"
  toError(r.run("org.red.SlickCodegen.CustomCodeGen", cp.files, Array(outputDir), s.log))
  Seq(file(fileName))
}*/