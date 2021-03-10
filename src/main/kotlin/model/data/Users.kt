package model.data

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object Users: Table<Nothing>("Users") {
  val id = int("ID").primaryKey()
  val name = varchar("NAME")
  val password = varchar("PASSWORD")
}