package model.entity

import org.ktorm.entity.Entity

interface ExpenseItem : Entity<ExpenseItem> {
  companion object : Entity.Factory<ExpenseItem>()
  val id: Int
  var itemName: String
}