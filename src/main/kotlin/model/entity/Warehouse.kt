package model.entity

import org.ktorm.entity.Entity

interface Warehouse : Entity<Warehouse> {
  companion object : Entity.Factory<Warehouse>()
  val id: Int
  var warehouseName: String
  var quantity: Int
  var amount: Int
}