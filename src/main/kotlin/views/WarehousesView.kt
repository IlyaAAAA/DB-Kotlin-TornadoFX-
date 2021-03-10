package views

import controllers.WarehousesController
import javafx.geometry.Orientation
import javafx.scene.control.TextField
import model.entity.Warehouse
import tornadofx.*

class WarehousesView(title: String, private val buttonText: String, private val op: () -> Unit) : Fragment(title) {

  constructor(title: String, buttonText: String, warehouse: Warehouse, op: () -> Unit) : this(
    title,
    buttonText,
    op
  ) {
    nameField.text = warehouse.warehouseName
    quantityField.text = warehouse.quantity.toString()
    amountField.text = warehouse.amount.toString()
    this.warehouse = warehouse
  }

  private val warehousesController: WarehousesController by inject()
  private lateinit var warehouse: Warehouse

  private var nameField: TextField by singleAssign()
  private var quantityField: TextField by singleAssign()
  private var amountField: TextField by singleAssign()


  override val root = form {
    fieldset(labelPosition = Orientation.VERTICAL) {
      field("Name") {
        textfield {
          nameField = this
        }
      }
      field("Quantity") {
        textfield {
          quantityField = this
        }
      }
      field("Amount") {
        textfield {
          amountField = this
        }
      }

      vbox {
        button(buttonText) {
          action {
            try {
              when (buttonText) {
                "Add" -> addAction(
                  nameField.text,
                  Integer.parseInt(quantityField.text),
                  Integer.parseInt(amountField.text)
                )
                "Ok" -> editAction(
                  nameField.text,
                  Integer.parseInt(quantityField.text),
                  Integer.parseInt(amountField.text)
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

  private fun addAction(name: String, quantity: Int, amount: Int) {
    warehousesController.addWarehouseItem(name, quantity, amount)
  }

  private fun editAction(name: String, quantity: Int, amount: Int) {
    warehousesController.editWarehouseItem(warehouse.id, name, quantity, amount)
  }
}
