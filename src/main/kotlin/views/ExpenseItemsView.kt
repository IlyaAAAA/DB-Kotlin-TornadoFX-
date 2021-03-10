package views

import controllers.ExpenseItemController
import javafx.geometry.Orientation
import javafx.scene.control.Button
import javafx.scene.control.TextField
import model.entity.ExpenseItem
import tornadofx.*
import java.sql.SQLIntegrityConstraintViolationException

class ExpenseItemsView(title: String, private val buttonText: String, private val op: () -> Unit) : Fragment(title) {

  constructor(title: String, buttonText: String, expenseItem: ExpenseItem, op: () -> Unit) : this(
    title,
    buttonText,
    op
  ) {
    nameField.text = expenseItem.itemName
    this.expenseItem = expenseItem
  }

  private val expenseItemController: ExpenseItemController by inject()
  private lateinit var expenseItem: ExpenseItem

  private var nameField: TextField by singleAssign()
  private var button: Button by singleAssign()

  override val root = form {
    fieldset(labelPosition = Orientation.VERTICAL) {
      field("Name") {
        textfield {
          nameField = this
        }
      }

      vbox {
        button(buttonText) {
          button = this
          action {
            try {
              when (button.text) {
                "Add" -> addAction(nameField.text)
                "Ok" -> editAction(nameField.text)
              }
              op.invoke()
              close()
            } catch (e: SQLIntegrityConstraintViolationException) {
              ErrorView("This name is already used").openModal(resizable = false)
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

  private fun addAction(name: String) {
    expenseItemController.addExpenseItem(name)
  }

  private fun editAction(name: String) {
    expenseItemController.editExpenseItem(expenseItem.id, name)
  }
}
