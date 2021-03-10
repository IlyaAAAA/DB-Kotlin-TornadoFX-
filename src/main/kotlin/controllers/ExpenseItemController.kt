package controllers

import model.data.ExpenseItems
import model.db.DataBaseConnection
import model.db.DataBaseConnection.expenseItems
import org.ktorm.dsl.eq
import org.ktorm.dsl.insert
import org.ktorm.entity.find
import tornadofx.Controller

class ExpenseItemController : Controller() {

  private val database = DataBaseConnection.database

  fun addExpenseItem(name: String) {
    database.insert(ExpenseItems) {
      set(it.itemName, name)
    }
  }

  fun editExpenseItem(id: Int, name: String) {
    val expenseItem = database.expenseItems.find { it.id eq id } ?: return
    expenseItem.itemName = name
    expenseItem.flushChanges()
  }
}