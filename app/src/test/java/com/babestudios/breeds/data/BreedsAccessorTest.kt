package com.babestudios.breeds.data

import com.babestudios.breeds.data.db.BreedsDataSource
import com.babestudios.breeds.data.network.BreedsService
import com.github.michaelbull.result.Err
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BreedsAccessorTest {

	private val mockWebServer = MockWebServer()

	private fun success() {
		mockWebServer.enqueue(
			MockResponse()
				.setResponseCode(200)
				.setBody(allBreedsResponse)
		)
	}

	private fun failure() {
		mockWebServer.enqueue(
			MockResponse()
				.setResponseCode(500)
				.setBody("")
		)
	}

	private lateinit var service: BreedsService
	private lateinit var accessor: BreedsAccessor
	private val breedsDataSource = mockk<BreedsDataSource>()

	@Before
	fun setup() {

		every { breedsDataSource.getBreeds() } returns emptyList()

		val url = mockWebServer.url("/")

		service = Retrofit.Builder()
			.baseUrl(url)
			.addConverterFactory(GsonConverterFactory.create())
			.build().create(BreedsService::class.java)

		accessor = BreedsAccessor(service, breedsDataSource)
	}

	@After
	fun teardown() {
		mockWebServer.shutdown()
	}

	@Test
	fun `when response is success then response is not null`() {
		success()
		runBlocking {
			val apiResponse = service.getBreeds()
			apiResponse shouldNotBe null
		}
	}

	@Test
	fun `when response is error then response is null`() {
		failure()
		runBlocking {
			val apiResponse = accessor.getAllBreeds()
			apiResponse shouldNotBe null
			apiResponse shouldBeEqualToComparingFields Err(
				HttpException(
					Response.error<String>(
						"{\"message\":[\"Server Error\"]}"
							.toResponseBody("application/json".toMediaTypeOrNull()),
						okhttp3.Response.Builder()
							.request(Request.Builder().url("http://localhost/").build())
							.protocol(Protocol.HTTP_1_1)
							.message("Server Error").code(500).build()
					)
				)
			)
		}
	}

}

val allBreedsResponse = """
	{
		"message":
			{
				"affenpinscher":[],
				"african":[],
				"airedale":[],
				"akita":[],
				"appenzeller":[],
				"australian":["shepherd"],
				"basenji":[],
				"beagle":[],
				"bluetick":[],
				"borzoi":[],
				"bouvier":[],
				"boxer":[],
				"brabancon":[],
				"briard":[],
				"buhund":["norwegian"],
				"bulldog":["boston","english","french"],
				"bullterrier":["staffordshire"],
				"cattledog":["australian"],
				"chihuahua":[],
				"chow":[],
				"clumber":[],
				"cockapoo":[],
				"collie":["border"],
				"coonhound":[],
				"corgi":["cardigan"],
				"cotondetulear":[],
				"dachshund":[],
				"dalmatian":[],
				"dane":["great"],
				"deerhound":["scottish"],
				"dhole":[],"dingo":[],"doberman":[],
				"elkhound":["norwegian"],
				"entlebucher":[],"eskimo":[],
				"finnish":["lapphund"],
				"frise":["bichon"],
				"germanshepherd":[],
				"greyhound":["italian"],
				"groenendael":[],
				"havanese":[],
				"hound":["afghan","basset","blood","english","ibizan","plott","walker"],
				"husky":[],"keeshond":[],"kelpie":[],"komondor":[],"kuvasz":[],"labradoodle":[],"labrador":[],"leonberg":[],
				"lhasa":[],"malamute":[],"malinois":[],"maltese":[],
				"mastiff":["bull","english","tibetan"],
				"mexicanhairless":[],
				"mix":[],
				"mountain":["bernese","swiss"],
				"newfoundland":[],"otterhound":[],
				"ovcharka":["caucasian"],
				"papillon":[],"pekinese":[],"pembroke":[],
				"pinscher":["miniature"],
				"pitbull":[],
				"pointer":["german","germanlonghair"],
				"pomeranian":[],
				"poodle":["medium","miniature","standard","toy"],
				"pug":[],"puggle":[],"pyrenees":[],"redbone":[],
				"retriever":["chesapeake","curly","flatcoated","golden"],
				"ridgeback":["rhodesian"],
				"rottweiler":[],"saluki":[],"samoyed":[],"schipperke":[],
				"schnauzer":["giant","miniature"],
				"segugio":["italian"],
				"setter":["english","gordon","irish"],
				"sharpei":[],
				"sheepdog":["english","shetland"],
				"shiba":[],"shihtzu":[],
				"spaniel":["blenheim","brittany","cocker","irish","japanese","sussex","welsh"],
				"spitz":["japanese"],
				"springer":["english"],
				"stbernard":[],
				"terrier":["american","australian","bedlington","border","cairn","dandie","fox","irish","kerryblue","lakeland","norfolk","norwich","patterdale","russell","scottish","sealyham","silky","tibetan","toy","welsh","westhighland","wheaten","yorkshire"],
				"tervuren":[],"vizsla":[],
				"waterdog":["spanish"],
				"weimaraner":[],"whippet":[],
				"wolfhound":["irish"]
			},
		"status":"success"
	}""".trimIndent()
