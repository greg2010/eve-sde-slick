package org.red

import java.io.File

import com.typesafe.config.ConfigFactory
import slick.jdbc.JdbcBackend.Database

/**
  * Created by greg2010 on 25/02/17.
  */
package object db {
  val parsedConfig = ConfigFactory.parseFile(new File("src/main/resources/reference.conf"))
  private val DBConfig = ConfigFactory.load(parsedConfig)
  val dbAgent = Database.forConfig("postgres", DBConfig)

}
