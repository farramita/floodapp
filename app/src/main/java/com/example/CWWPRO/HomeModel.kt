package com.example.CWWPRO

import android.Manifest
import android.content.ContentProviderClient
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.BasemapStyle
import com.example.CWWPRO.Adapter.ModelAdapter
import com.example.CWWPRO.Adapter.ModelViewItem
import com.example.CWWPRO.repository.FirestoreRepository
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.fragment_flood_map.*
import kotlinx.android.synthetic.main.fragment_home_model.*
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeModel.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeModel : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var ModelPager2: ViewPager2
    private var ModelList: MutableList<ModelViewItem> = mutableListOf<ModelViewItem>()
    private var firebaseRepository = FirestoreRepository()
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_model, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener {
            fetchCategoriesDataFromDatabase()
        }

        ModelPager2 = view.findViewById<ViewPager2>(R.id.ModelPager);
        fetchCategoriesDataFromDatabase()
        var etSearchInput = view.findViewById<EditText>(R.id.SearchInput);
        etSearchInput.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (etSearchInput.text.length > 0) {
                    var filteredItem = ModelList.filter {
                        it.modelprovince.toLowerCase().contains(etSearchInput.text.toString().toLowerCase()) or
                                it.modeldis.toLowerCase().contains(etSearchInput.text.toString().toLowerCase())
                    }
                    ModelPager.adapter = ModelAdapter(filteredItem)
                } else {
                    ModelPager.adapter = ModelAdapter(ModelList)
                }
            }
        })
    }


    private fun fetchCategoriesDataFromDatabase() {
        firebaseRepository.getSavedModel().get().addOnSuccessListener { documents ->
            ModelList.clear()
            for (document in documents) {
                ModelList.add(document.toObject(ModelViewItem::class.java))
            }
            ModelPager2.adapter = ModelAdapter(ModelList)
            // hide pull loading
            swipeRefreshLayout.isRefreshing = false
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeModel.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeModel().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}