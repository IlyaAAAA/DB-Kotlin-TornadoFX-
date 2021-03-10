package views

import controllers.DeleteController
import controllers.MainController
import javafx.scene.control.TabPane
import javafx.scene.control.TableView
import model.entity.Charge
import model.entity.ExpenseItem
import model.entity.Sale
import model.entity.Warehouse
import tornadofx.*

class MainView : View("Main View") {

  private val mainController: MainController by inject()
  private val deleteController: DeleteController by inject()

  private var salesTable: TableView<Sale> by singleAssign()
  private var warehousesTable: TableView<Warehouse> by singleAssign()
  private var chargesTable: TableView<Charge> by singleAssign()
  private var expenseItemsTable: TableView<ExpenseItem> by singleAssign()

  private var warehouses = mainController.getWarehouses().asObservable()
  private var sales = mainController.getSales().asObservable()
  private var expenseItems = mainController.getExpenseItems().asObservable()
  private var charges = mainController.getCharges().asObservable()

  override val root = tabpane {

    setPrefSize(600.0, 400.0)

    tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE

    tab("Warehouses") {
      vbox {
        tableview(warehouses) {
          readonlyColumn("ID", Warehouse::id)
          readonlyColumn("Name", Warehouse::warehouseName)
          readonlyColumn("Quantity", Warehouse::quantity)
          readonlyColumn("Amount", Warehouse::amount)

          warehousesTable = this

          smartResize()

          contextmenu {

            item("Add").action {
              WarehousesView("Add warehouse item", "Add") {
                updateWarehouses()
                warehousesTable.items = warehouses
              }.openModal(resizable = false)
            }

            item("Edit").action {
              selectedItem?.let {
                WarehousesView("Edit Warehouse", "Ok", it) {
                  updateWarehouses()
                  warehousesTable.items = warehouses
                }.openModal(resizable = false)
              }
            }

            item("Delete").action {
              selectedItem?.let {
                deleteController.deleteFromWarehouseById(it.id)
              }

              updateWarehouses()
              updateSales()

              warehousesTable.items = warehouses
              salesTable.items = sales
            }
          }
        }
      }
    }
    tab("Sales") {
      vbox {
        tableview(sales) {
          readonlyColumn("ID", Sale::id)
          readonlyColumn("Amount", Sale::amount)
          readonlyColumn("Quantity", Sale::quantity)
          readonlyColumn("Sale date", Sale::saleDate)
          readonlyColumn("Warehouse", Sale::warehouse).cellFormat {
            text = mainController.getWarehouseNameById(it.id)
          }

          salesTable = this

          smartResize()

          contextmenu {

            item("Add").action {
              SalesView("Add sale", "Add") {
                updateSales()
                salesTable.items = sales
              }.openModal(resizable = false)
            }

            item("Edit").action {
              selectedItem?.let {
                SalesView("Edit sale", "Ok", it, it.warehouse) {
                  updateSales()
                  salesTable.items = sales
                }.openModal(resizable = false)
              }
            }

            item("Delete").action {
              selectedItem?.let { sale ->
                deleteController.deleteFromSalesById(sale.id)
              }
              updateSales()

              salesTable.items = sales
            }
          }
        }
      }
    }

    tab("Charges") {
      vbox {
        tableview(charges) {
          readonlyColumn("ID", Charge::id)
          readonlyColumn("Amount", Charge::amount)
          readonlyColumn("Charge date", Charge::chargeDate)
          readonlyColumn("Expense Item", Charge::expenseItem).cellFormat {
            text = mainController.getExpenseItemNameById(it.id)
          }

          chargesTable = this

          smartResize()

          contextmenu {

            item("Add").action {
              ChargesView("Add charge", "Add") {
                updateCharges()
                chargesTable.items = charges
              }.openModal(resizable = false)
            }

            item("Edit").action {
              selectedItem?.let {
                ChargesView("Charge edit", "Ok", it, it.expenseItem) {
                  updateCharges()
                  chargesTable.items = charges
                }.openModal(resizable = false)
              }
            }

            item("Delete").action {
              selectedItem?.let {
                deleteController.deleteFromChargesById(it.id)
              }

              updateCharges()

              chargesTable.items = charges
            }
          }
        }
      }
    }

    tab("ExpenseItems") {
      vbox {
        tableview(expenseItems) {
          readonlyColumn("ID", ExpenseItem::id)
          readonlyColumn("Name", ExpenseItem::itemName)

          expenseItemsTable = this

          smartResize()

          contextmenu {

            item("Add").action {
              ExpenseItemsView("Add expense item", "Add") {
                updateExpenseItems()
                expenseItemsTable.items = expenseItems
              }.openModal(resizable = false)
            }

            item("Edit").action {
              selectedItem?.let {
                ExpenseItemsView("Edit expense item", "Ok", it) {
                  updateExpenseItems()
                  expenseItemsTable.items = expenseItems
                }.openModal(resizable = false)
              }
            }

            item("Delete").action {
              selectedItem?.let {
                deleteController.deleteFromExpenseItemsById(it.id)
              }

              updateExpenseItems()
              updateCharges()

              expenseItemsTable.items = expenseItems
              chargesTable.items = charges
            }
          }
        }
      }
    }

    tab("Actions") {
      hbox {

        button("Profitable goods") {
          action {
            ProfitableGoodsView().openModal(resizable = false)
          }
        }

        button("Monthly income") {
          action {
            MonthlyIncomeVIew().openModal(resizable = false)
          }
        }
      }
    }
  }


  private fun updateSales() {
    sales = mainController.getSales().asObservable()
  }

  private fun updateWarehouses() {
    warehouses = mainController.getWarehouses().asObservable()
  }

  private fun updateCharges() {
    charges = mainController.getCharges().asObservable()
  }

  private fun updateExpenseItems() {
    expenseItems = mainController.getExpenseItems().asObservable()
  }
}
