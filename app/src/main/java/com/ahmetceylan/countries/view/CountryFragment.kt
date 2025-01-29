package com.ahmetceylan.countries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ahmetceylan.countries.databinding.FragmentCountryBinding
import com.ahmetceylan.countries.util.downloadFromUrl
import com.ahmetceylan.countries.util.placeholderProgressBar
import com.ahmetceylan.countries.viewmodel.CountryViewModel

class CountryFragment : Fragment() {

    private var countryUuid = 0
    private var _binding: FragmentCountryBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel :CountryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCountryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            countryUuid = CountryFragmentArgs.fromBundle(it).CountryUuid
        }

        viewModel = ViewModelProvider(this).get(CountryViewModel::class.java)
        viewModel.getDataFromRoom(countryUuid)


        observeLiveData()
    }

    fun observeLiveData(){
        viewModel.countryLiveData.observe(viewLifecycleOwner){ country ->
            country?.let {

                binding.selectedCountry = it

                /*
                binding.countryName.text= country.countryName
                binding.countryCapital.text = country.countryCapital
                binding.countryCurrency.text = country.countryCurrency
                binding.countryLanguage.text = country.countryLanguage
                binding.countryRegion.text = country.countryRegion
                country.imageUrl?.let { url ->
                    context?.let { context -> placeholderProgressBar(context) }?.let { circularProgress ->
                        binding.countryImage.downloadFromUrl(url,circularProgress)
                    }
                }
                */
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
