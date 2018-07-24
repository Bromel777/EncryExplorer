package protocol

import crypto._
import scorex.crypto.encode.Base58
import scorex.crypto.signatures.PublicKey
import scala.util.Try

case class Account(address: String) {

  lazy val isValid: Boolean = Base58Check.decode(address).map(bytes =>
    if (bytes.length != PublicKey25519.Length) throw new Exception("Invalid address")
  ).isSuccess

  lazy val pubKey: PublicKey = Account.pubKeyFromAddress(address)
    .getOrElse(throw new Exception("Invalid address"))

  override def toString: String = address
}

object Account {

  val AddressLength: Int = 1 + PublicKey25519.Length + Base58Check.checkSumLength

  def apply(publicKey: PublicKey): Account = Account(Base58Check.encode(publicKey))

  def pubKeyFromAddress(address: String): Try[PublicKey] = Base58Check.decode(address).map(PublicKey @@ _)

  def decodeAddress(address: String): Array[Byte] = Base58.decode(address).get

  def validAddress(address: String): Boolean = Account(address).isValid
}

