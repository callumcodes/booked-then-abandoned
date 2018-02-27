package uk.gov.hmrc.bookedthenabandoned

import java.io.File
import java.util.Collections

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.http.HttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.calendar.{CalendarScopes}


case class GoogleCredentials(email: String, calendarId: String, p12: File)
object GoogleCredentials

class GoogleCredentialSetup {

  val httpTransport: HttpTransport = new NetHttpTransport()
  val jsonFactory: JsonFactory = new JacksonFactory()

  val (email, p12) = (
    "calendar-service-account@calendar-app-196508.iam.gserviceaccount.com",
    new File("src/main/resources/appygizmo.p12")
  )

  private def credential = {

    val emailAddress: String = email

    new GoogleCredential.Builder()
      .setTransport(httpTransport)
      .setJsonFactory(jsonFactory)
      .setServiceAccountId(emailAddress)
      .setServiceAccountPrivateKeyFromP12File(p12)
      .setServiceAccountScopes(Collections.singleton(CalendarScopes.CALENDAR_READONLY))
      .build()
  }

  def serviceObject: com.google.api.services.calendar.Calendar = {
    new com.google.api.services.calendar.Calendar.Builder(httpTransport, jsonFactory, credential).build()
  }

}

object Calendar {

  val calendar = new GoogleCredentialSetup()
  val calendarId = "appygizmo.com_7h111hmdelregg08nrv17ecnmg@group.calendar.google.com"

  def getCalendar = {
    calendar.serviceObject.calendars.get(calendarId).execute()
  }

  def getEvents = {
    calendar.serviceObject.events().list(calendarId).execute()
  }

  def getEvent(eventId: String) = {
    calendar.serviceObject.events().get(calendarId, eventId).execute()
  }
}