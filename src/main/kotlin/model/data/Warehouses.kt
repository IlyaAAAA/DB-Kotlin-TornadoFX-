package model.data

import model.entity.Warehouse
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object Warehouses: Table<Warehouse>("Warehouses") {
  val id = int("ID").primaryKey().bindTo { it.id }
  val warehouseName = varchar("NAME").bindTo { it.warehouseName }
  val quantity = int("QUANTITY").bindTo { it.quantity }
  val amount = int("AMOUNT").bindTo { it.amount }
}