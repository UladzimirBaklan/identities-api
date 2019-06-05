package com.itechart.identities.model.dao

import com.google.inject.{Inject, Singleton}
import com.itechart.identities.model._
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.lifted

@Singleton
class UserMapping @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) {
  val profile: JdbcProfile = dbConfigProvider.get.profile
  private lazy val USER_IDENTITY_TABLE_NAME = "user_identity"
  private lazy val EMAIL_TABLE_NAME = "email"
  private lazy val ADDRESS_TABLE_NAME = "address"
  private lazy val PHONE_NUMBER_TABLE_NAME = "phone_number"
  import profile.api._

  lazy val userIdentityQuery = lifted.TableQuery[UserIdentityTable]
  lazy val emailQuery = lifted.TableQuery[EmailTable]
  lazy val addressQuery = lifted.TableQuery[AddressTable]
  lazy val phoneNumberQuery = lifted.TableQuery[PhoneNumberTable]

  class UserIdentityTable(tag: Tag) extends Table[UserIdentityDatabase](tag, USER_IDENTITY_TABLE_NAME) {
    val id = column[String]("id", O.PrimaryKey)
    val userName = column[String]("user_name")
    val displayName = column[String]("display_name")
    val nickName = column[String]("nick_name")
    val profileUrl = column[String]("profile_url")
    val familyName = column[Option[String]]("family_name")
    val givenName = column[Option[String]]("given_name")
    val middleName = column[Option[String]]("middle_name")
    val honorificPrefix = column[Option[String]]("honorific_prefix")
    val honorificSuffix = column[Option[String]]("honorific_suffix")
    val formatted = column[String]("formatted")

    def * = (id, userName, displayName, nickName,
      profileUrl, familyName, givenName, middleName, honorificPrefix, honorificSuffix, formatted) <>
      (UserIdentityDatabase.tupled, UserIdentityDatabase.unapply)
  }

  class EmailTable(tag: Tag) extends Table[EmailDatabase](tag, EMAIL_TABLE_NAME) {
    val id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    val value = column[String]("value")
    val emailType = column[String]("type")
    val primary = column[Boolean]("is_primary")
    val userIdentity = column[String]("user_identity_fk")

    def * = (id, value, emailType, primary, userIdentity) <> (EmailDatabase.tupled, EmailDatabase.unapply)
  }

  class AddressTable(tag: Tag) extends Table[AddressDatabase](tag, ADDRESS_TABLE_NAME) {
    val id = column[Long]("id", O.PrimaryKey, O.AutoInc)
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
      region, postalCode, country, formatted, primary, userIdentity) <> (AddressDatabase.tupled, AddressDatabase.unapply)
  }

  class PhoneNumberTable(tag: Tag) extends Table[PhoneNumberDatabase](tag, PHONE_NUMBER_TABLE_NAME) {
    val id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    val value = column[String]("value")
    val phoneType = column[String]("type")
    val userIdentity = column[String]("user_identity_fk")

    def * = (id, value, phoneType, userIdentity) <> (PhoneNumberDatabase.tupled, PhoneNumberDatabase.unapply)
  }
}
