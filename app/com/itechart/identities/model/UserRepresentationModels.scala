package com.itechart.identities.model

import play.api.libs.json.Json

case class Email(value: String, emailType: String, primary: Boolean)

object Email extends ((String, String, Boolean) => Email) {
  implicit def emailDatabaseToEmail(dbEmail: EmailDatabase): Email = {
    Email(dbEmail.value, dbEmail.emailType, dbEmail.primary)
  }

  implicit def jsonFormat = Json.format[Email]
}

case class Address(addrType: String, streetAddress: String, locality: String, region: String, postalCode: Int, country: String, formatted: String, primary: Boolean)

object Address extends ((String, String, String, String, Int, String, String, Boolean) => Address) {
  implicit def addressDatabaseToAddress(dbAddress: AddressDatabase): Address = {
    Address(dbAddress.addressType,
      dbAddress.streetAddress,
      dbAddress.locality,
      dbAddress.region,
      dbAddress.postalCode,
      dbAddress.country,
      dbAddress.formatted,
      dbAddress.primary)
  }

  implicit def jsonFormat = Json.format[Address]
}

case class PhoneNumber(value: String, numberType: String)

object PhoneNumber extends ((String, String) => PhoneNumber) {
  implicit def phoneNumberDatabaseToPhoneNumber(dbPhone: PhoneNumberDatabase): PhoneNumber = {
    PhoneNumber(dbPhone.value, dbPhone.phoneType)
  }

  implicit def jsonFormat = Json.format[PhoneNumber]
}

case class User(id: String,
                externalId: String,
                userName: String,
                displayName: String,
                nickName: String,
                profileUrl: String,
                familyName: String,
                givenName: String,
                middleName: String,
                honorificPrefix: String,
                honorificSuffix: String,
                emails: Seq[Email],
                addresses: Seq[Address],
                phoneNumber: Seq[PhoneNumber])

object User extends ((String, String, String, String, String,
  String, String, String, String, String, String, Seq[Email], Seq[Address], Seq[PhoneNumber]) => User) {

  def apply(databaseData: (UserIdentityDatabase,EmailDatabase,PhoneNumberDatabase, AddressDatabase)): User = {
    val(userIdentity, email, phoneNumber, address) = databaseData

    User(userIdentity.id,
      "",
      userIdentity.userName,
      userIdentity.displayName,
      userIdentity.nickName,
      userIdentity.profileUrl,
      userIdentity.familyName.getOrElse(""),
      userIdentity.givenName.getOrElse(""),
      userIdentity.middleName.getOrElse(""),
      userIdentity.honorificPrefix.getOrElse(""),
      userIdentity.honorificSuffix.getOrElse(""),
      Seq(email),
      Seq(address),
      Seq(phoneNumber))
  }

  implicit def jsonFormat = Json.format[User]
}
