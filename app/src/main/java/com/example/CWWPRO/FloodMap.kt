package com.example.CWWPRO

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment
import com.esri.arcgisruntime.geometry.*
import com.esri.arcgisruntime.loadable.LoadStatus
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.Basemap
import com.esri.arcgisruntime.mapping.BasemapStyle
import com.esri.arcgisruntime.mapping.Viewpoint
import com.esri.arcgisruntime.mapping.view.Graphic
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay
import com.esri.arcgisruntime.mapping.view.MapView
import com.esri.arcgisruntime.portal.Portal
import com.esri.arcgisruntime.portal.PortalItem
import com.esri.arcgisruntime.symbology.SimpleFillSymbol
import com.esri.arcgisruntime.symbology.SimpleLineSymbol
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol
import com.example.CWWPRO.Adapter.DetailMapPointViewItem
import com.example.CWWPRO.Adapter.ModelAdapter
import com.example.CWWPRO.Adapter.ModelViewItem
import com.example.CWWPRO.repository.FirestoreRepository
import com.example.foodie.Adapter.DetailMapPointAdapter
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.fragment_home_model.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FloodMap.newInstance] factory method to
 * create an instance of this fragment.
 */
class FloodMap : Fragment() {
    private lateinit var mMapView: MapView

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var latTxt: EditText
    private lateinit var lonTxt: EditText
    private val LOCATION_PREMISSION_REQ_CODE:Int = 111
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var graphicLayer: GraphicsOverlay
    private lateinit var DetailMapPointAdapter: DetailMapPointAdapter
    private var DetailMapPointList: MutableList<DetailMapPointViewItem> = mutableListOf<DetailMapPointViewItem>()
    private var firebaseRepository = FirestoreRepository()
    private lateinit var DetailViewPager2: ViewPager2
    private var isFirst: Boolean = true
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        ArcGISRuntimeEnvironment.setLicense(resources.getString(R.string.arcgis_license_key))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var gStatus:Int = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(activity!!)
        if(gStatus == ConnectionResult.SUCCESS)
        {
            Toast.makeText(activity, "ท่านไม่ได้อยู่ในตำแหน่งพื้นที่ 10 จังหวัด", Toast.LENGTH_SHORT).show()
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity!!)
            manageLocation()
        }
        else
        {
            Toast.makeText(activity, "ท่านไม่ได้อยู่ในตำแหน่งพื้นที่ 10 จังหวัด", Toast.LENGTH_SHORT).show()
        }
        return inflater.inflate(R.layout.fragment_flood_map, container, false)

        }


    fun manageLocation()
    {
        if(ContextCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PREMISSION_REQ_CODE)
        }
        else
        {
            var locationCb = object : LocationCallback(){
                fun OnLocationResult(p0: LocationResult?){
                    super.onLocationResult(p0)
                    var last_location: Location? = p0?.lastLocation
                    if (last_location!=null)
                    {
                        latTxt.setText(last_location.latitude.toString())
                        lonTxt.setText(last_location.longitude.toString())
                    }
                }
            }
            val req = LocationRequest()
            req.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            req.interval = 0
            req.fastestInterval = 0
            fusedLocationProviderClient.requestLocationUpdates(req, locationCb, null)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==LOCATION_PREMISSION_REQ_CODE && permissions.isNotEmpty())
        {
            var granted:Boolean = false
            for(i in permissions.indices)
            {
                if (permissions[i].equals(android.Manifest.permission.ACCESS_FINE_LOCATION)
                    && grantResults[i]== PackageManager.PERMISSION_GRANTED)
                {
                    granted = true
                    manageLocation()
                    break
                }
            }
            if (!granted){
                Toast.makeText(activity, "No Permission to access location!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mMapView = view.findViewById<MapView>(R.id.mapView);
        val latitude = 13.700547
        val longitude = 100.535619
        val levelOfDetail = 15
        val portal = Portal("https://www.arcgis.com", false)
        val itemId = "0bb2947ddcf9476585a19df5b44b5f57"
        val portalItem = PortalItem(portal, itemId)
        val map = ArcGISMap(portalItem)
        mMapView.map = map
        mMapView.isAttributionTextVisible = false


        addGraphics()
        // Create List of Page
        DetailMapPointList = listOf<DetailMapPointViewItem>(
            DetailMapPointViewItem("22 เมษายน 2022", "บางบาล","พระนครศรีอยุธยา", "ไม่ท่วม", "9.9", genShopPoint( 100.46218155035768, 14.40760866722292)),
            DetailMapPointViewItem("22 เมษายน 2022", "เดิมบางนางบวด","สุพรรณบุรี", "ไม่ท่วม", "0.0", genShopPoint(100.08622775383182,14.861787650491328)),
            DetailMapPointViewItem("22 เมษายน 2022", "ไทรน้อย", "นนทบุรี", "ไม่ท่วม", "6.4", genShopPoint(100.31181635567863, 13.978167345634235)),
            DetailMapPointViewItem("22 เมษายน 2022", "แก่งคอย","สระบุรี", "ไม่ท่วม", "4.7", genShopPoint(101.04857283740867,14.565610084101783)),
            DetailMapPointViewItem("22 เมษายน 2022", "บางระจัน", "สิงห์บุรี", "ไม่ท่วม", "6.2", genShopPoint(100.2588728369711, 14.904481213635997)),
            DetailMapPointViewItem("22 เมษายน 2022", "ลำลูกกา","ปทุมธานี", "ไม่ท่วม", "21.9", genShopPoint( 100.78050548771785, 13.981828916387112)),
            DetailMapPointViewItem("22 เมษายน 2022", "ไชโย","อ่างทอง", "ไม่ท่วม", "6.7", genShopPoint(100.47012824682211,14.666108808445815)),
            DetailMapPointViewItem("22 เมษายน 2022", "เมืองอุทัยธานี", "อุทัยธานี", "ไม่ท่วม", "4.3", genShopPoint(100.02455071132277, 15.383812557067252)),
            DetailMapPointViewItem("22 เมษายน 2022", "ท่าวุ้ง", "ลพบุรี", "ไม่ท่วม", "7.4", genShopPoint(100.50696158093409, 14.814994029944755)),
            DetailMapPointViewItem("22 เมษายน 2022", "มโนรมย์", "ชัยนาท", "ไม่ท่วม", "13.6", genShopPoint(100.14774586154141, 15.338852769844944))
        ) as MutableList<DetailMapPointViewItem>
        // find Extent of all point
        val listOfPoint = mutableListOf<Point>()
        DetailMapPointList.forEach {
            listOfPoint.add(it.location)
        }

        DetailViewPager2 = view.findViewById<ViewPager2>(R.id.detailViewPager2);
        createBuffer7km()
        //fetchCategoriesDataFromDatabase()
        DetailMapPointAdapter = DetailMapPointAdapter(DetailMapPointList)
        DetailViewPager2.adapter = DetailMapPointAdapter;
        DetailMapPointAdapter = DetailMapPointAdapter(DetailMapPointList)
        DetailViewPager2 = view.findViewById<ViewPager2>(R.id.detailViewPager2);
        DetailViewPager2.adapter = DetailMapPointAdapter;
        DetailViewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (isFirst) {
                    isFirst = false;
                    addCurrentLocation()
                } else mMapView.setViewpointCenterAsync(DetailMapPointList[position].location, 30000.0)
            }
        })

      /*  var etSearchInput = view.findViewById<EditText>(R.id.floodsearch);
        etSearchInput.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (etSearchInput.text.length > 0) {
                    var filteredItem = DetailMapPointList.filter {
                        it.deM_province.toLowerCase().contains(etSearchInput.text.toString().toLowerCase()) or
                                it.deM_dis.toLowerCase().contains(etSearchInput.text.toString().toLowerCase())
                    }
                    DetailViewPager2.adapter = DetailMapPointAdapter(filteredItem)
                } else {
                    DetailViewPager2.adapter =DetailMapPointAdapter(DetailMapPointList)
                }
            }
        })
  */ }
     private fun fetchCategoriesDataFromDatabase() {
        firebaseRepository.getSavedMap().get().addOnSuccessListener { documents ->
            DetailMapPointList.clear()
            for (document in documents) {
                DetailMapPointList.add(document.toObject(DetailMapPointViewItem::class.java))
            }
            DetailViewPager2.adapter = DetailMapPointAdapter(DetailMapPointList)
            // hide pull loading
            swipeRefreshLayout.isRefreshing = false
        }
    }



    private fun addCurrentLocation() {
        val currentLocationPoint = Point(100.46218155035768, 14.40760866722292, SpatialReferences.getWgs84())
        // create an opaque orange (0xFFFF5733) point symbol with a blue (0xFF0063FF) outline symbol
        val simpleMarkerSymbol = SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.YELLOW, 10f)
        val blueOutlineSymbol = SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.RED, 2f)
        simpleMarkerSymbol.outline = blueOutlineSymbol
        // create a graphic with the point geometry and symbol
        val pointGraphic = Graphic(currentLocationPoint, simpleMarkerSymbol)
        // add the point graphic to the graphics overlay
        graphicLayer.graphics.add(pointGraphic)
        // zoom to current location point
        mMapView.setViewpointCenterAsync(currentLocationPoint, 20000.0)


    }


    private fun createBuffer7km() {
        val currentLocationPoint = Point(100.46218155035768, 14.40760866722292, SpatialReferences.getWgs84())
        // create buffer polygon
        // create buffer geometry 100 meter
        val geometryBuffer = GeometryEngine.bufferGeodetic(currentLocationPoint, 7.0,
            LinearUnit(LinearUnitId.KILOMETERS), Double.NaN, GeodeticCurveType.GEODESIC)

        // create symbol for buffer geometry
        val geodesicOutlineSymbol = SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.BLACK, 2F)
        // 0x4D00FF00 = Green Color with 25% Opacity (4D = 25%)
        val geodesicBufferFillSymbol = SimpleFillSymbol(SimpleFillSymbol.Style.SOLID,
            0x4D00FF00.toInt(), geodesicOutlineSymbol)

        // new graphic
        val graphicBuffer =  Graphic(geometryBuffer, geodesicBufferFillSymbol)
        //graphicLayer.graphics.add(graphicBuffer)

        // filter 7km shop

        // add all filtered shop to map
        DetailMapPointList.forEach {
            genShopPoint(it.location.x, it.location.y, true)
        }
    }

    private fun genShopPoint(x: Double, y: Double, addToMap: Boolean = false): Point {
        // create a point geometry with a location and spatial reference
        // Point(latitude, longitude, spatial reference)
        val point = Point(x, y, SpatialReferences.getWgs84())

        val pointGraphic = Graphic(point)
        // add the point graphic to the graphics overlay
        graphicLayer.graphics.add(pointGraphic)

        return point
    }


    private fun addGraphics() {
        // create a graphics overlay and add it to the map view
        graphicLayer = GraphicsOverlay()
        mMapView.graphicsOverlays.add(graphicLayer)
    }

    override fun onPause() {
        super.onPause()
        mMapView.pause()
    }

    override fun onResume() {
        super.onResume()
        mMapView.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView.dispose()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MapFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FloodMap().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}