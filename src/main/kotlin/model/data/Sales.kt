package model.data

import model.entity.Sale
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.jdbcTimestamp

object Sales : Table<Sale>("Sales") {
  val id = int("ID").primaryKey().bindTo { it.id }
  val amount = int("AMOUNT").bindTo { it.amount }
  val quantity = int("QUANTITY").bindTo { it.quantity }
  val saleDate = jdbcTimestamp("SALE_DATE").bindTo { it.saleDate }
  val warehouse = int("WAREHOUSES_ID").references(Warehouses) { it.warehouse }
}