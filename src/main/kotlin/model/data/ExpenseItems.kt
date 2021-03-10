package model.data

import model.entity.ExpenseItem
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object ExpenseItems : Table<ExpenseItem>("Expense_items") {
  val id = int("ID").primaryKey().bindTo { it.id }
  val itemName = varchar("NAME").bindTo { it.itemName }
}