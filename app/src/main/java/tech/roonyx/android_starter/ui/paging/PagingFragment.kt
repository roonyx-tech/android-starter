package tech.roonyx.android_starter.ui.paging

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.roonyx.android_starter.R
import tech.roonyx.android_starter.databinding.FragmentPagingBinding
import tech.roonyx.android_starter.extension.viewBinding

/**
 * Example of using Paging
 */
class PagingFragment : Fragment(R.layout.fragment_paging) {

    private val viewModel: PagingViewModel by viewModel()

    private val binding by viewBinding(FragmentPagingBinding::bind)

    private val adapter = PagingAdapter {
        viewModel.retry()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.networkStateLiveData.observe(viewLifecycleOwner) {
            adapter.networkState = it
        }

        viewModel.pagedListLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        binding.recyclerView.adapter = null
        super.onDestroyView()
    }
}