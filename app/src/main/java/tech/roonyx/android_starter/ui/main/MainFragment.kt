package tech.roonyx.android_starter.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.roonyx.android_starter.R
import tech.roonyx.android_starter.databinding.MainFragmentBinding
import tech.roonyx.android_starter.extension.viewBinding

class MainFragment : Fragment(R.layout.main_fragment) {

    private val binding by viewBinding(MainFragmentBinding::bind)

    private val viewModel: MainViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.messageLiveData.observe(viewLifecycleOwner) {
            binding.message.text = it
        }
    }

    companion object {
        val TAG: String = MainFragment::class.java.simpleName

        fun newInstance() = MainFragment()
    }
}