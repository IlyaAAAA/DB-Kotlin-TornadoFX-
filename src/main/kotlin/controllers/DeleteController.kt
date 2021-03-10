package controllers

import model.data.Charges
import model.data.ExpenseItems
import model.data.Sales
import model.data.Warehouses
import model.db.DataBaseConnection
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq
import tornadofx.Controller

class DeleteController : Controller() {

  private val connect = DataBaseConnection.database

  fun deleteFromSalesById(id: Int) {
    connect.delete(Sales) { it.id eq id }
  }

  fun deleteFromWarehouseById(id: Int) {
    connect.delete(Sales) { it.warehouse eq id }

    connect.delete(Warehouses) { it.id eq id }
  }

  fun deleteFromExpenseItemsById(id: Int) {
    connect.delete(Charges) { it.expenseItem eq id }

    connect.delete(ExpenseItems) { it.id eq id }
  }

  fun deleteFromChargesById(id: Int) {
    connect.delete(Charges) { it.id eq id }
  }
}