package ru.yandex.dunaev.mick.myenglish

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import ru.yandex.dunaev.mick.myenglish.databinding.FragmentLibraryListBinding

class LibraryListFragment : Fragment() {
    private lateinit var binding: FragmentLibraryListBinding
    private lateinit var model: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_library_list, container, false)
        model = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        binding.viewModel = model
        binding.recycler.apply{
            adapter = BookListAdapter()
            val cols = if(getActivity()!!.isPortrait()) 2 else 3
            layoutManager = GridLayoutManager(activity, cols)
        }

        return binding.root
    }


}
