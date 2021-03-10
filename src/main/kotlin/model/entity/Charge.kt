package model.entity

import org.ktorm.entity.Entity
import java.sql.Timestamp
import java.time.Instant


interface Charge : Entity<Charge> {
  companion object : Entity.Factory<Charge>()
  val id: Int
  var amount: Int
  var chargeDate: Timestamp
  var expenseItem: ExpenseItem
}