package model

import model.db.DataBaseConnection
import model.data.Users
import model.exceptions.IncorrectPasswordOrLoginException
import org.ktorm.database.iterator
import org.ktorm.dsl.*

class Authorization {

  fun logIn(login: String, password: String): String {
    val user = DataBaseConnection.database
      .from(Users)
      .select(Users.name, Users.password)
      .where(Users.name eq login)

    if (user.totalRecords == 0) {
      throw IncorrectPasswordOrLoginException("Incorrect login or password")
    }

    val passHash: String? = getPassHash(user)

    passHash?.let {
      if (!PasswordEncoder().verifyPasswords(password, it)) {
        throw IncorrectPasswordOrLoginException("Incorrect login or password")
      }
    }

    return login
  }


  fun signIn(login: String, password: String) {
    val hashString = PasswordEncoder().encode(password)

    DataBaseConnection.database
      .insert(Users) {
        set(it.name, login)
        set(it.password, hashString)
      }
  }

  private fun getPassHash(user: Query): String? {
    var passHash: String? = null

    for (row in user.rowSet) {
      passHash = row[Users.password]
    }
    return passHash
  }
}