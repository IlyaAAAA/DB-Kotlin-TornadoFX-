package controllers

import model.data.Sales
import model.db.DataBaseConnection
import model.db.DataBaseConnection.sales
import model.db.DataBaseConnection.warehouses
import org.ktorm.dsl.eq
import org.ktorm.dsl.insert
import org.ktorm.entity.find
import tornadofx.Controller
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class SalesController : Controller() {
  private val database = DataBaseConnection.database

  fun addSale(amount: Int, quantity: Int, saleDate: LocalDate, warehouseName: String) {

    val warehouse = database.warehouses.find { it.warehouseName eq warehouseName } ?: return

    println(warehouseName)
    println(warehouse)

    database.insert(Sales) {
      set(it.amount, amount)
      set(it.quantity, quantity)
      set(it.saleDate, Timestamp.valueOf(LocalDateTime.of(saleDate, LocalTime.now())))
      set(it.warehouse, warehouse.id)
    }
  }

  fun editSale(id: Int, amount: Int, quantity: Int, saleDate: LocalDate, warehouseName: String) {
    val sale = database.sales.find { it.id eq id } ?: return

    val timestampDate = Timestamp.valueOf(LocalDateTime.of(saleDate, LocalTime.now()))

    sale.amount = amount
    sale.quantity = quantity
    sale.saleDate = timestampDate
    sale.warehouse = database.warehouses.find { it.warehouseName eq warehouseName } ?: return
    sale.flushChanges()
  }
}