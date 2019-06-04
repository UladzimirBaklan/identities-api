package com.itechart.identities.model

import play.api.libs.json.{Json, OFormat}

case class Name(id: Long,
                familyName: Option[String],
                givenName: Option[String],
                middleName: Option[String],
                honorificPrefix: Option[String],
                honorificSuffix: Option[String],
                formatted: String)

object Name
  extends ((Long, Option[String], Option[String], Option[String], Option[String], Option[String], String) => Name) {

  implicit val jsonFormat: OFormat[Name] = Json.format[Name]
}

case class UserIdentity(id: String,
                        userName: String,
                        name: Long,
                        displayName: String,
                        nickName: String,
                        profileUrl: String)

object UserIdentity
  extends ((String, String, Long, String, String, String) => UserIdentity) {

  implicit val jsonFormat: OFormat[UserIdentity] = Json.format[UserIdentity]
}

case class Email(id: Long,
                 value: String,
                 emailType: String,
                 primary: Boolean,
                 userIdentity: String)

object Email extends ((Long, String, String, Boolean, String) => Email) {
  implicit val jsonFormat: OFormat[Email] = Json.format[Email]
}

case class Address(id: Long,
                   addressType: String,
                   streetAddress: String,
                   locality: String,
                   region: String,
                   postalCode: Int,
                   country: String,
                   formatted: String,
                   primary: Boolean,
                   userIdentity: String)

object Address extends ((Long, String, String, String, String, Int, String, String, Boolean, String) => Address) {
  implicit val jsonFormat: OFormat[Address] = Json.format[Address]
}

case class PhoneNumber(id: Long, value: String, phoneType: String, userIdentity: String)

object PhoneNumber extends ((Long, String, String, String) => PhoneNumber) {
  implicit val jsonFormat: OFormat[PhoneNumber] = Json.format[PhoneNumber]
}

case class User(userIdentity: UserIdentity, name: Name, emails: Seq[Email], address: Seq[Address], numbers: Seq[PhoneNumber])

