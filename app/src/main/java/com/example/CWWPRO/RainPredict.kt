package com.example.CWWPRO

import android.os.Bundle
import android.text.BoringLayout.make
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.esri.arcgisruntime.internal.jni.it
import com.example.CWWPRO.Adapter.ModelAdapter
import com.example.CWWPRO.Adapter.RainAdapter
import com.example.CWWPRO.Adapter.RainViewItem
import com.example.CWWPRO.repository.FirestoreRepository
import com.google.android.material.snackbar.Snackbar.make
import kotlinx.android.synthetic.main.fragment_home_model.*
import kotlinx.android.synthetic.main.fragment_rain_predict.*
import kotlinx.android.synthetic.main.rain_template.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RainPredict.newInstance] factory method to
 * create an instance of this fragment.
 */
class RainPredict : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var RainPager2: ViewPager2
    private var RainList: MutableList<RainViewItem> = mutableListOf<RainViewItem>()
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
        return inflater.inflate(R.layout.fragment_rain_predict, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener {
            fetchCategoriesDataFromDatabase()
        }

        RainPager2 = view.findViewById<ViewPager2>(R.id.rainPager);
        fetchCategoriesDataFromDatabase()
        var etSearchInput = view.findViewById<EditText>(R.id.RainSearchInput);
        etSearchInput.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (etSearchInput.text.length > 0) {
                    var filteredItem = RainList.filter {
                        it.province.toLowerCase().contains(etSearchInput.text.toString().toLowerCase())
                    }
                    RainPager2.adapter = RainAdapter(filteredItem)
                } else {
                    RainPager2.adapter = RainAdapter(RainList)
                }
            }
        })
    }


    private fun fetchCategoriesDataFromDatabase() {
        firebaseRepository.getSavedRain().get().addOnSuccessListener { documents ->
            RainList.clear()
            for (document in documents) {
                RainList.add(document.toObject(RainViewItem::class.java))
            }
            RainPager2.adapter = RainAdapter(RainList)
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
         * @return A new instance of fragment RainPredict.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RainPredict().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}