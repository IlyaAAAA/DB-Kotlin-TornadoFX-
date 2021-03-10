package views

import controllers.MainController
import controllers.SalesController
import javafx.geometry.Orientation
import javafx.scene.control.ComboBox
import javafx.scene.control.DatePicker
import javafx.scene.control.TextField
import model.entity.Sale
import model.entity.Warehouse
import tornadofx.*
import java.time.LocalDate

class SalesView(title: String, private val buttonText: String, private val op: () -> Unit) : Fragment(title) {

  constructor(title: String, buttonText: String, sale: Sale, warehouse: Warehouse, op: () -> Unit) : this(
    title,
    buttonText,
    op
  ) {
    amountField.text = sale.amount.toString()
    quantityField.text = sale.quantity.toString()
    saleDateField.value = sale.saleDate.toLocalDateTime().toLocalDate()
    warehouseComboBox.value = mainController.getWarehouseNameById(warehouse.id)
    this.sale = sale
  }

  private val mainController: MainController by inject()
  private val salesController: SalesController by inject()

  private val warehousesNameList = mainController.getWarehousesName()
  private lateinit var sale: Sale

  private var amountField: TextField by singleAssign()
  private var quantityField: TextField by singleAssign()
  private var saleDateField: DatePicker by singleAssign()
  private var warehouseComboBox: ComboBox<String> by singleAssign()

  override val root = form {
    fieldset(labelPosition = Orientation.VERTICAL) {
      field("Amount") {
        textfield {
          amountField = this
        }
      }
      field("Quantity") {
        textfield {
          quantityField = this
        }
      }
      field("Sale date") {
        datepicker {
          saleDateField = this
          value = LocalDate.now()
        }
      }

      field("Warehouse item") {
        combobox(values = warehousesNameList) {
          warehouseComboBox = this
        }
      }

      vbox {
        button(buttonText) {
          action {
            try {
              when (buttonText) {
                "Add" -> addAction(
                  Integer.parseInt(amountField.text),
                  Integer.parseInt(quantityField.text),
                  saleDateField.value,
                  warehouseComboBox.value
                )
                "Ok" -> editAction(
                  Integer.parseInt(amountField.text),
                  Integer.parseInt(quantityField.text),
                  saleDateField.value,
                  warehouseComboBox.value
                )
              }
              op.invoke()
              close()
            } catch (e: NumberFormatException) {
              ErrorView("Amount and quantity must be a number").openModal(resizable = false)
            }

          }
        }
        button("Cancel") {

          vboxConstraints {
            marginTop = 10.0
          }

          action {
            close()
          }
        }
      }
    }
  }

  private fun addAction(amount: Int, quantity: Int, saleDate: LocalDate, warehouseName: String) {
    try {
      salesController.addSale(amount, quantity, saleDate, warehouseName)
    } catch (e: java.sql.SQLException) {
      ErrorView("Not enough goods at warehouse").openModal(resizable = false)
    }
  }

  private fun editAction(amount: Int, quantity: Int, saleDate: LocalDate, warehouseName: String) {
    try {
      salesController.editSale(sale.id, amount, quantity, saleDate, warehouseName)
    } catch (e: java.sql.SQLException) {
      ErrorView("Date can not be set retroactively").openModal(resizable = false)
    }
  }
}
