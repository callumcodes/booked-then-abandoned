package uk.gov.hmrc.bookedthenabandoned

case class Event(
                  items: String
                )

//implicit val decoder = jsonOf[IO, Event]