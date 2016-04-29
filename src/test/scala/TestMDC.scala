
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class TestMDC extends Simulation {

	val httpProtocol = http
		.baseURL("https://mdc-performance.kingsmensoftware.com")
		.inferHtmlResources()
		.acceptHeader("application/json, text/plain, */*")
		.acceptEncodingHeader("gzip, deflate, br")
		.acceptLanguageHeader("en-US,en;q=0.5")
		.userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.11; rv:45.0) Gecko/20100101 Firefox/45.0")

	val headers_0 = Map("Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")

	val headers_1 = Map("Accept" -> "image/png,image/*;q=0.8,*/*;q=0.5")

	val headers_3 = Map(
		"Accept" -> "application/json, text/javascript, */*; q=0.01",
		"X-Requested-With" -> "XMLHttpRequest")

	val headers_9 = Map(
		"Accept" -> "application/font-woff2;q=1.0,application/font-woff;q=0.9,*/*;q=0.8",
		"Accept-Encoding" -> "identity")

	val headers_19 = Map(
		"Accept" -> "*/*",
		"X-Requested-With" -> "XMLHttpRequest")

    val uri1 = "https://mdc-performance.kingsmensoftware.com:443"

	val scn = scenario("TestMDC")
		// Site Load
		.exec(http("Site Load")
			.get("/")
			.headers(headers_0)
			.resources(http("Bkgrnd PNG")
			.get(uri1 + "/AppCompiled/Content/images/mybg.png")
			.headers(headers_1),
			// .check(status.is(304))
            http("Pattern PNG")
			.get(uri1 + "/AppCompiled/Content/images/pattern/overlay-pattern.png")
			.headers(headers_1),
			// .check(status.is(304))
            http("SecurityAssertion")
			.get(uri1 + "/dataapi/data/SecurityAssertion")
			.headers(headers_3),
            http("ConfigurationItems")
			.get(uri1 + "/dataapi/data/ConfigurationItems?$orderby=Value&"),
            http("Dashboard request")
			.get(uri1 + "/dataapi/data/Dashboards?$filter=(ApplicationUser_Id%20eq%20guid%27626ca629-ab51-4be5-ab18-eef82bfcc70d%27)%20and%20((IsActive%20eq%20true)%20and%20(IsDeleted%20eq%20false))&$orderby=Title&"),
            http("Messages request")
			.get(uri1 + "/dataapi/data/Messages?$filter=((((MessageType_Id%20eq%20guid%27bf7fbdb3-c127-4fae-8d56-cf79894d41b2%27)%20and%20(DisplayType_Id%20eq%20guid%27cf68c43f-8e3c-4829-8a5a-f9f6119d69a0%27))%20and%20(StartDate%20le%20datetime%272016-04-26T20%3A39%3A23.732Z%27))%20and%20(EndDate%20ge%20datetime%272016-04-26T20%3A39%3A23.732Z%27))%20and%20((IsActive%20eq%20true)%20and%20(IsDeleted%20eq%20false))&$orderby=StartDate&$select=Id%2CTitle%2CSubject%2CFrom%2CTo%2CBody%2CStartDate%2CEndDate&"),
            http("Logo PNG")
			.get(uri1 + "/AppCompiled/Content/images/wf-logo_sm.png")
			.headers(headers_1),
			// .check(status.is(304))
            http("NavigationPanel")
			.get(uri1 + "/PageComponents/NavigationPanel"),
            http("Awesome webfont")
			.get(uri1 + "/AppCompiled/fonts/fontawesome-webfont.woff2?v=4.4.0")
			.headers(headers_9)
			.check(status.is(404)),
            http("Glyphicon")
			.get(uri1 + "/AppCompiled/fonts/glyphicons-halflings-regular.woff2")
			.headers(headers_9)
			.check(status.is(404))))
		.pause(2)
		// Click Equities
		.exec(http("Equities WidgetDataSources")
			.get("/dataapi/data/WidgetDataSources?$filter=Id%20eq%20guid%2774818ed2-5f09-e611-82d5-002170ba297c%27&$expand=Fields&")
			.resources(http("sprite png")
			.get(uri1 + "/AppCompiled/Content/Default/sprite.png")
			.headers(headers_1),
			// .check(status.is(304))
            http("loading image gif")
			.get(uri1 + "/AppCompiled/Content/Default/loading-image.gif")
			.headers(headers_1),
			// .check(status.is(304))
            http("data request Equities")
			.get(uri1 + "/api/DynamicData?%24format=json&DataSourceId=74818ed2-5f09-e611-82d5-002170ba297c&%24top=25&%24orderby=Name&%24count=true")
			.headers(headers_3)))
		.pause(2)
		// Click ETFs
		.exec(http("ETFs WidgetDataSources")
			.get("/dataapi/data/WidgetDataSources?$filter=Id%20eq%20guid%27a86be5f3-a909-e611-82d6-002170ba297c%27&$expand=Fields&")
			.resources(http("ETFs DynamicData")
			.get(uri1 + "/api/DynamicData?%24format=json&DataSourceId=a86be5f3-a909-e611-82d6-002170ba297c&%24top=25&%24orderby=Name&%24count=true")
			.headers(headers_3)))
		.pause(2)
		// Click Mutual Funds
		.exec(http("Mutual Funds WidgetDataSources")
			.get("/dataapi/data/WidgetDataSources?$filter=Id%20eq%20guid%27bad5139b-470a-e611-82d6-002170ba297c%27&$expand=Fields&")
			.resources(http("Mutual Funds DynamicData")
			.get(uri1 + "/api/DynamicData?%24format=json&DataSourceId=bad5139b-470a-e611-82d6-002170ba297c&%24top=25&%24orderby=Name&%24count=true")
			.headers(headers_3)))
		.pause(4)
		// Search Ticker
		.exec(http("Enter W")
			.get("/api/Securities/GetCount?searchTerm=W")
			.headers(headers_19)
			.resources(http("Return W")
			.get(uri1 + "/api/Securities/GetAutoComplete?searchTerm=W")
			.headers(headers_19),
            http("Enter WF")
			.get(uri1 + "/api/Securities/GetCount?searchTerm=WF")
			.headers(headers_19),
            http("Return WF")
			.get(uri1 + "/api/Securities/GetAutoComplete?searchTerm=WF")
			.headers(headers_19),
            http("Enter WFC")
			.get(uri1 + "/api/Securities/GetCount?searchTerm=WFC")
			.headers(headers_19),
            http("Return WFC")
			.get(uri1 + "/api/Securities/GetAutoComplete?searchTerm=WFC")
			.headers(headers_19)))
		.pause(1)
		.exec(http("Click WFC in Dropdown")
			.get("/dataapi/data/Dashboards?$filter=(ApplicationUser_Id%20eq%20guid%27626ca629-ab51-4be5-ab18-eef82bfcc70d%27)%20and%20((IsActive%20eq%20true)%20and%20(IsDeleted%20eq%20false))&$orderby=Title&"))

		
		setUp(scn.inject(atOnceUsers(1)))
			// setUp(scn.inject(constantUsersPerSec(1) during(1 minute)))
			// setUp(scn.inject(rampUsers(400) over(5 seconds)))
			.protocols(httpProtocol)
}


