package controllers

import model.data.Warehouses
import model.db.DataBaseConnection
import model.db.DataBaseConnection.warehouses
import org.ktorm.dsl.eq
import org.ktorm.dsl.insert
import org.ktorm.entity.find
import tornadofx.Controller

class WarehousesController : Controller() {
  private val database = DataBaseConnection.database

  fun addWarehouseItem(name: String, quantity: Int, amount: Int) {
    database.insert(Warehouses) {
      set(it.warehouseName, name)
      set(it.quantity, quantity)
      set(it.amount, amount)
    }
  }

  fun editWarehouseItem(id: Int, name: String, quantity: Int, amount: Int) {
    val warehouseItem = database.warehouses.find { it.id eq id } ?: return
    warehouseItem.warehouseName = name
    warehouseItem.quantity = quantity
    warehouseItem.amount = amount
    warehouseItem.flushChanges()
  }
}