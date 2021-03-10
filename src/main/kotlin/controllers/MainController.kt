package controllers

import model.data.Charges
import model.data.ExpenseItems
import model.data.Sales
import model.data.Warehouses
import model.db.DataBaseConnection
import model.entity.Charge
import model.entity.ExpenseItem
import model.entity.Sale
import model.entity.Warehouse
import org.ktorm.dsl.*
import tornadofx.Controller

class MainController : Controller() {
  private val connect = DataBaseConnection.database

  fun getSales(): List<Sale> {
    val sales = connect.from(Sales)
      .select()
      .orderBy(Sales.id.asc())
      .map { Sales.createEntity(it) }
    return sales.toList()
  }

  fun getWarehouses(): List<Warehouse> {
    val warehouses = connect.from(Warehouses)
      .select()
      .orderBy(Warehouses.id.asc())
      .map { Warehouses.createEntity(it) }
    return warehouses.toList()
  }

  fun getExpenseItems(): List<ExpenseItem> {
    val expenseItems = connect.from(ExpenseItems)
      .select()
      .orderBy(ExpenseItems.id.asc())
      .map { ExpenseItems.createEntity(it) }
    return expenseItems.toList()
  }

  fun getCharges(): List<Charge> {
    val charges = connect.from(Charges)
      .select()
      .orderBy(Charges.id.asc())
      .map { Charges.createEntity(it) }
    return charges.toList()
  }

  fun getExpenseItemNameById(id: Int): String {
    val expenseItem = connect.from(ExpenseItems)
      .select(ExpenseItems.itemName)
      .where(ExpenseItems.id eq id)
      .map { ExpenseItems.createEntity(it) }

    return expenseItem[0].itemName
  }

  fun getWarehouseNameById(id: Int): String {
    val warehouse = connect.from(Warehouses)
      .select(Warehouses.warehouseName)
      .where(Warehouses.id eq id)
      .map { Warehouses.createEntity(it) }

    return warehouse[0].warehouseName
  }

  fun getWarehousesName(): List<String> {
    val warehouses = connect.from(Warehouses)
      .select(Warehouses.warehouseName)
      .map { Warehouses.createEntity(it) }

    val warehousesNameList = mutableListOf<String>()

    warehouses.forEach {
      warehousesNameList.add(it.warehouseName)
    }

    return warehousesNameList
  }

  fun getExpenseItemsName(): List<String> {
    val expenseItem = connect.from(ExpenseItems)
      .select(ExpenseItems.itemName)
      .map { ExpenseItems.createEntity(it) }

    val expenseItemNameList = mutableListOf<String>()

    expenseItem.forEach {
      expenseItemNameList.add(it.itemName)
    }

    return expenseItemNameList
  }
}