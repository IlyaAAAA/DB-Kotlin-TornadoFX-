package model.entity

import org.ktorm.entity.Entity
import java.sql.Timestamp

interface Sale : Entity<Sale> {
  companion object : Entity.Factory<Sale>()
  val id: Int
  var amount: Int
  var quantity: Int
  var saleDate: Timestamp
  var warehouse: Warehouse
}