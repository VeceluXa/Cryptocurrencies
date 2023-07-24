package com.danilovfa.cryptocurrencies.app.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.core.content.ContextCompat.getColor
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.danilovfa.cryptocurrencies.R
import com.danilovfa.cryptocurrencies.app.viewmodel.DetailsViewModel
import com.danilovfa.cryptocurrencies.databinding.FragmentDetailsBinding
import com.danilovfa.cryptocurrencies.domain.model.ChartTimestamp
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrencyChart
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrencyDetails
import com.danilovfa.cryptocurrencies.utils.extension.showTextDialog
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit
import kotlin.math.abs

class DetailsFragment : BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {
    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel: DetailsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarHideTitle()
        toolbarShowBackButton()
        setTransition()

        viewModel.getDetails(args.coinId)
        observeResponses()
    }

    private fun setTransition() {
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        postponeEnterTransition(250, TimeUnit.MILLISECONDS)
    }

    private fun observeResponses() {
        lifecycleScope.launch {
            viewModel.apply {
                launch {
                    details.collectLatest { details ->
                        hideProgressBar()
                        if (details != null)
                            setupUiData(details)
                    }
                }
                launch {
                    errorMessage.collectLatest { errorMessage ->
                        hideProgressBar()
                        if (errorMessage.isNotEmpty())
                            requireContext().showTextDialog(R.string.error, errorMessage) {
                                findNavController().navigateUp()
                            }
                    }
                }
                launch {
                    isLoading.collectLatest { isLoading ->
                        if (isLoading)
                            showProgressBar()
                        else
                            hideProgressBar()
                    }
                }
            }
        }
    }

    private fun hideProgressBar() {
        binding.apply {
            detailsLayout.visibility = View.VISIBLE
            detailsProgressBar.visibility = View.GONE
        }
    }

    private fun showProgressBar() {
        binding.apply {
            detailsLayout.visibility = View.GONE
            detailsProgressBar.visibility = View.VISIBLE
        }
    }

    private fun setupUiData(data: CryptocurrencyDetails) {
        binding.apply {
            priceTextView.text = priceToString(data.coinDetails.price)
            priceTextView.transitionName = data.coinDetails.id
            marketCapTextView.text = suffixPriceToString(data.coinDetails.marketCap)
            toolbarShowTitle(data.coinDetails.name)
            addIconToToolbar(data.coinDetails.imageUrl)
            setPriceChangedPercentageText(data.coinDetails.priceChanged24hPercentage)
            setupRadioButtonsCharts(data.charts)
        }
    }

    private fun setupRadioButtonsCharts(charts: CryptocurrencyChart) {
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioButton24h -> displayChart(charts.lastDay)
                R.id.radioButton1w -> displayChart(charts.lastWeek)
                R.id.radioButton1m -> displayChart(charts.lastMonth)
                R.id.radioButton1y -> displayChart(charts.lastYear)
                else -> displayChart(charts.lastMax)
            }
        }
        binding.radioGroup.clearCheck()
        binding.radioGroup.check(R.id.radioButton24h)
    }

    private fun displayChart(chart: List<ChartTimestamp>) {
        val min = chart.minBy { it.price }
        val max = chart.maxBy { it.price }

        binding.apply {
            chartPriceLowTextView.text = suffixPriceToString(min.price)
            chartPriceMaxTextView.text = suffixPriceToString(max.price)
        }

        configureLineChart()
        setLineChartData(chart)
    }

    private fun configureLineChart() {
        binding.chart.apply {
            invalidate()
            setTouchEnabled(false)
            setViewPortOffsets(0f, 0f, 0f, 0f)
            description.isEnabled = false
            xAxis.setDrawGridLines(false)
            xAxis.setDrawAxisLine(false)
            xAxis.setDrawLabels(false)
            axisLeft.isEnabled = false
            axisRight.isEnabled = false
            legend.isEnabled = false
            setDrawGridBackground(false)
        }
    }

    private fun setLineChartData(data: List<ChartTimestamp>) {
        val entries = data.convertToDataEntries()
        val dataSet = LineDataSet(entries, "")
        dataSet.apply {
            setDrawValues(false)
            setDrawCircles(false)
            lineWidth = 2f
            mode = LineDataSet.Mode.HORIZONTAL_BEZIER
            color = getColor(requireContext(), R.color.yellow)
        }

        val lineData = LineData(dataSet)
        binding.chart.data = lineData
    }

    private fun List<ChartTimestamp>.convertToDataEntries(): List<Entry> {
        val entries = mutableListOf<Entry>()
        for (chartTimestamp in this) {
            entries.add(
                Entry(
                    chartTimestamp.epochTimestamp.toFloat(),
                    chartTimestamp.price.toFloat()
                )
            )
        }
        return entries
    }

    private fun setPriceChangedPercentageText(priceChangedPercentage: Double) {
        binding.priceChangedPercentageTextView.apply {
            if (priceChangedPercentage >= 0)
                setTextColor(getColor(requireContext(), R.color.green))
            else
                setTextColor(getColor(requireContext(), R.color.pink))

            text = priceChangedToString(priceChangedPercentage)
        }
    }

    private fun priceChangedToString(number: Double): String {
        var formattedNumber = String.format("%.${if (number == 0.0) 0 else 2}f", abs(number))
        val indexOfDecimal = formattedNumber.indexOf('.')
        formattedNumber = if (indexOfDecimal >= 0) {
            val trailingZeros = formattedNumber.substring(indexOfDecimal + 1).count { it == '0' }
            if (trailingZeros > 2) {
                String.format("%.${trailingZeros}f", number)
            } else {
                formattedNumber
            }
        } else {
            formattedNumber
        }

        return if (number >= 0)
            "+$formattedNumber %"
        else
            "-$formattedNumber %"
    }

    private fun addIconToToolbar(url: String) {
        Glide
            .with(requireActivity())
            .asDrawable()
            .load(url)
            .circleCrop()
            .placeholder(R.mipmap.ic_launcher_round)
            .override(60, 60)
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    toolbarShowIcon(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }

            })
    }

    private fun priceToString(price: Double): String {
        return "$price $"
    }

    private fun suffixPriceToString(price: Double): String {
        val suffixes = listOf("", "K", "M", "B", "T")
        var num = price
        var suffixIndex = 0

        while (num >= 1000 && suffixIndex < suffixes.lastIndex) {
            num /= 1000
            suffixIndex++
        }

        val formattedString = String.format("%.2f", num)
        val suffix = suffixes[suffixIndex]

        return "\$ $formattedString $suffix"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        toolbarHideIcon()
        toolbarHideBackButton()
    }
}