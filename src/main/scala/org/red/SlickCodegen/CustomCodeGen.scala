package org.red.SlickCodegen

import org.red.db.dbAgent
import slick.codegen.SourceCodeGenerator
import slick.jdbc.PostgresProfile
import slick.jdbc.meta.MTable

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps


object CustomCodeGen {
  def main(args: Array[String]): Unit = {
    Await.ready(
      codegen.map(_.writeToFile(
        "org.red.SlickCodegen.CustomPostgresDriver",
        args(0),
        "org.red.db.models", // package under which the generated code is placed
        "Sde", // container
        "Sde.scala" // filename
      )),
      90 seconds
    )
  }
    private def codegen = dbAgent.run {

      val list = MTable.getTables(None, None, None, Some(Seq("TABLE", "VIEW")))

      list.flatMap { x =>
        println(x)
        PostgresProfile.createModelBuilder(x, ignoreInvalidDefaults = true).buildModel
      }
    }.map { model => new SourceCodeGenerator(model) }

}