package model

import at.favre.lib.crypto.bcrypt.BCrypt

class PasswordEncoder {
  fun encode(password: String) : String = BCrypt.withDefaults().hashToString(12, password.toCharArray())

  fun verifyPasswords(password: String, bcryptHashString: String) = BCrypt.verifyer().verify(password.toCharArray(), bcryptHashString).verified
}