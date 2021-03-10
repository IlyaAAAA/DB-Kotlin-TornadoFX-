package model.data

import model.entity.Charge
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.jdbcTimestamp

object Charges : Table<Charge>("Charges") {
  val id = int("ID").primaryKey().bindTo { it.id }
  val amount = int("AMOUNT").bindTo { it.amount }
  val chargeDate = jdbcTimestamp("CHARGE_DATE").bindTo { it.chargeDate }
  val expenseItem = int("EXPENSE_ITEM_ID").references(ExpenseItems) { it.expenseItem }
}