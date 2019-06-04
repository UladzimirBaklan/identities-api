package com.itechart.identities.model.dao

import com.google.inject.{Inject, Singleton}
import com.itechart.identities.model.{Address, Email, Name, PhoneNumber, UserIdentity}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.lifted.TableQuery

import scala.concurrent.ExecutionContext

@Singleton
class NameDao @Inject()(configProvider: DatabaseConfigProvider) {
  val profile: JdbcProfile = configProvider.get.profile
  private lazy val TABLE_NAME = "name"
  private lazy val nameQuery = TableQuery[NameTable]

  import profile.api._

  private class NameTable(tag: Tag) extends Table[Name](tag, TABLE_NAME) {
    val id = column[Long]("id")
    val familyName = column[Option[String]]("family_name")
    val givenName = column[Option[String]]("given_name")
    val middleName = column[Option[String]]("middle_name")
    val honorificPrefix = column[Option[String]]("honorific_prefix")
    val honorificSuffix = column[Option[String]]("honorific_suffix")
    val formatted = column[String]("formatted")

    def * = (id, familyName, givenName, middleName, honorificPrefix, honorificSuffix, formatted) <> (Name.tupled, Name.unapply)
  }

  def all(): DBIO[Seq[Name]] = nameQuery.result

  def get(id: Long): DBIO[Option[Name]] = nameQuery.filter(_.id === id).result.headOption

  def getAllWithId(id: Long): DBIO[Seq[Name]] = nameQuery.filter(_.id === id).result
}

class EmailDao @Inject()(configProvider: DatabaseConfigProvider) {
  val profile: JdbcProfile = configProvider.get.profile
  private lazy val TABLE_NAME = "email"
  private lazy val emailQuery = TableQuery[EmailTable]

  import profile.api._
  private class EmailTable(tag: Tag) extends Table[Email](tag, TABLE_NAME) {
    val id = column[Long]("id")
    val value = column[String]("value")
    val emailType = column[String]("type")
    val primary = column[Boolean]("is_primary")
    val userIdentity = column[String]("user_identity_fk")

    def * = (id, value, emailType, primary, userIdentity) <> (Email.tupled, Email.unapply)
  }

  def all(): DBIO[Seq[Email]] = emailQuery.result

  def get(id: Long): DBIO[Option[Email]] = emailQuery.filter(_.id === id).result.headOption

  def getAllWithId(id: Long): DBIO[Seq[Email]] = emailQuery.filter(_.id === id).result
}

class UserIdentityDao @Inject()(configProvider: DatabaseConfigProvider) {
  val profile: JdbcProfile = configProvider.get.profile
  private lazy val TABLE_NAME = "user_identity"
  private lazy val userIdentityQuery = TableQuery[UserIdentityTable]

  import profile.api._
  private class UserIdentityTable(tag: Tag) extends Table[UserIdentity](tag, TABLE_NAME) {
    val id = column[String]("id")
    val userName = column[String]("user_name")
    val name = column[Long]("name")
    val displayName = column[String]("display_name")
    val nickName = column[String]("nick_name")
    val profileUrl = column[String]("profile_url")

    def * = (id, userName, name, displayName, nickName, profileUrl) <> (UserIdentity.tupled, UserIdentity.unapply)
  }

  def all(): DBIO[Seq[UserIdentity]] = userIdentityQuery.result

  def get(id: String): DBIO[Option[UserIdentity]] = userIdentityQuery.filter(_.id === id).result.headOption

  def getAllWithId(id: String): DBIO[Seq[UserIdentity]] = userIdentityQuery.filter(_.id === id).result
}

class AddressDao @Inject()(configProvider: DatabaseConfigProvider) {
  val profile: JdbcProfile = configProvider.get.profile
  private lazy val TABLE_NAME = "address"
  private lazy val addressQuery = TableQuery[AddressTable]

  import profile.api._
  private class AddressTable(tag: Tag) extends Table[Address](tag, TABLE_NAME) {
    val id = column[Long]("id")
    val addressType = column[String]("type")
    val streetAddress = column[String]("street_address")
    val locality = column[String]("locality")
    val region = column[String]("region")
    val postalCode = column[Int]("postal_code")
    val country = column[String]("country")
    val formatted = column[String]("formatted")
    val primary = column[Boolean]("is_primary")
    val userIdentity = column[String]("user_identity_fk")

    def * = (id, addressType, streetAddress, locality,
      region, postalCode, country, formatted, primary, userIdentity) <> (Address.tupled, Address.unapply)
  }

  def all(): DBIO[Seq[Address]] = addressQuery.result

  def get(id: Long): DBIO[Option[Address]] = addressQuery.filter(_.id === id).result.headOption

  def getAllWithId(id: Long): DBIO[Seq[Address]] = addressQuery.filter(_.id === id).result
}

class PhoneNumberDao @Inject()(configProvider: DatabaseConfigProvider) {
  val profile: JdbcProfile = configProvider.get.profile
  private lazy val TABLE_NAME = "phone_number"
  private lazy val phoneNumberQuery = TableQuery[PhoneNumberTable]

  import profile.api._
  private class PhoneNumberTable(tag: Tag) extends Table[PhoneNumber](tag, TABLE_NAME) {
    val id = column[Long]("id")
    val value = column[String]("value")
    val phoneType = column[String]("type")
    val userIdentity = column[String]("user_identity_fk")

    def * = (id, value, phoneType, userIdentity) <> (PhoneNumber.tupled, PhoneNumber.unapply)
  }

  def all(): DBIO[Seq[PhoneNumber]] = phoneNumberQuery.result

  def get(id: Long): DBIO[Option[PhoneNumber]] = phoneNumberQuery.filter(_.id === id).result.headOption

  def getAllWithId(id: Long): DBIO[Seq[PhoneNumber]] = phoneNumberQuery.filter(_.id === id).result
}

class UserDao @Inject()(configProvider: DatabaseConfigProvider,
                        nameDao: NameDao,
                        emailDao: EmailDao,
                        userIdentityDao: UserIdentityDao,
                        addressDao: AddressDao,
                        phoneNumberDao: PhoneNumberDao) (implicit ec: ExecutionContext) {

}