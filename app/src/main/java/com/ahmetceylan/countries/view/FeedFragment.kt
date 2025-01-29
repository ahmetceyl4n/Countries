package com.ahmetceylan.countries.view

import FeedViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmetceylan.countries.adapter.CountryAdapter
import com.ahmetceylan.countries.databinding.FragmentFeedBinding


class FeedFragment : Fragment() {

    private lateinit var viewModel: FeedViewModel
    private val adapter = CountryAdapter(arrayListOf())
    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(FeedViewModel::class.java)
        viewModel.refreshData()

        // RecyclerView Ayarları
        binding.countryList.layoutManager = LinearLayoutManager(context)
        binding.countryList.adapter = adapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.countryList.visibility = View.GONE
            binding.countryError.visibility = View.GONE
            binding.countryLoading.visibility = View.VISIBLE
            viewModel.refreshFromAPI()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.countries.observe(viewLifecycleOwner) { countries ->
            countries?.let {
                binding.countryList.visibility = View.VISIBLE
                adapter.updateCountryList(it)
            }
        }
        viewModel.countryError.observe(viewLifecycleOwner){error ->
            error?.let {
                if (it){
                    binding.countryError.visibility = View.VISIBLE
                    binding.countryList.visibility = View.GONE
                }else{
                    binding.countryError.visibility = View.GONE
                    binding.countryList.visibility = View.VISIBLE
                    binding.countryLoading.visibility = View.GONE
                }
            }

        }

        viewModel.countryLoading.observe(viewLifecycleOwner){load->

            load?.let {
                if (it){
                    binding.countryLoading.visibility = View.VISIBLE
                    binding.countryList.visibility = View.GONE
                    binding.countryError.visibility = View.GONE
                }else{
                    binding.countryLoading.visibility = View.GONE
                    binding.countryList.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
